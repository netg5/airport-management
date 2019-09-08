# Prometheus Metrics
To run prometheus metrics and dashboard run the following command:

`docker run -d --name=prometheus -p 9090:9090 -v "PATH_TO\prometheus.yml":/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml`