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
  public ModelAndView pcPay(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.consume(request,UnionpayConstants.channelType_pc);
    mv.addObject("form", form);
    return mv;
  }
  
  
  @ApiOperation(value = "PC query by query id")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnTime",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/pc/query", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> pcQuery(HttpServletRequest request){
    return payService.query(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "PC ConsumeUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/pc/consumeUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> pcConsumeUndo(HttpServletRequest request){
    return payService.consumeUndo(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "PC Redfund")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/pc/refund", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> pcRefund(HttpServletRequest request){
    return payService.refund(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "PC PreAuth")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/preAuth", method = RequestMethod.POST)
  public ModelAndView pcPreAuth(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.preAuth(request,UnionpayConstants.channelType_pc);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "PC AuthFinish")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/authFinish", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> pcAuthFinish(HttpServletRequest request) {
    return payService.authFinish(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "PC AuthUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/authUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> pcAuthUndo(HttpServletRequest request) {
    return payService.authUndo(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "PC AuthFinishUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/pc/authFinishUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> pcAuthFinishUndo(HttpServletRequest request) {
    return payService.authFinishUndo(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "mobile pay")
  @RequestMapping(value = "/mobile/pay", method = RequestMethod.POST)
  public ModelAndView mobilePay(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.consume(request,UnionpayConstants.channelType_mobile);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "Mobile query by query id")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnTime",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value="/mobile/query",method=RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> mobileQuery(HttpServletRequest request){
    return payService.query(request, UnionpayConstants.channelType_mobile);
  }
  
  @ApiOperation(value = "PC ConsumeUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/mobile/consumeUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> mobileConsumeUndo(HttpServletRequest request){
    return payService.consumeUndo(request, UnionpayConstants.channelType_mobile);
  }
  
  @ApiOperation(value = "Mobile Redfund")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
  })
  @RequestMapping(value = "/mobile/refund", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> mobileRefund(HttpServletRequest request){
    return payService.refund(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "Mobile PreAuth")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/mobile/preAuth", method = RequestMethod.POST)
  public ModelAndView mobilePreAuth(HttpServletRequest request) {
    ModelAndView mv = new ModelAndView("unionpay/pay");
    String form = payService.preAuth(request,UnionpayConstants.channelType_pc);
    mv.addObject("form", form);
    return mv;
  }
  
  @ApiOperation(value = "Mobile AuthFinish")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/mobile/authFinish", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> mobileAuthFinish(HttpServletRequest request) {
    return payService.authFinish(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "Mobile AuthUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/mobile/authUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> mobileAuthUndo(HttpServletRequest request) {
    return payService.authUndo(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "Mobile AuthFinishUndo")
  @ApiImplicitParams({
    @ApiImplicitParam(name="orderId",required=true,dataType="String"),
    @ApiImplicitParam(name="queryId",required=true,dataType="String"),
    @ApiImplicitParam(name="txnAmt",required=true,dataType="String")
  })
  @RequestMapping(value = "/mobile/authFinishUndo", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> mobileAuthFinishUndo(HttpServletRequest request) {
    return payService.authFinishUndo(request, UnionpayConstants.channelType_pc);
  }
  
  @ApiOperation(value = "FileTransfer")
   @ApiImplicitParam(name="settleDate",required=true,dataType="String")
  @RequestMapping(value = "/fileTransfer", method = RequestMethod.POST)
  public @ResponseBody ServiceResponse<String> fileTransfer(HttpServletRequest request) {
    return payService.fileTransfer(request);
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
