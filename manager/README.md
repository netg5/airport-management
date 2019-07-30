# Management service
Service to perform airport management operations e.g. save aircraft, pilot, calendar and other administrative data.

## Jaeger trace
For tracing application uses Jaeger.

Jaeger is an implementation of opentrace specification which gives an ability to perform tracing of application, 
see all the requests, see its quantity, how successful they are (status code) and more other useful features.

Run Jaeger in Docker container:

```
docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:1.13
```