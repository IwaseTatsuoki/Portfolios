package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import bean.ItemBean;
import inventoryEnum.InventoryEnum;

public class ArraivalExcute {

	//入力した伝票コードと入荷商品が一致しているか見るために伝票コードに対応した商品を取得
	public static List<ItemBean> getSlipList(Connection con, PreparedStatement ps, ResultSet rs, String slipCode)
			throws Exception {

		//入荷処理時に入力した伝票コードの商品を入れるリスト
		List<ItemBean> slipItemBeanList = new LinkedList<ItemBean>();

		String sql = "select item_code, shipping_count from slip_item_list where slip_code = ?";

		ps = con.prepareStatement(sql);

		ps.setString(1, slipCode);

		rs = ps.executeQuery();

		//伝票に記載の商品情報をBeanに入れ生成しそれをリストに入れる
		while (rs.next()) {

			ItemBean slipItemBean = new ItemBean(rs.getString("item_code"), rs.getInt("shipping_count"));

			slipItemBeanList.add(slipItemBean);

		}

		//存在しない伝票コードを指定するとリストのサイズが0になる
		//ArraivalMatchで入荷商品をサイズ0のリストと比較するので当然一致しなくてやり直しになる
		//なのでここでは0だろうが何もしない
		//仮に指定した伝票コードが間違っていることを知らせたい場合には別途考える必要あり
		//呼び出し元で戻り値のリストのサイズ見て0ならエラー出すようにしようとも考えたが
		//リスト=0はエラーという暗黙のルールができるので避けた方がいいかなと思った
		return slipItemBeanList;

	}

	//inventoryテーブルの未確定在庫を入荷分だけ引く
	public static void shipmentPendingUpdate(
			Connection con,
			PreparedStatement ps,
			String slipCode,
			List<ItemBean> arraivalItemBeanList
			) throws Exception {

		int res = 0;

		//リストの商品の数だけアップデートする
		for (ItemBean itemBean : arraivalItemBeanList) {

			//shipping_slipとinventoryを内部結合し、そのテーブルの伝票コードと商品コードで行を確定
			String sql = "UPDATE shipping_slip " +
					"INNER JOIN inventory ON shipping_slip.sender = inventory.store_code " +
					"SET inventory.shipment_pending = inventory.shipment_pending - ? " +
					"WHERE shipping_slip.slip_code = ? and inventory.item_code = ?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, itemBean.getItemCount());

			ps.setString(2, slipCode);

			ps.setString(3, itemBean.getItemCode());

			res = ps.executeUpdate();

			if(res <= 0) {
				throw new Exception();
			}
		}
	}

	//入荷先の商品の在庫個数を変更
	public static void inventoryCountUpdate(
			Connection con,
			PreparedStatement ps,
			String slipCode,
			List<ItemBean> arraivalItemBeanList
			) throws Exception {

		int res = 0;

		for (ItemBean itemBean : arraivalItemBeanList) {

			//shipmentPendingUpdateとほぼ一緒
			String sql = "UPDATE shipping_slip " +
					"INNER JOIN inventory ON shipping_slip.sending_address = inventory.store_code " +
					"SET inventory.inventory_count = inventory.inventory_count + ? " +
					"WHERE shipping_slip.slip_code = ?  and inventory.item_code = ?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, itemBean.getItemCount());

			ps.setString(2, slipCode);

			ps.setString(3, itemBean.getItemCode());

			res = ps.executeUpdate();

			if(res <= 0) {
				throw new Exception();
			}
		}
	}

	//入荷が確定したら伝票の確定フラグを立てる
	public static void shippingShipDecision(
			Connection con,
			PreparedStatement ps,
			String slipCode
			) throws Exception {

		int res = 0;

		String sql = "update shipping_slip set shipping_slip.decision_flag = ? WHERE shipping_slip.slip_code = ?";

		ps = con.prepareStatement(sql);

		//enumの確定定数（1）
		ps.setInt(1, InventoryEnum.DECISION.getFlag());

		ps.setString(2, slipCode);

		res = ps.executeUpdate();

		//更新できていなければエラー
		if(res <= 0) {
			throw new Exception();
		}
	}
}
