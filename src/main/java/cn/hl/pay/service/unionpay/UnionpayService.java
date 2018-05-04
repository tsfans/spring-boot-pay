package cn.hl.pay.service.unionpay;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import cn.hl.pay.constants.unionpay.PayWay;
import cn.hl.pay.constants.unionpay.UnionpayConstants;
import cn.hl.pay.domain.dto.ServiceResponse;
import cn.hl.pay.domain.model.Order;
import cn.hl.pay.domain.model.UnionpayConfig;
import cn.hl.pay.util.common.CommonUtil;
import cn.hl.pay.util.common.DateTimeUtil;
import cn.hl.pay.util.unionpay.sdk.AcpService;
import cn.hl.pay.util.unionpay.sdk.LogUtil;
import cn.hl.pay.util.unionpay.sdk.SDKConfig;
import cn.hl.pay.util.unionpay.sdk.SDKConstants;

@Service
public class UnionpayService {

  @Autowired
  private UnionpayConfig unionpayConfig;

  @Autowired
  private SDKConfig sdkConfig;

  public String getForm(Order order) {

    Map<String, String> data = setCommonData();
    // 渠道类型，这个字段区分B2C网关支付和手机wap支付；07：PC,平板 08：手机
    if (order.getPayWay() == PayWay.PC.code()) {
      data.put(SDKConstants.param_channelType, UnionpayConstants.channelType_pc);
    } else {
      data.put(SDKConstants.param_channelType, UnionpayConstants.channelType_mobile);
    }
    data.put(SDKConstants.param_txnType, UnionpayConstants.txnType_consume);
    data.put(SDKConstants.param_txnSubType, UnionpayConstants.txnSubType_selfConsume);
    data.put(SDKConstants.param_bizType, UnionpayConstants.bizType_gatewayPay);
    data.put(SDKConstants.param_txnAmt, CommonUtil.subZeroAndDot(order.getTotalFee()));
    data.put(SDKConstants.param_orderId, order.getOutTradeNo());
    data.put(SDKConstants.param_txnTime, DateTimeUtil.getCurrentTime());
    data.put(SDKConstants.param_currencyCode, UnionpayConstants.currencyCode_rmb);
    data.put(SDKConstants.param_frontUrl, unionpayConfig.getFrontUrl());
    data.put(SDKConstants.param_backUrl, unionpayConfig.getBackUrl());
    data.put(SDKConstants.param_reqReserved, "nothing");
    Map<String, String> submitData = AcpService.sign(data, unionpayConfig.getEncoding());
    String requestFrontUrl = sdkConfig.getFrontRequestUrl();
    String form = AcpService.createAutoFormHtml(requestFrontUrl, submitData, unionpayConfig.getEncoding());
    return form;
  }

  public ServiceResponse<String> query(HttpServletRequest request) {
    String queryId = request.getParameter(SDKConstants.param_queryId);
    String orderId = request.getParameter(SDKConstants.param_orderId);
    String txnTime = request.getParameter(SDKConstants.param_txnTime);
    Map<String, String> data = setCommonData();

    /*** 银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改 ***/
    data.put(SDKConstants.param_txnType, UnionpayConstants.txnType_query); // 交易类型 00-默认
    data.put(SDKConstants.param_txnSubType, UnionpayConstants.txnSubType_default); // 交易子类型 默认00
    data.put(SDKConstants.param_bizType, UnionpayConstants.bizType_gatewayPay); // 业务类型
                                                                                // B2C网关支付，手机wap支付

    /*** 商户接入参数 ***/
    data.put(SDKConstants.param_queryId, queryId);
    data.put(SDKConstants.param_orderId, orderId);
    data.put(SDKConstants.param_txnTime, txnTime);
    LogUtil.writeLog("request data : " + data);
    Map<String, String> reqData = AcpService.sign(data, unionpayConfig.getEncoding());
    String queryUrl = sdkConfig.getSingleQueryUrl();
    Map<String, String> rspData = AcpService.post(reqData, queryUrl, unionpayConfig.getEncoding());
    if (!rspData.isEmpty()) {
      String rspMsg = JSON.toJSONString(rspData);
      LogUtil.writeLog("response message:" + rspMsg);
      if (AcpService.validate(rspData, unionpayConfig.getEncoding())) {
        return new ServiceResponse<>(rspMsg, HttpStatus.OK);
      } else {
        return new ServiceResponse<>(rspMsg, HttpStatus.BAD_REQUEST);
      }
    }
    return new ServiceResponse<>("response message is empty", HttpStatus.BAD_REQUEST);
  }

  public ServiceResponse<String> consumeUndo(HttpServletRequest request) {
    String orderId = request.getParameter(SDKConstants.param_orderId);
    String txnTime = request.getParameter(SDKConstants.param_txnTime);
    String queryId = request.getParameter(SDKConstants.param_queryId);
    String txnAmt = request.getParameter(SDKConstants.param_txnAmt);
    return null;
  }

  private Map<String, String> setCommonData() {
    Map<String, String> data = new HashMap<>();
    data.put(SDKConstants.param_version, unionpayConfig.getVersion());
    data.put(SDKConstants.param_encoding, unionpayConfig.getEncoding());
    data.put(SDKConstants.param_signMethod, unionpayConfig.getSignMethod());
    data.put(SDKConstants.param_merId, unionpayConfig.getMerId());
    data.put(SDKConstants.param_accessType, unionpayConfig.getAccessType());
    return data;
  }

}
