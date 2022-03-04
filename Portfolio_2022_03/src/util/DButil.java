package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.SqlException;

public class DButil {

	public static void rollback(Connection con) throws SqlException {


		//一つでも処理がされない場合はロールバック
		try {

			con.rollback();

		}catch (SQLException e) {

			//画面をとめてそのまま
			JFrame jFrame = new JFrame();

			JOptionPane.showMessageDialog(jFrame, "重大なエラーが発生しました");

		}

	}



	public static void closeDB(ResultSet rs, PreparedStatement ps, Connection con) throws SqlException {


		try{

			//データベースに接続されていれば切断する
			if(rs != null) {
				rs.close();
			}

			if(ps != null) {
				ps.close();
			}

			if(con != null){
				con.close();
			}


		}catch(SQLException e){

			//画面をとめてそのまま
			JFrame jFrame = new JFrame();

			JOptionPane.showMessageDialog(jFrame, "重大なエラーが発生しました");

		}

	}

}
