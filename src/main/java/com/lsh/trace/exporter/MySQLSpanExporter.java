package com.lsh.trace.exporter;

import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.sdk.trace.data.SpanData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

public class MySQLSpanExporter implements SpanExporter {

    private static final String INSERT_SQL = "INSERT INTO trace_data (id, trace_id, span_id, parent_span_id, name, start_time, end_time, attributes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private final Connection connection;

    public MySQLSpanExporter(String jdbcUrl, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(jdbcUrl, username, password);
    }

    @Override
    public CompletableResultCode export(Collection<SpanData> spans) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SQL)) {
            for (SpanData span : spans) {
                statement.setString(1, span.getSpanId());
                statement.setString(2, span.getTraceId());
                statement.setString(3, span.getSpanId());
                statement.setString(4, span.getParentSpanId());
                statement.setString(5, span.getName());
                statement.setTimestamp(6, new java.sql.Timestamp(span.getStartEpochNanos() / 1_000_000));
                statement.setTimestamp(7, new java.sql.Timestamp(span.getEndEpochNanos() / 1_000_000));
                statement.setString(8, span.getAttributes().toString());
                statement.addBatch();
            }
            statement.executeBatch();
            return CompletableResultCode.ofSuccess();
        } catch (SQLException e) {
            e.printStackTrace();
            return CompletableResultCode.ofFailure();
        }
    }

    @Override
    public CompletableResultCode flush() {
        // Optional: Add logic to flush data if needed
        return null;
    }

    @Override
    public CompletableResultCode shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}