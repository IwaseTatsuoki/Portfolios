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
import bean.SlipBean;
import bean.StoreBean;
import inventoryEnum.ErroMesEnum;
import inventoryEnum.InventoryEnum;
import util.DButil;

public class StoreItemSlipDAO {

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


	public List<EntryItemBean> getEntryItemList(String sender) throws SqlException {

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

			String sql = "SELECT inventory.item_code, item.item_name FROM inventory "
					+ "INNER JOIN item ON inventory.item_code = item.item_code "
					+ "WHERE inventory.store_code = ?  ";

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

	public List<SlipBean> getSlipAll() throws SqlException {

		List<SlipBean> slipBeanList = new LinkedList<SlipBean>();

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

			String sql = "SELECT shipping_slip.slip_code, store_warehouse.store_name " +
					"FROM shipping_slip " +
					"INNER JOIN store_warehouse " +
					"ON shipping_slip.sending_address = store_warehouse.store_code "+
					"WHERE decision_flag = 0 AND cancel = 0";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while(rs.next()) {

				SlipBean slipBean = new SlipBean();
				slipBean.setSlipCode(rs.getString("slip_code"));
				slipBean.setStoreName(rs.getString("store_name"));

				slipBeanList.add(slipBean);
			}

			return slipBeanList;

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

	public List<EntryItemBean> getSlipItem(String slipCode) throws SqlException {

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

			String sql = "SELECT slip_item_list.item_code, item.item_name " +
					"FROM slip_item_list " +
					"INNER JOIN item " +
					"ON slip_item_list.item_code = item.item_code " +
					"WHERE slip_item_list.slip_code = ?";

			ps = con.prepareStatement(sql);

			ps.setString(1, slipCode);

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
