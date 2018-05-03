package cn.hl.pay.web.unionpay;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.hl.pay.constants.unionpay.PayWay;
import cn.hl.pay.domain.dto.ServiceResponse;
import cn.hl.pay.domain.model.Order;
import cn.hl.pay.service.unionpay.UnionpayService;
import cn.hl.pay.util.unionpay.sdk.AcpService;
import cn.hl.pay.util.unionpay.sdk.LogUtil;
import cn.hl.pay.util.unionpay.sdk.SDKConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "unionpay")
@Controller
@RequestMapping("/unionpay")
public class UnionPayController {

  @Autowired
  private UnionpayService payService;

  @ApiOperation(value = "Index page of unionpay")
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index() {
    return "unionpay/index";
  }

  @ApiOperation(value = "PC pay")
  @RequestMapping(value = "/pcpay", method = RequestMethod.POST)
  public ModelAndView pcpay(Order order) {
    LogUtil.writeLog("order:" + order);
    ModelAndView mv = new ModelAndView("unionpay/pay");
    order.setPayWay(PayWay.PC.code());
    String form = payService.getForm(order);
    LogUtil.writeLog("form:" + form);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "mobile pay")
  @RequestMapping(value = "/mobilepay", method = RequestMethod.POST)
  public ModelAndView mobilepay(Order order) {
    LogUtil.writeLog("order:" + order);
    ModelAndView mv = new ModelAndView("unionpay/pay");
    order.setPayWay(PayWay.MOBILE.code());
    String form = payService.getForm(order);
    LogUtil.writeLog("form:" + form);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "query by order id")
  @RequestMapping(value = "/query")
  public @ResponseBody ServiceResponse<String> query(HttpServletRequest request){
    return payService.query(request);
  }
  
  @ApiOperation(value = "front receive unionpay message")
  @RequestMapping(value = "/front")
  public @ResponseBody ServiceResponse<String> front(HttpServletRequest request) {
    String encoding = request.getParameter(SDKConstants.param_encoding);
    Map<String,String> params = getAllRequestParams(request);
    String requestParams = JSON.toJSONString(params);
    LogUtil.writeLog("request params : "+requestParams);
    if(AcpService.validate(params, encoding)) {
      return new ServiceResponse<>(requestParams,HttpStatus.OK);
    }else {
      return new ServiceResponse<>(requestParams,HttpStatus.BAD_REQUEST);
    }
  }

  @ApiOperation(value = "back receive unionpay message")
  @RequestMapping(value = "/back")
  public @ResponseBody ServiceResponse<String> back(HttpServletRequest request) {
    String encoding = request.getParameter(SDKConstants.param_encoding);
    Map<String,String> params = getAllRequestParams(request);
    String requestParams = JSON.toJSONString(params);
    LogUtil.writeLog("request params : "+requestParams);
    if(AcpService.validate(params, encoding)) {
      LogUtil.writeLog("验证签名成功!");
    }else {
      LogUtil.writeErrorLog("验证签名失败！");
    }
    return new ServiceResponse<>("ok",HttpStatus.OK);
  }
  
  private Map<String, String> getAllRequestParams(HttpServletRequest request) {
    Map<String,String> params = new HashMap<>();
    Enumeration<String> temp = request.getParameterNames();
    if(temp!=null) {
      while(temp.hasMoreElements()) {
        String key = temp.nextElement();
        String value = request.getParameter(key);
        if(value!=null || "".equals(value)) {
          params.put(key, value);
        }
      }
    }
    return params;
  }

}
