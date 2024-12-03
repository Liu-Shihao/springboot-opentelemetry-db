package com.lsh.trace.config;

import com.lsh.trace.exporter.MySQLSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

@Configuration
public class OpenTelemetryConfig {

    @Bean
    public OpenTelemetrySdk openTelemetrySdk() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost:3306/opentelemetry";
        String username = "root";
        String password = "Lsh123456!";

        MySQLSpanExporter mySQLSpanExporter = new MySQLSpanExporter(jdbcUrl, username, password);

        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(mySQLSpanExporter))
                .build();

        return OpenTelemetrySdk.builder()
                .setTracerProvider(tracerProvider)
                .buildAndRegisterGlobal();
    }
}