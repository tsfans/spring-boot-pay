package cn.hl.pay.config;

import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer{

	// connection pooling
    @Value("${httpClient.connection.pool.size}") private String poolMaxTotal;

    @Value("${httpClientFactory.connection.timeout}") private String connectionTimeOut;

    @Value("${httpClientFactory.read.timeout}") private String readTimeOut;
    
    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
    	List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
    	converters.add(new FormHttpMessageConverter());
    	restTemplate.setMessageConverters(converters);
    	return restTemplate;
    }
    
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
    	HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient());
    	factory.setConnectTimeout(Integer.parseInt(connectionTimeOut));
    	factory.setReadTimeout(Integer.parseInt(readTimeOut));
    	return factory;
    }
    
    @Bean
    public HttpClient httpClient() {
    	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    	cm.setMaxTotal(Integer.parseInt(poolMaxTotal));
    	return HttpClientBuilder.create().setConnectionManager(cm).build();
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("index");
    }
	
}
