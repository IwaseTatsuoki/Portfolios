package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.InventoryBean;
import inventoryEnum.ErroMesEnum;
import util.DButil;

public class InventoryDAO {

	public List<InventoryBean> getInventoryList(String storeCode) throws SqlException {

		List<InventoryBean> inventoryBeanList = new LinkedList<InventoryBean>();

		//DB取得結果を格納するリスト
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			//コンテキストの取得
			Context context = new InitialContext();
			//データソースの指定
			DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/inventorymanagement");
			//コネクションの取得
			con = ds.getConnection();

			//オートコミット無効
			con.setAutoCommit(false);

			String sql = "SELECT item. item_name, item.price,size.size_type, color.color_type,item.best_before, "+
					"inventory.inventory_count, inventory.shipment_pending, "+
					"category.category_type, sex.sex_type " +
					"FROM item "+
					"INNER JOIN inventory ON inventory.item_code = item.item_code " +
					"INNER JOIN size ON item.size = size.size_code " +
					"INNER JOIN color ON item.color = color.color_code " +
					"INNER JOIN category ON item.category_code = category.category_code " +
					"INNER JOIN sex ON item.sex_code = sex.sex_code " +
					"WHERE store_code = ?";

			ps = con.prepareStatement(sql);

			ps.setString(1, storeCode);

			rs = ps.executeQuery();

			while(rs.next()) {

				String slipDate = new SimpleDateFormat("yyyy年MM月dd日").format(rs.getDate("best_before"));

				InventoryBean inventoryBean = new InventoryBean(
						rs.getString("item_name"),
						rs.getInt("price"),
						rs.getString("size_type"),
						rs.getString("color_type"),
						rs.getInt("inventory_count"),
						rs.getInt("shipment_pending"),
						rs.getString("category_type"),
						rs.getString("sex_type"),
						slipDate
						);

				inventoryBeanList.add(inventoryBean);

			}

			return inventoryBeanList;

		}catch(Exception ex){

			//DB登録中にエラーが出ればロールバック
			DButil.rollback(con);

			SqlException sqlException = new SqlException(ErroMesEnum.DBERRORMES.getMes());

			sqlException.addSuppressed(ex);

			throw sqlException;

		}finally{

			DButil.closeDB(rs, ps, con);

		}
	}
}
