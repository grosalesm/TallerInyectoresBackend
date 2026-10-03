package com.taller.ordenes.infrastructure.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    @Bean
    public TopicExchange ordenExchange() {
        return new TopicExchange("orden.exchange");
    }

    @Bean
    public TopicExchange facturacionExchange() {
        return new TopicExchange("facturacion.exchange");
    }

    @Bean
    public Queue ordenesPagoRegistradoQueue() {
        return new Queue("ordenes.pago.registrado.queue", true);
    }

    @Bean
    public Binding bindingPagoRegistrado(Queue ordenesPagoRegistradoQueue,
                                         TopicExchange facturacionExchange) {
        return BindingBuilder.bind(ordenesPagoRegistradoQueue)
                .to(facturacionExchange)
                .with("pago.registrado");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}