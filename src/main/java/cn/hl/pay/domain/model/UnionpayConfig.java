package cn.hl.pay.domain.model;

import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UnionpayConfig implements Serializable{

	private static final long serialVersionUID = 1L;

	@Value("${payment.unionpay.encoding}")
	private String encoding;
	
	@Value("${payment.unionpay.version}")
	private String version;
	
	@Value("${payment.unionpay.signMethod}")
	private String signMethod;
	
	@Value("${payment.unionpay.txnType}")
	private String txnType;
	
	@Value("${payment.unionpay.txnSubType}")
	private String txnSubType;
	
	@Value("${payment.unionpay.bizType}")
	private String bizType;
	
	private String channelType;
	
	@Value("${payment.unionpay.merId}")
	private String merId;
	
	@Value("${payment.unionpay.accessType}")
	private String accessType;
	
	private String orderId;
	
	private String txnTime;
	
	private String queryId;
	
	@Value("${payment.unionpay.currencyCode}")
	private String currencyCode;
	
	private String txnAmt;//交易金额，单位分，不带小数点
	
	@Value("${payment.unionpay.frontUrl}")
	private String frontUrl;
	
	@Value("${payment.unionpay.backUrl}")
	private String backUrl;
	
	private String reqReserved;//自定义参数

	public UnionpayConfig() {}

	@Override
  public String toString() {
    return "UnionpayConfig [encoding=" + encoding + ", version=" + version + ", signMethod="
        + signMethod + ", txnType=" + txnType + ", txnSubType=" + txnSubType + ", bizType="
        + bizType + ", channelType=" + channelType + ", merId=" + merId + ", accessType="
        + accessType + ", orderId=" + orderId + ", txnTime=" + txnTime + ", currencyCode="
        + currencyCode + ", txnAmt=" + txnAmt + ",queryId="+queryId+", frontUrl=" + frontUrl + ", backUrl=" + backUrl
        + ", reqReserved=" + reqReserved + "]";
  }


  public String getQueryId() {
    return queryId;
  }

  public void setQueryId(String queryId) {
    this.queryId = queryId;
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

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public String getReqReserved() {
		return reqReserved;
	}

	public void setReqReserved(String reqReserved) {
		this.reqReserved = reqReserved;
	}
	
	
}
