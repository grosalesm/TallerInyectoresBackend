package com.taller.reportes.application.port.outservice;

import java.util.List;
import java.util.Map;

public interface ClienteOutService {
    List<Map<String, Object>> listarTodos();
}