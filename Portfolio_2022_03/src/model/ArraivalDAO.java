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

public class ArraivalDAO {

	public boolean arraivalDB(String slipCode, List<ItemBean> arraivalItemBeanList) throws SqlException {

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

			//伝票の商品コードと商品数を取得
			List<ItemBean> slipItemBeanList = ArraivalExcute.getSlipList(con, ps, rs, slipCode);

			//slipItemBeanListが入荷で入力したものと一致しているか確認
			boolean arraivalMatch = ArraivalMatch.checkMatch(slipItemBeanList, arraivalItemBeanList);

			if(!arraivalMatch) {
				return false;
			}

			//inventoryのshipment_pendingを入荷確定した分減らす
			ArraivalExcute.shipmentPendingUpdate(con, ps, slipCode, arraivalItemBeanList);

			//inventoryの在庫を増やす
			ArraivalExcute.inventoryCountUpdate(con, ps, slipCode, arraivalItemBeanList);

			//伝票の確定フラグを立てる
			ArraivalExcute.shippingShipDecision(con, ps, slipCode);

			con.commit();

			return true;

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
