package cn.hl.pay.constants.unionpay;

public interface UnionpayConstants {

  /****** txnType ********/
  String txnType_default = "00";
  String txnType_consume = "01";
  String txnType_preAuth = "02";
  String txnType_authFinish = "03";
  String txnType_refund = "04";
  String txnType_consumeUndo = "31";
  String txnType_authUndo = "32";
  String txnType_authFinishUndo = "33";
  String txnType_fileTransfer = "76";
  
  /****** txnSubType ********/
  String txnSubType_default = "00";
  String txnSubType_01 = "01";
  
  /****** bizType ********/
  String bizType_default = "000000";
  String bizType_gatewayPay = "000201";
  
  /****** channelType ********/
  String channelType_pc = "07";
  String channelType_mobile = "08";
  
  /****** currencyCode ********/
  String currencyCode_rmb = "156";
  
  /****** fileType *******/
  String fileType_default = "00";
}
