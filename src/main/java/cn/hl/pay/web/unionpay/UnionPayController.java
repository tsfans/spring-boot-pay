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
import cn.hl.pay.constants.unionpay.UnionpayConstants;
import cn.hl.pay.domain.dto.ServiceResponse;
import cn.hl.pay.service.unionpay.UnionpayService;
import cn.hl.pay.util.unionpay.sdk.AcpService;
import cn.hl.pay.util.unionpay.sdk.LogUtil;
import cn.hl.pay.util.unionpay.sdk.SDKConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "unionpay")
@Controller
@RequestMapping("/pay/unionpay")
public class UnionPayController {

  @Autowired
  private UnionpayService payService;

  @ApiOperation(value = "Index page of unionpay")
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index() {
    return "unionpay/index";
  }

  @ApiOperation(value = "PC pay")
  @RequestMapping(value = "/pc/pay", method = RequestMethod.POST)
  public ModelAndView pcpay(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.consume(request,UnionpayConstants.channelType_pc);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "mobile pay")
  @RequestMapping(value = "/mobile/pay", method = RequestMethod.POST)
  public ModelAndView mobilepay(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.consume(request,UnionpayConstants.channelType_mobile);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "query by query id")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnTime",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/pc/query", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> query(HttpServletRequest request){
    return payService.query(request);
  }
  
  @ApiOperation(value = "ConsumeUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/pc/consumeUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> consumeUndo(HttpServletRequest request){
    return payService.consumeUndo(request);
  }
  
  @ApiOperation(value = "Redfund")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/pc/refund", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> refund(HttpServletRequest request){
    return payService.refund(request);
  }
  
  @ApiOperation(value = "PreAuth")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/preAuth", method = RequestMethod.POST)
  public ModelAndView preAuth(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.preAuth(request,UnionpayConstants.channelType_pc);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "AuthFinish")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/authFinish", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> authFinish(HttpServletRequest request) {
    return payService.authFinish(request);
  }
  
  @ApiOperation(value = "AuthUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/authUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> authUndo(HttpServletRequest request) {
    return payService.authUndo(request);
  }
  
  @ApiOperation(value = "AuthFinishUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/authFinishUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> authFinishUndo(HttpServletRequest request) {
    return payService.authFinishUndo(request);
  }
  
  @ApiOperation(value = "front receive unionpay message")
  @RequestMapping(value = "/front", method = RequestMethod.POST)
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
  @RequestMapping(value = "/back", method = RequestMethod.POST)
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
