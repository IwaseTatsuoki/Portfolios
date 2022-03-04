package model;

public class InputBean {

	private String itemCode;
	private int itemCount;

	public InputBean(String itemCode, int itemCount) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.itemCode = itemCode;
		this.itemCount = itemCount;

	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public int getItemCount() {
		return itemCount;
	}

	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}




}
