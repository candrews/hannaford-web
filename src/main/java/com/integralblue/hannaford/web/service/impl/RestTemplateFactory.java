package com.integralblue.hannaford.web.service.impl;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.integralblue.hannaford.web.property.ProxyProperties;

@Component
public class RestTemplateFactory implements FactoryBean<RestTemplate>, InitializingBean {
	
    private RestTemplate restTemplate;
    
    private static class Interceptor implements ClientHttpRequestInterceptor {

        @Override
        public ClientHttpResponse intercept(
                HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
                throws IOException {

            HttpHeaders headers = request.getHeaders();
            headers.add(HttpHeaders.COOKIE, "TS0122583e=015c807ca72a4d06a1efe857d3bf7d06674c11ca3a589d174d37a2d908efc80542ab6cd3a042d8b5e07d8b4dee5a2b57b8c781c7b691a1112d37cbc2b4008c7ac8d2f217fff1a37923933414025ae25dedc10f09d32cd2f9375fbd7e3f08356a8d2f31df9784bcd1301eb3666c17e59d566414eea2; fsr.s={\"f\":1468264637890,\"rid\":\"d028012-57583119-0c28-8baf-dcae3\",\"ru\":\"http://www.hannaford.com/custserv/locate_store.cmd\",\"r\":\"www.hannaford.com\",\"st\":\"\",\"to\":4.1,\"v\":-2,\"c\":\"http://www.hannaford.com/custserv/locate_store.cmd\",\"pv\":6,\"lc\":{\"d0\":{\"v\":6,\"s\":true}}}; PIPELINE_SESSION_ID=caff90337b77479480ff96587ffb3db3; JSESSIONID=DDAD2CFB7705FC0BA55231D6A9A398A3; HFD-LoadBalance=!XDeJGb9AVdwHiZKbiGzL8paiCpVwVP2gLvxHSWIzpyl9Bf5n0TF1a4OznH8+hJD1ChBuWUdYYBqH1+g=; __utma=233378055.93321936.1468264582.1468264582.1468264582.1; __utmb=233378055.12.9.1468264636324; __utmc=233378055; __utmz=233378055.1468264582.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __utmt=1; _fsrSR=%7B%7D");
            return execution.execute(request, body);
        }
    }
 
    public RestTemplate getObject() {
        return restTemplate;
    }
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }
    public boolean isSingleton() {
        return true;
    }
 
    public void afterPropertiesSet() {
        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(Collections.singletonList(new Interceptor()));
    }
}