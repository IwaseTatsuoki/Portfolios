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
import util.DButil;

public class SlipCancelDAO {

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
					+ "where sender = ?";

			ps = con.prepareStatement(sql);

			ps.setString(1, storeCode);

			rs = ps.executeQuery();

			while(rs.next()) {
				String str = new SimpleDateFormat("yyyy年MM月dd日").format(rs.getDate("slip_date"));
				System.out.println(str);

				SlipBean slipBean = new SlipBean(
						rs.getString("slip_code"),
						str,
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

}
