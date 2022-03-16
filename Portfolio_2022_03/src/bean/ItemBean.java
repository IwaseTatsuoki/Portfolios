package bean;

public class ItemBean {

	private String itemCode;
	private int itemCount;

	public ItemBean(String itemCode, int itemCount) {

		this.itemCode = itemCode;

		this.itemCount = itemCount;

	}

	public String getItemCode() {

		return itemCode;

	}

	public int getItemCount() {

		return itemCount;

	}

	public boolean equals(ItemBean obj) {

		return this.itemCode.equals(obj.getItemCode()) && this.itemCount == obj.getItemCount();

	}



}
