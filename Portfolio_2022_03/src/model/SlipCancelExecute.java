package model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import inventoryEnum.InventoryEnum;

public class SlipCancelExecute {

	public static void cancelInventory(Connection con, PreparedStatement ps, String[] cancelSlipCodes)
			throws Exception {

		int[] resArray = new int[cancelSlipCodes.length];

		String sql = "UPDATE shipping_slip " +
				"INNER JOIN inventory ON  shipping_slip.sender = inventory.store_code " +
				"INNER JOIN slip_item_list ON shipping_slip.slip_code = slip_item_list.slip_code " +
				"SET inventory.shipment_pending = inventory.shipment_pending - slip_item_list.shipping_count, " +
				"inventory.inventory_count = inventory.inventory_count + slip_item_list.shipping_count " +
				"WHERE shipping_slip.slip_code = ? AND inventory.item_code = slip_item_list.item_code";

		ps = con.prepareStatement(sql);

		for(int i = 0; i < cancelSlipCodes.length; i++) {
			ps.setString(1, cancelSlipCodes[i]);

			resArray[i] = ps.executeUpdate();
		}

		//executeUpdateの戻り値がすべて1以上なら正常
		for (int num : resArray) {
			if(num <= 0) {
				throw new Exception();
			}
		}
	}

	public static void cancelShippingSlip(Connection con, PreparedStatement ps, String[] cancelSlipCodes)
			throws Exception {

		int[] resArray = new int[cancelSlipCodes.length];

		String sql = "UPDATE shipping_slip SET cancel = ? WHERE slip_code = ?";

		ps = con.prepareStatement(sql);

		for(int i = 0; i < cancelSlipCodes.length; i++) {

			ps.setInt(1, InventoryEnum.CANCEL.getFlag());

			ps.setString(2, cancelSlipCodes[i]);

			resArray[i] = ps.executeUpdate();
		}

		//executeUpdateの戻り値がすべて1以上なら正常
		for (int num : resArray) {
			if(num <= 0) {
				throw new Exception();
			}
		}

	}

}
