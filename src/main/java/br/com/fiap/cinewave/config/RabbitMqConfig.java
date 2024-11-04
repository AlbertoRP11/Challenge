package br.com.fiap.cinewave.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue cinewaveQueue() {
        return new Queue("cinewave_queue", true); // true indica que a fila é durável
    }

    public Queue queue(){
        return new Queue("email-queue", true);
    }
}