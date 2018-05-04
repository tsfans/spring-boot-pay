package cn.hl.pay.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.hl.pay.domain.dto.ServiceResponse;

@Profile("!dev")
@RestController
public class DisableSwaggerUIController {

  @RequestMapping(value="/swagger-ui.html", method = RequestMethod.GET)
  public ServiceResponse<String> swaggerUi(HttpServletResponse response) {
    return new ServiceResponse<>("404 NOT FOUND!",HttpStatus.NOT_FOUND);
  }
}
