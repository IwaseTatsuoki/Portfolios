package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bean.ItemInfoBean;
import inventoryEnum.ErroMesEnum;
import util.DButil;

public class ItemInfoDAO {

	public ItemInfoBean getItemInfo() throws SqlException {

		ItemInfoBean itemInfoBean = new ItemInfoBean();


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

			itemInfoBean.setSizes(ItemInfoExecute.getSizes(con, ps, rs));

			itemInfoBean.setColors(ItemInfoExecute.getColors(con, ps, rs));

			itemInfoBean.setCategorys(ItemInfoExecute.getCategorys(con, ps, rs));

			itemInfoBean.setSexs(ItemInfoExecute.getSexs(con, ps, rs));

			con.commit();

			return itemInfoBean;



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
