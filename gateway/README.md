# Zuul gateway service
Works as a proxy which is basically and entry point of all existing airport management services.

## Profiling
* plain - plain configuration which does not require Config, Discovery and Auth servers to be run
* dev - development configuration with authentication disabled but required Config and Discovery servers to be run
* prod - production configuration which is located in Configuration GIT repository and requires full setup

No specific configuration required except that to run this service in _prod_ mode 
config service should be up and running.

## Jaeger / Opentracing
Application uses distributed tracing using Jaeger and this tool should be run first.
To run Jaeger in Docker container use the following Docker command:

```
docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:1.13
```