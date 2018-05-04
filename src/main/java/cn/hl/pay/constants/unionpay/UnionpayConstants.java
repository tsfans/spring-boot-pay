package cn.hl.pay.constants.unionpay;

public interface UnionpayConstants {

  /****** txnType ********/
  String txnType_consume = "01";
  String txnType_query = "00";
  String txnType_refund = "04";
  String txnType_consumeUndo = "31";
  
  /****** txnSubType ********/
  String txnSubType_default = "00";
  String txnSubType_selfConsume = "01";
  
  /****** bizType ********/
  String bizType_default = "000000";
  String bizType_gatewayPay = "000201";
  
  /****** channelType ********/
  String channelType_pc = "07";
  String channelType_mobile = "08";
  
  /****** currencyCode ********/
  String currencyCode_rmb = "156";
}
