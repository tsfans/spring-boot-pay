package cn.hl.pay.client;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SimpleHttpClinet {

	@Autowired
	private RestTemplate restTemplate;
	
	public String simplePost(String requestBody,String url) {
		return post(requestBody,url,jsonHeader()).getBody().toString();
	}
	
	public String simpleGet(String url) {
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate.getForEntity(url, String.class).getBody().toString();
	}
	
	public ResponseEntity<String> post(String requestBody,String url,HttpHeaders headers) {
		HttpEntity<String> entity = new HttpEntity<>(requestBody,headers);
		return restTemplate.postForEntity(url, entity, String.class);
	}
	
	public HttpHeaders jsonHeader() {
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(mediaType);
		return headers;
	}
}
