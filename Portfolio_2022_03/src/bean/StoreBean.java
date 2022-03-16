package bean;

public class StoreBean {

	private String storeName;
	private String storeCode;

	public StoreBean(String storeName, String storeCode) {
		this.storeName = storeName;
		this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public String getStoreCode() {
		return storeCode;
	}

}
