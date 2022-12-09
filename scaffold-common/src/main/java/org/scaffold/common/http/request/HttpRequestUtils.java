package org.scaffold.common.http.request;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class HttpRequestUtils {

    private static RestTemplate RestTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    public HttpRequestUtils(RestTemplate restTemplate){
        RestTemplate = restTemplate;
    }

    public static String get(String url){

        ResponseEntity<String> responseEntity = RestTemplate.getForEntity(url,String.class);

        return responseEntity.getBody();
    }


    public static String post(String url,String body){

        ResponseEntity<String> responseEntity = RestTemplate.postForEntity(url,body,String.class);

        return responseEntity.getBody();
    }

}
