package inventoryEnum;

public enum InventoryEnum {

	SHIPPINGSCONFIRMATION(0),
	NONDECISION(0),
	DECISION(1),
	NONCANCEL(0),
	CANCEL(1),
	NONABOLITION(0),
	ABOLITION(1),
	NONWAREHOUSE(0),
	WAREHOUSE(1);




	private final int flag;


	private InventoryEnum(final int flag) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.flag = flag;

	}

	public int getFlag() {
		return this.flag;
	}



}
