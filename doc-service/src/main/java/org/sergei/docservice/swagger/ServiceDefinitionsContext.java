package org.sergei.docservice.swagger;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Sergei Visotsky, 2018
 */
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServiceDefinitionsContext {

    private final ConcurrentHashMap<String, String> serviceDescriptions;

    private ServiceDefinitionsContext() {
        serviceDescriptions = new ConcurrentHashMap<>();
    }

    void addServiceDefinition(String serviceName, String serviceDescription) {
        serviceDescriptions.put(serviceName, serviceDescription);
    }

    public String getSwaggerDefinition(String serviceId) {
        return this.serviceDescriptions.get(serviceId);
    }

    List<SwaggerResource> getSwaggerDefinitions() {
        return serviceDescriptions.entrySet().stream().map(serviceDefinition -> {
            SwaggerResource resource = new SwaggerResource();
            resource.setLocation("/service/" + serviceDefinition.getKey());
            resource.setName(serviceDefinition.getKey());
            resource.setSwaggerVersion("2.0");
            return resource;
        }).collect(Collectors.toList());
    }
}
