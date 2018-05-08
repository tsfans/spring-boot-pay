package cn.hl.pay.service.unionpay;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import cn.hl.pay.constants.unionpay.UnionpayConstants;
import cn.hl.pay.domain.dto.ServiceResponse;
import cn.hl.pay.domain.model.UnionpayConfig;
import cn.hl.pay.util.common.CommonUtil;
import cn.hl.pay.util.common.DateTimeUtil;
import cn.hl.pay.util.unionpay.sdk.AcpService;
import cn.hl.pay.util.unionpay.sdk.LogUtil;
import cn.hl.pay.util.unionpay.sdk.SDKConfig;
import cn.hl.pay.util.unionpay.sdk.SDKConstants;

@Service
public class UnionpayService {

  @Value("${payment.unionpay.billFilePath}")
  private String billFilePath;

  @Autowired
  private UnionpayConfig unionpayConfig;

  @Autowired
  private SDKConfig sdkConfig;

  public String consume(HttpServletRequest request, String channelType) {
    return getForm(request, UnionpayConstants.txnType_consume, UnionpayConstants.txnSubType_01,
        channelType);
  }

  public ServiceResponse<String> query(HttpServletRequest request, String channelType) {
    return operateByQueryId(request, UnionpayConstants.txnType_default,
        UnionpayConstants.txnSubType_default, channelType);
  }

  public ServiceResponse<String> consumeUndo(HttpServletRequest request, String channelType) {
    return operateByQueryId(request, UnionpayConstants.txnType_consumeUndo,
        UnionpayConstants.txnSubType_default, channelType);
  }

  public ServiceResponse<String> refund(HttpServletRequest request, String channelType) {
    return operateByQueryId(request, UnionpayConstants.txnType_refund,
        UnionpayConstants.txnSubType_default, channelType);
  }

  public ServiceResponse<String> authFinish(HttpServletRequest request, String channelType) {
    return operateByQueryId(request, UnionpayConstants.txnType_authFinish,
        UnionpayConstants.txnSubType_default, channelType);
  }

  public ServiceResponse<String> authUndo(HttpServletRequest request, String channelType) {
    return operateByQueryId(request, UnionpayConstants.txnType_authUndo,
        UnionpayConstants.txnSubType_default, channelType);
  }

  public ServiceResponse<String> authFinishUndo(HttpServletRequest request, String channelType) {
    return operateByQueryId(request, UnionpayConstants.txnType_authFinishUndo,
        UnionpayConstants.txnSubType_default, channelType);
  }

  public String preAuth(HttpServletRequest request, String channelType) {
    return getForm(request, UnionpayConstants.txnType_preAuth, UnionpayConstants.txnSubType_01,
        channelType);
  }


  public ServiceResponse<String> fileTransfer(HttpServletRequest request) {
    String settleDate = request.getParameter(SDKConstants.param_settleDate);
    Map<String, String> data = setData(UnionpayConstants.txnType_fileTransfer,
        UnionpayConstants.txnSubType_01, UnionpayConstants.bizType_default, null,
        DateTimeUtil.getCurrentTime(), null, null, null, null, null);
    data.put(SDKConstants.param_settleDate, settleDate);
    data.put(SDKConstants.param_fileType, UnionpayConstants.fileType_default);
    //测试案例   请使用700000000000001商户号发起文件传输案例测试 清算日期：0119
    data.put(SDKConstants.param_merId, "700000000000001");
    Map<String, String> reqData = AcpService.sign(data, unionpayConfig.getEncoding());
    Map<String, String> rspData =
        AcpService.post(reqData, sdkConfig.getFileTransUrl(), unionpayConfig.getEncoding());
    String rspJson = JSON.toJSONString(rspData);
    LogUtil.writeLog("FileTransfer rspJson:" + rspJson);
    if (!rspData.isEmpty()) {
      if (AcpService.validate(rspData, unionpayConfig.getEncoding())) {
        LogUtil.writeLog("验证签名成功");
        String respCode = rspData.get(SDKConstants.param_respCode);
        if ("00".equals(respCode)) {
          AcpService.deCodeFileContent(rspData, billFilePath, unionpayConfig.getEncoding());
          return new ServiceResponse<>(rspJson);
        } else {
          return new ServiceResponse<>(rspJson, HttpStatus.BAD_REQUEST);
        }
      } else {
        LogUtil.writeErrorLog("验证签名失败");
        return new ServiceResponse<>(rspJson, HttpStatus.BAD_REQUEST);
      }
    } else {
      LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
      return new ServiceResponse<>(rspJson, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private String getForm(HttpServletRequest request,
                         String txnType,
                         String txnSubType,
                         String channelType) {
    String orderId = request.getParameter(SDKConstants.param_orderId);
    String txnAmt = request.getParameter(SDKConstants.param_txnAmt);
    Map<String, String> data = setData(txnType, txnSubType, UnionpayConstants.bizType_gatewayPay,
        orderId, DateTimeUtil.getCurrentTime(), CommonUtil.subZeroAndDot(txnAmt), channelType,
        UnionpayConstants.currencyCode_rmb, unionpayConfig.getFrontUrl(),
        unionpayConfig.getBackUrl());
    // data.put(SDKConstants.param_reqReserved, "nothing");
    LogUtil.writeLog("Consume data:" + JSON.toJSONString(data));
    Map<String, String> submitData = AcpService.sign(data, unionpayConfig.getEncoding());
    String form = AcpService.createAutoFormHtml(sdkConfig.getFrontRequestUrl(), submitData,
        unionpayConfig.getEncoding());
    return form;
  }

  private ServiceResponse<String> operateByQueryId(HttpServletRequest request,
                                                   String txnType,
                                                   String txnSubType,
                                                   String channelType) {
    String orderId = request.getParameter(SDKConstants.param_orderId);
    String queryId = request.getParameter(SDKConstants.param_queryId);
    String txnAmt = request.getParameter(SDKConstants.param_txnAmt);
    Map<String, String> data =
        setData(txnType, txnSubType, UnionpayConstants.bizType_gatewayPay, orderId,
            DateTimeUtil.getCurrentTime(), txnAmt, channelType, UnionpayConstants.currencyCode_rmb,
            unionpayConfig.getFrontUrl(), unionpayConfig.getBackUrl());
    data.put(SDKConstants.param_origQryId, queryId);
    Map<String, String> reqData = AcpService.sign(data, unionpayConfig.getEncoding());
    Map<String, String> rspData =
        AcpService.post(reqData, sdkConfig.getBackRequestUrl(), unionpayConfig.getEncoding());
    if (!rspData.isEmpty()) {
      String rspJson = JSON.toJSONString(rspData);
      LogUtil.writeLog("RspJson:" + rspJson);
      if (AcpService.validate(rspData, unionpayConfig.getEncoding())) {
        LogUtil.writeLog("验证签名通过！");
        return new ServiceResponse<>(rspJson);
      } else {
        LogUtil.writeLog("验证签名未通过！");
        return new ServiceResponse<>(rspJson, HttpStatus.BAD_REQUEST);
      }
    } else {
      LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
      return new ServiceResponse<>("未获取到返回报文或返回http状态码非200", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private Map<String, String> setData(String txnType,
                                      String txnSubType,
                                      String bizType,
                                      String orderId,
                                      String txnTime,
                                      String txnAmt,
                                      String channelType,
                                      String currencyCode,
                                      String frontUrl,
                                      String backUrl) {
    Map<String, String> data = new HashMap<>();

    /*** 银联全渠道系统参数，不需修改 ***/
    data.put(SDKConstants.param_version, unionpayConfig.getVersion());
    data.put(SDKConstants.param_encoding, unionpayConfig.getEncoding());
    data.put(SDKConstants.param_signMethod, unionpayConfig.getSignMethod());
    data.put(SDKConstants.param_merId, unionpayConfig.getMerId());
    data.put(SDKConstants.param_accessType, unionpayConfig.getAccessType());

    /**** 自选参数 *****/
    data.put(SDKConstants.param_txnType, txnType);
    data.put(SDKConstants.param_txnSubType, txnSubType);
    data.put(SDKConstants.param_bizType, bizType);
    data.put(SDKConstants.param_orderId, orderId);
    data.put(SDKConstants.param_txnTime, txnTime);
    data.put(SDKConstants.param_txnAmt, txnAmt);
    data.put(SDKConstants.param_channelType, channelType);
    data.put(SDKConstants.param_currencyCode, currencyCode);
    data.put(SDKConstants.param_frontUrl, frontUrl);
    data.put(SDKConstants.param_backUrl, backUrl);

    return data;
  }

}
