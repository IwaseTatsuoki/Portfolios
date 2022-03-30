package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.EntryItemBean;
import bean.StoreBean;
import inventoryEnum.ErroMesEnum;
import inventoryEnum.InventoryEnum;
import util.DButil;

public class ItemSlipGetDAO {

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


	public List<EntryItemBean> getItemList(String sender) throws SqlException {

		List<EntryItemBean> entryItemBeanList = new LinkedList<EntryItemBean>();

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

			String sql = "SELECT item_code, item_name FROM inventory"
					+ "INNER JOIN item on inventory.item_code = item.item_code"
					+ " WHERE inventory.store_code = ?  ";

			ps = con.prepareStatement(sql);

			ps.setString(1, sender);

			rs = ps.executeQuery();

			while(rs.next()) {

				EntryItemBean	entryItemBean = new EntryItemBean(rs.getString("item_code"), rs.getString("item_name"));

				entryItemBeanList.add(entryItemBean);
			}

			return entryItemBeanList;

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
