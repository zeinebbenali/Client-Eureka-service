package com.project.eureka.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ClientApplication {
    @Autowired
    private EurekaClient eurekaClient;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String callService() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        InstanceInfo info = eurekaClient.getNextServerFromEureka("service-magazin-client", false);
        String baseurl = info.getHomePageUrl();
        ResponseEntity<String> response = restTemplate.exchange(baseurl, HttpMethod.GET, null, String.class);
        return response.getBody();
    }
}
