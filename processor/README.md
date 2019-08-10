# Processor service
Service to process reserved flights e.g. validate and respond to reservation service 
requests and perform data processing.

## Profiling
* plain - plain configuration which does not require Config, Discovery and Auth servers to be run
* dev - development configuration with authentication disabled but required Config and Discovery servers to be run
* prod - production configuration which is located in Configuration GIT repository and requires full setup (See steps below)

**Steps to run service in production mode**
1. Run Config server with prod profile enabled
2. Run Discovery server with prod profile enabled
3. Run Auth service with prod profile enabled
4. Run Gateway service with prod profile enabled
5. Run a server of this application with prod profile enabled
6. Go to the Gateway service to the authentication endpoint
7. After this all manipulations with services should be performed through the Gateway

## Jaeger / Opentracing
Application uses distributed tracing using Jaeger and this tool should be run first.
To run Jaeger in Docker container use the following Docker command:

```
docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:1.13
```