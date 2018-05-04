package cn.hl.pay.service.unionpay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.hl.pay.constants.unionpay.PayWay;
import cn.hl.pay.domain.dto.ServiceResponse;
import cn.hl.pay.domain.model.Order;
import cn.hl.pay.domain.model.UnionpayConfig;
import cn.hl.pay.util.common.CommonUtil;
import cn.hl.pay.util.common.DateTimeUtil;
import cn.hl.pay.util.unionpay.sdk.AcpService;
import cn.hl.pay.util.unionpay.sdk.LogUtil;
import cn.hl.pay.util.unionpay.sdk.SDKConfig;

@Service
public class UnionpayService {

	@Autowired
	private UnionpayConfig unionpayConfig;
	
	@SuppressWarnings("unchecked")
	public String getForm(Order order) {
		//渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板  08：手机
		if(order.getPayWay()==PayWay.PC.code()) {
			unionpayConfig.setChannelType("07");
		}else {
			unionpayConfig.setChannelType("08");
		}
		unionpayConfig.setTxnAmt(CommonUtil.subZeroAndDot(order.getTotalFee()));
		unionpayConfig.setOrderId(order.getOutTradeNo());
		unionpayConfig.setTxnTime(DateTimeUtil.getCurrentTime());
		unionpayConfig.setReqReserved("customize parameter");
		Map<String,String> requestData = JSON.parseObject(JSON.toJSONString(unionpayConfig),Map.class);
		LogUtil.writeLog("unionpayConfig : "+unionpayConfig);
		Map<String,String> submitData = AcpService.sign(requestData, unionpayConfig.getEncoding());
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
		String form = AcpService.createAutoFormHtml(requestFrontUrl, submitData, unionpayConfig.getEncoding());
		return form;
	}

  public ServiceResponse<String> query(HttpServletRequest request) {
    String queryId = request.getParameter("queryId");
    String orderId = request.getParameter("orderId");
    String txnTime = request.getParameter("txnTime");
    Map<String, String> data = new HashMap<String, String>();
    
    /***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
    data.put("version", unionpayConfig.getVersion());                 //版本号
    data.put("encoding", unionpayConfig.getEncoding());               //字符集编码 可以使用UTF-8,GBK两种方式
    data.put("signMethod", unionpayConfig.getSignMethod());                          //签名方法 目前只支持01-RSA方式证书加密
    data.put("txnType", "00");                             //交易类型 00-默认
    data.put("txnSubType", "00");                          //交易子类型  默认00
    data.put("bizType", unionpayConfig.getBizType());                         //业务类型 B2C网关支付，手机wap支付
    
    /***商户接入参数***/
    data.put("merId", unionpayConfig.getMerId());                  //商户号码，请改成自己申请的商户号或者open上注册得来的777商户号测试
    data.put("accessType", unionpayConfig.getAccessType());                           //接入类型，商户接入固定填0，不需修改
    data.put("queryId", queryId);
    data.put("orderId", orderId);
    data.put("txnTime", txnTime);
    LogUtil.writeLog("request data : "+ data);
    Map<String,String> reqData = AcpService.sign(data, unionpayConfig.getEncoding());
    String queryUrl = SDKConfig.getConfig().getSingleQueryUrl();
    Map<String,String> rspData = AcpService.post(reqData, queryUrl, unionpayConfig.getEncoding());
    if(!rspData.isEmpty()) {
      String rspMsg = JSON.toJSONString(rspData);
      LogUtil.writeLog("response message:"+rspMsg);
      if(AcpService.validate(rspData, unionpayConfig.getEncoding())) {
        return new ServiceResponse<>(rspMsg,HttpStatus.OK);
      }else {
        return new ServiceResponse<>(rspMsg,HttpStatus.BAD_REQUEST);
      }
    }
    return new ServiceResponse<>("response message is empty",HttpStatus.BAD_REQUEST);
  }

}
