
```shell
export JAVA_TOOL_OPTIONS="-javaagent:PATH/TO/opentelemetry-javaagent.jar" \
  OTEL_TRACES_EXPORTER=logging \
  OTEL_METRICS_EXPORTER=logging \
  OTEL_LOGS_EXPORTER=logging \
  OTEL_METRIC_EXPORT_INTERVAL=15000


JAVA_TOOL_OPTIONS=-javaagent:/Users/liushihao/IdeaProjects/springboot-opentelemetry-db/lib/opentelemetry-javaagent.jar;OTEL_TRACES_EXPORTER=logging;OTEL_METRICS_EXPORTER=logging;OTEL_LOGS_EXPORTER=logging;OTEL_METRIC_EXPORT_INTERVAL=15000
```

```sql
CREATE DATABASE opentelemetry;
USE opentelemetry;
CREATE TABLE trace_data (
    id VARCHAR(50) PRIMARY KEY,
    trace_id VARCHAR(50),
    span_id VARCHAR(50),
    parent_span_id VARCHAR(50),
    name VARCHAR(255),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    attributes TEXT
);
```