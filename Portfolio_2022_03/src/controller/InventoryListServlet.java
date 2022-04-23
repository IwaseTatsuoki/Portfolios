package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bean.InventoryBean;
import inventoryEnum.ErroMesEnum;
import model.InventoryDAO;
import model.SqlException;

/**
 * Servlet implementation class InventoryListServlet
 */
public class InventoryListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InventoryListServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String url = "index.jsp" ;

		String storeCode = request.getParameter("storeCode");

		try {

			InventoryDAO inventoryDAO = new InventoryDAO();

			List<InventoryBean> inventoryBeanList = inventoryDAO.getInventoryList(storeCode);

			// JSON変換用のクラス
			ObjectMapper mapper = new ObjectMapper();

			//JSON文字列に変換
			String json = mapper.writeValueAsString(inventoryBeanList);

			//ヘッダ設定
			response.setContentType("application/json;charset=UTF-8");   //JSON形式, UTF-8

			//pwオブジェクト
			PrintWriter pw = response.getWriter();

			//出力
			pw.write(json);

			//クローズ
			pw.close();

		} catch (SqlException e) {

			// エラー内容表示
			e.printStackTrace();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

			url = e.getERRORURL();

			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);

		}catch (JsonProcessingException e) {
			e.printStackTrace();
			e.getMessage();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", ErroMesEnum.JSONERRORMES.getMes());

			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);

		}

	}

}
