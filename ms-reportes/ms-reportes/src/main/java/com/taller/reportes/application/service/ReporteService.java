package com.taller.reportes.application.service;

import com.taller.reportes.application.port.outservice.ClienteOutService;
import com.taller.reportes.application.port.outservice.OrdenOutService;
import com.taller.reportes.application.port.outservice.ReciboOutService;
import com.taller.reportes.application.port.usecase.ReporteUseCase;
import com.taller.reportes.domain.bean.Dashboard;
import com.taller.reportes.domain.bean.ItemReporte;
import com.taller.reportes.domain.bean.OrdenResumen;
import com.taller.reportes.domain.bean.ReporteMensual;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService implements ReporteUseCase {

    private final OrdenOutService ordenOutService;
    private final ReciboOutService reciboOutService;
    private final ClienteOutService clienteOutService;

    @Override
    public ReporteMensual reportePorMes(int mes, int anio) {
        List<Map<String, Object>> recibos = reciboOutService.listarPorMes(mes, anio);

        ReporteMensual reporte = new ReporteMensual();
        reporte.setMes(mes);
        reporte.setAnio(anio);
        reporte.setTipoFiltro("mes");
        reporte.setNombreMes(
                LocalDate.of(anio, mes, 1)
                        .getMonth()
                        .getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                        + " " + anio
        );
        reporte.setTitulo("Reporte de " + reporte.getNombreMes());
        reporte.setTotalOrdenes(recibos.size());
        reporte.setOrdenesAtendidas(recibos.size());
        reporte.setIngresoTotal(sumarMontos(recibos));
        reporte.setRecibos(recibos);
        reporte.setServiciosMasUsados(calcularServiciosMasUsados(recibos));
        reporte.setInyectoresMasAtendidos(calcularInyectoresMasAtendidos(recibos));
        return reporte;
    }

    @Override
    public Dashboard obtenerDashboard() {
        List<OrdenResumen> ordenes = ordenOutService.listarTodas();
        List<Map<String, Object>> clientes = clienteOutService.listarTodos();

        LocalDate hoy = LocalDate.now();
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes = hoy.withDayOfMonth(hoy.lengthOfMonth());

        List<Map<String, Object>> recibosMes = reciboOutService
                .listarPorMes(hoy.getMonthValue(), hoy.getYear());

        Dashboard dash = new Dashboard();
        dash.setOrdenesTotalHoy((int) ordenes.stream()
                .filter(o -> o.getFechaIngreso() != null
                        && o.getFechaIngreso().toLocalDate().equals(hoy))
                .count());
        dash.setOrdenesPendientes((int) ordenes.stream()
                .filter(o -> "Pendiente".equalsIgnoreCase(o.getEstado()))
                .count());
        dash.setOrdenesEnProceso((int) ordenes.stream()
                .filter(o -> "En Proceso".equalsIgnoreCase(o.getEstado()))
                .count());
        dash.setOrdenesTerminadas((int) ordenes.stream()
                .filter(o -> "Terminado".equalsIgnoreCase(o.getEstado()))
                .count());
        dash.setOrdenesPagadas((int) ordenes.stream()
                .filter(o -> "Pagado".equalsIgnoreCase(o.getEstado())
                        && o.getFechaIngreso() != null
                        && !o.getFechaIngreso().toLocalDate().isBefore(inicioMes)
                        && !o.getFechaIngreso().toLocalDate().isAfter(finMes))
                .count());
        dash.setIngresosMes(sumarMontos(recibosMes));
        dash.setTotalClientes(clientes.size());
        dash.setUltimasOrdenes(ordenes.stream()
                .sorted(Comparator.comparing(
                        OrdenResumen::getFechaIngreso,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(5)
                .collect(Collectors.toList()));
        return dash;
    }

    private Double sumarMontos(List<Map<String, Object>> recibos) {
        return recibos.stream()
                .map(r -> r.get("monto"))
                .filter(m -> m instanceof Number)
                .mapToDouble(m -> ((Number) m).doubleValue())
                .sum();
    }

    private List<ItemReporte> calcularServiciosMasUsados(List<Map<String, Object>> recibos) {
        Map<String, ItemReporte> mapa = new LinkedHashMap<>();
        for (Map<String, Object> recibo : recibos) {
            Object serviciosObj = recibo.get("servicios");
            if (!(serviciosObj instanceof List<?> servicios)) continue;
            for (Object item : servicios) {
                if (!(item instanceof Map<?, ?> servicio)) continue;
                String nombre = String.valueOf(servicio.get("nombreServicio"));
                int cantidad = servicio.get("cantidad") instanceof Number
                        ? ((Number) servicio.get("cantidad")).intValue() : 0;
                double subtotal = servicio.get("subtotal") instanceof Number
                        ? ((Number) servicio.get("subtotal")).doubleValue() : 0.0;
                ItemReporte it = mapa.computeIfAbsent(nombre,
                        k -> new ItemReporte(k, 0, 0.0));
                it.setCantidad(it.getCantidad() + cantidad);
                it.setTotal(it.getTotal() + subtotal);
            }
        }
        return mapa.values().stream()
                .sorted(Comparator.comparingInt(ItemReporte::getCantidad).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    private List<ItemReporte> calcularInyectoresMasAtendidos(List<Map<String, Object>> recibos) {
        Map<String, ItemReporte> mapa = new LinkedHashMap<>();
        for (Map<String, Object> recibo : recibos) {
            Object inyectoresObj = recibo.get("inyectores");
            if (!(inyectoresObj instanceof List<?> inyectores)) continue;
            for (Object item : inyectores) {
                if (!(item instanceof Map<?, ?> inyector)) continue;
                String modelo = String.valueOf(inyector.get("modeloInyector"));
                String marca = String.valueOf(inyector.get("marcaInyector"));
                String nombre = modelo + " (" + marca + ")";
                int cantidad = inyector.get("cantidad") instanceof Number
                        ? ((Number) inyector.get("cantidad")).intValue() : 0;
                ItemReporte it = mapa.computeIfAbsent(nombre,
                        k -> new ItemReporte(k, 0, 0.0));
                it.setCantidad(it.getCantidad() + cantidad);
            }
        }
        return mapa.values().stream()
                .sorted(Comparator.comparingInt(ItemReporte::getCantidad).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}