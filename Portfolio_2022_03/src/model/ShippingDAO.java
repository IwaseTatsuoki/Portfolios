package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.ItemBean;
import inventoryEnum.ErroMesEnum;
import util.DButil;


public class ShippingDAO {

	public void shippingDB(List<ItemBean> slipItemBeanList, String sender, String sendingAddress, String uuidString)
			throws SqlException {

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

			//inventoryの在庫数と未確定在庫を更新
			ShippingExecute.inventoryUpdate(con, ps, slipItemBeanList, sender);

			ShippingExecute.shippingSlipInsert(con, ps, uuidString, sender, sendingAddress);

			ShippingExecute.slipItemListInsert(con, ps, uuidString, slipItemBeanList);

			con.commit();


		}catch(Exception ex){


			DButil.rollback(con);

			SqlException sqlException = new SqlException(ErroMesEnum.DBERRORMES.getMes());

			sqlException.addSuppressed(ex);

			throw sqlException;

		}finally{

			DButil.closeDB(rs, ps, con);
		}
	}
}
