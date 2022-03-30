package bean;

//出荷と入荷時に入力された商品のコードと個数を保持
//db登録時に使う入れ物
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

//	入荷処理時に指定した伝票の商品コードと個数が入力されたものと一致しているか確認用のメソッド。
//	引数で入力された伝票の商品情報を受け取り自分の（入荷で入力された商品情報）と比較
	public boolean equals(ItemBean obj) {
		return this.itemCode.equals(obj.getItemCode()) && this.itemCount == obj.getItemCount();

	}



}
