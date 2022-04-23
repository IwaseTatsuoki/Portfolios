package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

import bean.ItemBean;

public class ShippingExecute  {

	public static void inventoryUpdate (Connection con, PreparedStatement ps, List<ItemBean> inputBeanList, String sender)
			throws Exception {

		int[] resArray = new int[inputBeanList.size()];

		//SQLの作成
		String sql = "update inventory set "
				+ "inventory_count = inventory_count - ?, "
				+ "shipment_pending = shipment_pending + ? "
				+ "where store_code = ? and item_code = ?";

		ps = con.prepareStatement(sql);

		for (int i = 0; i < inputBeanList.size(); i++) {

			//値のセット
			ps.setInt(1, inputBeanList.get(i).getItemCount());
			ps.setInt(2, inputBeanList.get(i).getItemCount());
			ps.setString(3, sender);
			ps.setString(4, inputBeanList.get(i).getItemCode());

			//クエリの実行
			resArray[i] = ps.executeUpdate();

		}

		//executeUpdateの戻り値がすべて1なら正常
		for (int num : resArray) {
			if(num <= 0) {
				throw new Exception();
			}
		}
	}

	public static void shippingSlipInsert(Connection con, PreparedStatement ps, String uuidString, String sender, String sendingAddress)
			throws Exception {

		//更新件数を受け取る
		int res;

		//sqlのdateを作成
		long javaDate = new Date().getTime();
		java.sql.Date sqlDate = new java.sql.Date(javaDate);


		String sql = "insert into "
				+ "shipping_slip (slip_code, slip_date, sender, sending_address) "
				+ "values (?, ?, ?, ?)";

		ps = con.prepareStatement(sql);

		ps.setString(1, uuidString);
		ps.setDate(2, sqlDate);
		ps.setString(3, sender);
		ps.setString(4, sendingAddress);

		res = ps.executeUpdate();

		if(res <= 0) {

			throw new Exception();

		}
	}

	public static void slipItemListInsert(Connection con, PreparedStatement ps, String uuidString, List<ItemBean> inputBeanList)
			throws Exception{

		int[] resArray = new int[inputBeanList.size()];

		String	sql =  "insert into "
				+ "slip_item_list (slip_code, item_code, shipping_count) "
				+ "values (?, ?, ?)";

		ps = con.prepareStatement(sql);


		for (int i = 0; i < inputBeanList.size(); i++) {

			ps.setString(1, uuidString);
			ps.setString(2, inputBeanList.get(i).getItemCode());
			ps.setInt(3, inputBeanList.get(i).getItemCount());

			resArray[i] = ps.executeUpdate();

		}

		//executeUpdateの戻り値がすべて1なら正常
		for (int num : resArray) {
			if(num <= 0) {
				throw new Exception();
			}
		}
	}
}
