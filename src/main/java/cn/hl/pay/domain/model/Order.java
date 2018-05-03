package cn.hl.pay.domain.model;

import java.io.Serializable;

/**
 * Product order information
 * @author HULIN
 */
public class Order implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String productId;// 商品ID
	private String subject;//订单名称 
	private String body;// 商品描述
	private String totalFee;// 总金额(单位是分)
	private String outTradeNo;// 订单号(唯一)
	private String spbillCreateIp;// 发起人IP地址
	private String attach;// 附件数据主要用于商户携带订单的自定义数据
	private Integer payType;// 支付类型(1:支付宝 2:微信 3:银联)
	private Integer payWay;// 支付方式 (1：PC,平板 2：手机)
	private String frontUrl;// 前台回调地址  非扫码支付使用
	
	public Order() {}
	
	@Override
	public String toString() {
		return "Order [productId=" + productId + ", subject=" + subject + ", body=" + body + ", totalFee=" + totalFee
				+ ", outTradeNo=" + outTradeNo + ", spbillCreateIp=" + spbillCreateIp + ", attach=" + attach
				+ ", payType=" + payType + ", payWay=" + payWay + ", frontUrl=" + frontUrl + "]";
	}

	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}
	public String getOutTradeNo() {
		return outTradeNo;
	}
	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}
	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}
	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	public String getFrontUrl() {
		return frontUrl;
	}
	public void setFrontUrl(String frontUrl) {
		this.frontUrl = frontUrl;
	}


}
