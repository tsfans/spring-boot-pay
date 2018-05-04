package cn.hl.pay.domain.model;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UnionpayConfig implements Serializable {

  private static final long serialVersionUID = 1L;

  @Value("${payment.unionpay.encoding}")
  private String encoding;

  @Value("${payment.unionpay.version}")
  private String version;

  @Value("${payment.unionpay.signMethod}")
  private String signMethod;

  @Value("${payment.unionpay.merId}")
  private String merId;

  @Value("${payment.unionpay.accessType}")
  private String accessType;
  
  @Value("${payment.unionpay.frontUrl}")
  private String frontUrl;
  
  @Value("${payment.unionpay.backUrl}")
  private String backUrl;

  public UnionpayConfig() {}

  @Override
  public String toString() {
    return "UnionpayConfig [encoding=" + encoding + ", version=" + version + ", signMethod="
        + signMethod + ", merId=" + merId + ", accessType=" + accessType + ", frontUrl=" + frontUrl
        + ", backUrl=" + backUrl + "]";
  }


  public String getEncoding() {
    return encoding;
  }

  public void setEncoding(String encoding) {
    this.encoding = encoding;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getSignMethod() {
    return signMethod;
  }

  public void setSignMethod(String signMethod) {
    this.signMethod = signMethod;
  }

  public String getMerId() {
    return merId;
  }

  public void setMerId(String merId) {
    this.merId = merId;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }


  public String getFrontUrl() {
    return frontUrl;
  }


  public void setFrontUrl(String frontUrl) {
    this.frontUrl = frontUrl;
  }


  public String getBackUrl() {
    return backUrl;
  }


  public void setBackUrl(String backUrl) {
    this.backUrl = backUrl;
  }

}
