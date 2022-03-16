package bean;

public class InventoryBean {

	//item.item_name,item.price,size.size_type, color.color_type, inventory.inventory_count, inventory.shipment_pending

	private String itemName;
	private int price;
	private String size;
	private String color;
	private int inventoryCount;
	private int shipmentPending;

	public InventoryBean(String itemName,
			int price,
			String size,
			String color,
			int inventoryCount,
			int shipmentPending ) {

		this.itemName = itemName;
		this.price = price;
		this.size = size;
		this.color = color;
		this.inventoryCount = inventoryCount;
		this.shipmentPending = shipmentPending;

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


}
