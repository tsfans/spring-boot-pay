package cn.hl.pay.util.unionpay.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SDKConfig {


  /** 前台请求URL. */
  @Value("${acpsdk.frontTransUrl}")
  private String frontRequestUrl;
  
  /** 后台请求URL. */
  @Value("${acpsdk.backTransUrl}")
  private String backRequestUrl;
  
  /** 单笔查询 */
  @Value("${acpsdk.singleQueryUrl}")
  private String singleQueryUrl;
  
  /** 批量查询 */
  @Value("${acpsdk.batchQueryUrl}")
  private String batchQueryUrl;
  
  /** 批量交易 */
  @Value("${acpsdk.batchTransUrl}")
  private String batchTransUrl;
  
  /** 文件传输 */
  @Value("${acpsdk.fileTransUrl}")
  private String fileTransUrl;
  
  /** 签名证书路径. */
  @Value("${acpsdk.signCert.path}")
  private String signCertPath;
  
  /** 签名证书密码. */
  @Value("${acpsdk.signCert.pwd}")
  private String signCertPwd;
  
  /** 签名证书类型. */
  @Value("${acpsdk.signCert.type}")
  private String signCertType;
  
  /** 加密公钥证书路径. */
  @Value("${acpsdk.encryptCert.path}")
  private String encryptCertPath;
  
  /** 验证签名公钥证书目录. */
  @Value("${acpsdk.validateCert.dir}")
  private String validateCertDir;
  
  /** 按照商户代码读取指定签名证书目录. */
  @Value("${acpsdk.signCert.path}")
  private String signCertDir;
  
  /** 磁道加密证书路径. */
//  @Value("${acpsdk.encryptTrackCert.path}")
  private String encryptTrackCertPath;
  
  /** 磁道加密公钥模数. */
//  @Value("${acpsdk.encryptTrackKey.modulus}")
  private String encryptTrackKeyModulus;
  
  /** 磁道加密公钥指数. */
//  @Value("${acpsdk.encryptTrackKey.exponent}")
  private String encryptTrackKeyExponent;
  
  /** 有卡交易. */
  @Value("${acpsdk.cardTransUrl}")
  private String cardRequestUrl;
  
  /** app交易 */
  @Value("${acpsdk.appTransUrl}")
  private String appRequestUrl;
  
  /** 证书使用模式(单证书/多证书) */
  @Value("${acpsdk.singleMode}")
  private String singleMode;

  /* 缴费相关地址 */
  @Value("${acpsdk.jfFrontTransUrl}")
  private String jfFrontRequestUrl;
  
  @Value("${acpsdk.jfBackTransUrl}")
  private String jfBackRequestUrl;
  
  @Value("${acpsdk.jfSingleQueryUrl}")
  private String jfSingleQueryUrl;
  
  @Value("${acpsdk.jfCardTransUrl}")
  private String jfCardRequestUrl;
  
  @Value("${acpsdk.jfAppTransUrl}")
  private String jfAppRequestUrl;

  public SDKConfig() {
      super();
  }

  @Override
  public String toString() {
    return "SDKConfig [frontRequestUrl=" + frontRequestUrl + ", backRequestUrl=" + backRequestUrl
        + ", singleQueryUrl=" + singleQueryUrl + ", batchQueryUrl=" + batchQueryUrl
        + ", batchTransUrl=" + batchTransUrl + ", fileTransUrl=" + fileTransUrl + ", signCertPath="
        + signCertPath + ", signCertPwd=" + signCertPwd + ", signCertType=" + signCertType
        + ", encryptCertPath=" + encryptCertPath + ", validateCertDir=" + validateCertDir
        + ", signCertDir=" + signCertDir + ", encryptTrackCertPath=" + encryptTrackCertPath
        + ", encryptTrackKeyModulus=" + encryptTrackKeyModulus + ", encryptTrackKeyExponent="
        + encryptTrackKeyExponent + ", cardRequestUrl=" + cardRequestUrl + ", appRequestUrl="
        + appRequestUrl + ", singleMode=" + singleMode + ", jfFrontRequestUrl=" + jfFrontRequestUrl
        + ", jfBackRequestUrl=" + jfBackRequestUrl + ", jfSingleQueryUrl=" + jfSingleQueryUrl
        + ", jfCardRequestUrl=" + jfCardRequestUrl + ", jfAppRequestUrl=" + jfAppRequestUrl + "]";
  }

  public String getFrontRequestUrl() {
    return frontRequestUrl;
  }

  public void setFrontRequestUrl(String frontRequestUrl) {
    this.frontRequestUrl = frontRequestUrl;
  }

  public String getBackRequestUrl() {
    return backRequestUrl;
  }

  public void setBackRequestUrl(String backRequestUrl) {
    this.backRequestUrl = backRequestUrl;
  }

  public String getSignCertPath() {
    return signCertPath;
  }

  public void setSignCertPath(String signCertPath) {
    this.signCertPath = signCertPath;
  }

  public String getSignCertPwd() {
    return signCertPwd;
  }

  public void setSignCertPwd(String signCertPwd) {
    this.signCertPwd = signCertPwd;
  }

  public String getSignCertType() {
    return signCertType;
  }

  public void setSignCertType(String signCertType) {
    this.signCertType = signCertType;
  }

  public String getEncryptCertPath() {
    return encryptCertPath;
  }

  public void setEncryptCertPath(String encryptCertPath) {
    this.encryptCertPath = encryptCertPath;
  }

  public String getValidateCertDir() {
    return validateCertDir;
  }

  public void setValidateCertDir(String validateCertDir) {
    this.validateCertDir = validateCertDir;
  }

  public String getSingleQueryUrl() {
    return singleQueryUrl;
  }

  public void setSingleQueryUrl(String singleQueryUrl) {
    this.singleQueryUrl = singleQueryUrl;
  }

  public String getBatchQueryUrl() {
    return batchQueryUrl;
  }

  public void setBatchQueryUrl(String batchQueryUrl) {
    this.batchQueryUrl = batchQueryUrl;
  }

  public String getBatchTransUrl() {
    return batchTransUrl;
  }

  public void setBatchTransUrl(String batchTransUrl) {
    this.batchTransUrl = batchTransUrl;
  }

  public String getFileTransUrl() {
    return fileTransUrl;
  }

  public void setFileTransUrl(String fileTransUrl) {
    this.fileTransUrl = fileTransUrl;
  }

  public String getSignCertDir() {
    return signCertDir;
  }

  public void setSignCertDir(String signCertDir) {
    this.signCertDir = signCertDir;
  }

  public String getCardRequestUrl() {
    return cardRequestUrl;
  }

  public void setCardRequestUrl(String cardRequestUrl) {
    this.cardRequestUrl = cardRequestUrl;
  }

  public String getAppRequestUrl() {
    return appRequestUrl;
  }

  public void setAppRequestUrl(String appRequestUrl) {
    this.appRequestUrl = appRequestUrl;
  }

  public String getEncryptTrackCertPath() {
    return encryptTrackCertPath;
  }

  public void setEncryptTrackCertPath(String encryptTrackCertPath) {
    this.encryptTrackCertPath = encryptTrackCertPath;
  }

  public String getJfFrontRequestUrl() {
    return jfFrontRequestUrl;
  }

  public void setJfFrontRequestUrl(String jfFrontRequestUrl) {
    this.jfFrontRequestUrl = jfFrontRequestUrl;
  }

  public String getJfBackRequestUrl() {
    return jfBackRequestUrl;
  }

  public void setJfBackRequestUrl(String jfBackRequestUrl) {
    this.jfBackRequestUrl = jfBackRequestUrl;
  }

  public String getJfSingleQueryUrl() {
    return jfSingleQueryUrl;
  }

  public void setJfSingleQueryUrl(String jfSingleQueryUrl) {
    this.jfSingleQueryUrl = jfSingleQueryUrl;
  }

  public String getJfCardRequestUrl() {
    return jfCardRequestUrl;
  }

  public void setJfCardRequestUrl(String jfCardRequestUrl) {
    this.jfCardRequestUrl = jfCardRequestUrl;
  }

  public String getJfAppRequestUrl() {
    return jfAppRequestUrl;
  }

  public void setJfAppRequestUrl(String jfAppRequestUrl) {
    this.jfAppRequestUrl = jfAppRequestUrl;
  }

  public String getSingleMode() {
    return singleMode;
  }

  public void setSingleMode(String singleMode) {
    this.singleMode = singleMode;
  }

  public String getEncryptTrackKeyExponent() {
    return encryptTrackKeyExponent;
  }

  public void setEncryptTrackKeyExponent(String encryptTrackKeyExponent) {
    this.encryptTrackKeyExponent = encryptTrackKeyExponent;
  }

  public String getEncryptTrackKeyModulus() {
    return encryptTrackKeyModulus;
  }

  public void setEncryptTrackKeyModulus(String encryptTrackKeyModulus) {
    this.encryptTrackKeyModulus = encryptTrackKeyModulus;
  }



}
