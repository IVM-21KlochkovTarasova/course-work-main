package ru.rsatu.lifecicle;

import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.agent.ImmutableRegistration;
import com.orbitz.consul.model.health.ServiceHealth;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StudyServiceLifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudyServiceLifecycle.class);
    private String instanceId;

    @Inject
    Consul consulClient;
    @ConfigProperty(name = "quarkus.application.name")
    String appName;
    @ConfigProperty(name = "quarkus.application.version")
    String appVersion;
    @ConfigProperty(name = "quarkus.http.port")
    int port;
    @ConfigProperty(name = "app.host_url")
    String HOST_URL;

    void onStart(@Observes StartupEvent ev) {
        HealthClient healthClient = consulClient.healthClient();
        List<ServiceHealth> instances = healthClient.getHealthyServiceInstances(appName).getResponse();
        instanceId = appName + "-" + instances.size();
        ImmutableRegistration registration = ImmutableRegistration.builder()
                .id(instanceId)
                .name(appName)
                .address(HOST_URL)
                .port(port)
                .putMeta("version", appVersion)
                .build();
        consulClient.agentClient().register(registration);
        LOGGER.info("Instance registered: id={}, address={}:{}", registration.getId(), HOST_URL, port);
    }

    void onStop(@Observes ShutdownEvent ev) {
        consulClient.agentClient().deregister(instanceId);
        LOGGER.info("Instance de-registered: id={}", instanceId);
    }

}
