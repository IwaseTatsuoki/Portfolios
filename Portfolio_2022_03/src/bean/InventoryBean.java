package bean;

//inventoryList.jspで表示する在庫情報を保持。
public class InventoryBean {

	private String itemName;
	private int price;
	private String size;
	private String color;
	private String categoryName;
	private String sexType;
	private String bestBefore;
	private int inventoryCount;
	private int shipmentPending;

	public InventoryBean(
			String itemName,
			int price,
			String size,
			String color,
			int inventoryCount,
			int shipmentPending,
			String categoryName,
			String sexType,
			String bestBefore
			) {

		this.itemName = itemName;
		this.price = price;
		this.size = size;
		this.color = color;
		this.inventoryCount = inventoryCount;
		this.shipmentPending = shipmentPending;
		this.categoryName = categoryName;
		this.sexType = sexType;
		this.bestBefore = bestBefore;
	}

	public String getItemName() {
		return itemName;
	}

	public int getPrice() {
		return price;
	}

	public String getSize() {
		return size;
	}

	public String getColor() {
		return color;
	}

	public int getInventoryCount() {
		return inventoryCount;
	}

	public int getShipmentPending() {
		return shipmentPending;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public String getSexType() {
		return sexType;
	}

	public String getBestBefore() {
		return bestBefore;
	}

}
