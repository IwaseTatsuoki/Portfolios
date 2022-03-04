package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import util.DButil;


public class ShippingDAO {

	public void shippingDB(List<InputBean> inputBeanList, String sender, String sendingAddress)
			throws SqlException {

		//sqlを実行するのに使うクラス
		ShippingExecute shippingExecute = new ShippingExecute();

		//slip_codeの最大値の数字を入れる
		String maxSlipCode = null;

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
			shippingExecute.inventoryUpdate(con, ps, inputBeanList, sender);

			//伝票の連番を取得
			maxSlipCode = shippingExecute.getMaxSlipCode(con, ps, rs);

//
			shippingExecute.shippingSlipInsert(con, ps, maxSlipCode, sender, sendingAddress);


			shippingExecute.slipItemListInsert(con, ps, maxSlipCode, inputBeanList);

			con.commit();


		}catch(Exception ex){


			DButil.rollback(con);

			SqlException sqlException = new SqlException("DBエラー");

			sqlException.addSuppressed(ex);

			throw sqlException;


		}finally{

			DButil.closeDB(rs, ps, con);
		}


	}


}
