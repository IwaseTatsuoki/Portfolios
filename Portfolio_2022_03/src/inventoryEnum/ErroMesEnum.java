package inventoryEnum;

public enum ErroMesEnum {


	DBERRORMES("DBエラー"),
	NULLPOINTEERORMES("値が正しく入力されていません"),
	ARRAIVALMISMATCH("入荷物と出荷伝票に誤差があります"),
	JSONERRORMES("Jsonに変換失敗しました。");

	private final String mes;

	private ErroMesEnum(final String mes) {
		this.mes = mes;
	}

	public String getMes() {
		return this.mes;
	}
}
