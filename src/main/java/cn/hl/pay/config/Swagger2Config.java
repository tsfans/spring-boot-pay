package cn.hl.pay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile("dev")
@Configuration
@EnableSwagger2
public class Swagger2Config {

  @Value("${swagger2.enable}")
  private boolean enableSwagger;

  public Docket unionpayApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("银联支付API")
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("cn.hl.pay.web.unionpay"))
        .paths(PathSelectors.any())
        .build()
        .enable(enableSwagger);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("第三方支付接入系统")
        .description("银联、微信、支付宝")
        .termsOfServiceUrl("xxx")
        .version("0.0.1")
        .build();
  }

}
