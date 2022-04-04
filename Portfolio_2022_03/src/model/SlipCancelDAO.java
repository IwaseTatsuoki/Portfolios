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

import bean.SlipBean;
import inventoryEnum.ErroMesEnum;
import inventoryEnum.InventoryEnum;
import util.DButil;

public class SlipCancelDAO {

//	slipList.jsp伝票キャンセルの画面で取得した店舗コードの店舗が所持している伝票を取得するときに使う。
//	SlipListServletで呼び出し
	public List<SlipBean> getSlip (String storeCode) throws SqlException {

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

			String sql = "select * from shipping_slip "
					+ "inner join store_warehouse on "
					+ "shipping_slip.sending_address = store_warehouse.store_code "
					+ "where sender = ? and decision_flag = ? and cancel = ? ";

			ps = con.prepareStatement(sql);

			ps.setString(1, storeCode);

			ps.setInt(2, InventoryEnum.NONDECISION.getFlag());

			ps.setInt(3, InventoryEnum.NONCANCEL.getFlag());

			rs = ps.executeQuery();

			while(rs.next()) {

				String slipDate = new SimpleDateFormat("yyyy年MM月dd日").format(rs.getDate("slip_date"));

				SlipBean slipBean = new SlipBean(
						rs.getString("slip_code"),
						slipDate,
						rs.getString("store_name"));

				slipBeanList.add(slipBean);

			}

			con.commit();

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



	public void slipCancel (String[] cancelSlipCodes) throws SqlException {

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

			SlipCancelExecute.cancelInventory(con, ps, cancelSlipCodes);

			SlipCancelExecute.cancelShippingSlip(con, ps, cancelSlipCodes);


			con.commit();



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
