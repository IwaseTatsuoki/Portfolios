package bean;

//伝票情報保持
public class SlipBean {

	private String slipCode;
	private String slipDate;
	//	cancelの時は送り元店舗。
	//	出荷時の伝票一覧持ってくるときStoreItemSlipDAOでは受け取り店舗。
	private String storeName;

	public SlipBean() {

	}

	public SlipBean(String slipCode, String slipDate, String storeName) {
		super();
		this.slipCode = slipCode;
		this.slipDate = slipDate;
		this.storeName = storeName;
	}

	public String getSlipCode() {
		return slipCode;
	}

	public void setSlipCode(String slipCode) {
		this.slipCode = slipCode;
	}

	public String getSlipDate() {
		return slipDate;
	}

	public void setSlipDate(String slipDate) {
		this.slipDate = slipDate;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}







}
