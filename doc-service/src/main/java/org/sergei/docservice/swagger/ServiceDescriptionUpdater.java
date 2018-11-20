package org.sergei.docservice.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class ServiceDescriptionUpdater {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDescriptionUpdater.class);

    private static final String DEFAULT_SWAGGER_URL = "/v2/api-docs";
    private static final String KEY_SWAGGER_URL = "swagger_url";

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final ServiceDefinitionsContext definitionContext;

    @Autowired
    public ServiceDescriptionUpdater(RestTemplate restTemplate,
                                     DiscoveryClient discoveryClient,
                                     ServiceDefinitionsContext definitionContext) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.definitionContext = definitionContext;
    }

    @Scheduled(fixedDelayString = "${swagger.config.refreshrate}")
    public void refreshSwaggerConfigurations() {
        LOGGER.debug("Starting Service Definition Context refresh");

        discoveryClient.getServices().forEach(serviceId -> {
            LOGGER.debug("Attempting service definition refresh for Service: {} ", serviceId);
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
            if (serviceInstances == null || serviceInstances.isEmpty()) { // Should not be the case kept for failsafe
                LOGGER.info("No instances available for service: {} ", serviceId);
            } else {
                ServiceInstance instance = serviceInstances.get(0);
                String swaggerURL = getSwaggerURL(instance);
                LOGGER.info("Attempting to get Swagger definition from URL: {}", swaggerURL);

                Optional<Object> jsonData = getSwaggerDefinitionForAPI(serviceId, swaggerURL);

                if (jsonData.isPresent()) {
                    String content = getJSON(serviceId, jsonData.get());
                    definitionContext.addServiceDefinition(serviceId, content);
                } else {
                    LOGGER.error("Skipping service id: {} Error: Could not get Swagger definition from API ", serviceId);
                }

                LOGGER.info("Service Definition Context Refreshed at:  {}", LocalDate.now());
            }
        });
    }

    private String getSwaggerURL(ServiceInstance instance) {
        String swaggerURL = instance.getMetadata().get(KEY_SWAGGER_URL);
        return swaggerURL != null ? instance.getUri() + swaggerURL : instance.getUri() + DEFAULT_SWAGGER_URL;
    }

    private Optional<Object> getSwaggerDefinitionForAPI(String serviceName, String url) {
        LOGGER.debug("Accessing the SwaggerDefinition JSON for Service: {}: URL: {} ", serviceName, url);
        try {
            Object jsonData = restTemplate.getForObject(url, Object.class);
            return Optional.of(Objects.requireNonNull(jsonData));
        } catch (RestClientException ex) {
            LOGGER.error("Error while getting service definition for service: {} Error: {} ", serviceName, ex.getMessage());
            return Optional.empty();
        }

    }

    private String getJSON(String serviceId, Object jsonData) {
        try {
            return new ObjectMapper().writeValueAsString(jsonData);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error: {} ", e.getMessage());
            return "";
        }
    }
}
