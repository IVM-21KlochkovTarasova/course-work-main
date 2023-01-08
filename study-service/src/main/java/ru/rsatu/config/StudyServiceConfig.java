package ru.rsatu.config;

import com.orbitz.consul.Consul;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class StudyServiceConfig {

    @ConfigProperty(name = "consul.url")
    String consul_url;

    @Produces
    public Consul ConsulClient(){
        return Consul.builder().withUrl(consul_url).build();
    }

}
