package inventoryEnum;

public enum ErroMesEnum {


	DBERRORMES("DBエラー"),
	NULLPOINTEERORMES("値が正しく入力されていません"),
	ARRAIVALMISMATCH("入荷物と出荷伝票に誤差があります");

	private final String mes;

	private ErroMesEnum(final String mes) {
		// TODO 自動生成されたコンストラクター・スタブ

		this.mes = mes;
	}

	public String getMes() {
		return this.mes;
	}
}
