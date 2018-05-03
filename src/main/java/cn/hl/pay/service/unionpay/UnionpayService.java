package cn.hl.pay.service.unionpay;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.hl.pay.constants.unionpay.PayWay;
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

}
