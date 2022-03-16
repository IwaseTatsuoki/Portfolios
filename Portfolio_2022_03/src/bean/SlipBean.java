package bean;

public class SlipBean {

	private String slipCode;
	private String slipDate;
	private String receiverStoreName;

	public SlipBean(String slipCode, String slipDate, String receiverStoreName) {
		this.slipCode = slipCode;
		this.slipDate = slipDate;
		this.receiverStoreName = receiverStoreName;
	}

	public String getSlipCode() {
		return slipCode;
	}

	public String getSlipDate() {
		return slipDate;
	}

	public String getReceiverStoreName() {
		return receiverStoreName;
	}








}
