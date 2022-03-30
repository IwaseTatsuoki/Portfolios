package bean;

//出荷と入荷の入力画面に表示するプルダウンで使うための情報を保持。
public class EntryItemBean {

	private String itemCode;
	private String itemName;

	public EntryItemBean(String itemCode, String itemName) {
		super();
		this.itemCode = itemCode;
		this.itemName = itemName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getItemName() {
		return itemName;
	}
}
