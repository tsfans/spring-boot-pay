package cn.hl.pay.constants.unionpay;

public enum PayWay {

	PC("PC,平板",1),MOBILE("手机",2);
	
	private String way;
	
	private int code;
	
	private PayWay(String way,int code) {
		this.way = way;
		this.code = code;
	}
	
	public int code() {
		return code;
	}
	
	@Override
	public String toString() {
		return way;
	}
}
