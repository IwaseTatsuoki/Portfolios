package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.InventoryBean;
import bean.StoreBean;
import inventoryEnum.ErroMesEnum;
import inventoryEnum.InventoryEnum;
import util.DButil;

public class InventoryDAO {

	public List<StoreBean> getStoreList() throws SqlException {

		List<StoreBean> storeBeanList = new LinkedList<StoreBean>();

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

			String sql = "SELECT store_code, store_name FROM store_warehouse WHERE abolition_flag = ?";

			ps = con.prepareStatement(sql);

			ps.setInt(1, InventoryEnum.NONABOLITION.getFlag());

			rs = ps.executeQuery();

			while(rs.next()) {

				StoreBean storeBean = new StoreBean(rs.getString("store_name"), rs.getString("store_code"));

				storeBeanList.add(storeBean);
			}

			return storeBeanList;

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

			String sql = "SELECT item.item_name,item.price,size.size_type, color.color_type, inventory.inventory_count, "
					+ "inventory.shipment_pending " +
					"FROM item INNER JOIN inventory ON inventory.item_code = item.item_code " +
					"INNER JOIN size ON item.size = size.size_code " +
					"INNER JOIN color ON item.color = color.color_code " +
					"WHERE store_code = ?";

			ps = con.prepareStatement(sql);

			ps.setString(1, storeCode);

			rs = ps.executeQuery();

			while(rs.next()) {

				InventoryBean inventoryBean = new InventoryBean(
						rs.getString("item_name"),
						rs.getInt("price"),
						rs.getString("size_type"),
						rs.getString("color_type"),
						rs.getInt("inventory_count"),
						rs.getInt("shipment_pending")
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