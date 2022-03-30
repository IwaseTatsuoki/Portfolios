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

import bean.SlipBean;
import inventoryEnum.ErroMesEnum;
import model.SlipCancelDAO;
import model.SqlException;

/**
 * Servlet implementation class ShippingArraivalInputServlet
 */
public class ShippingArraivalInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShippingArraivalInputServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String storeCode = request.getParameter("sender");

		SlipCancelDAO slipCancelDAO = new SlipCancelDAO();

		String url = "index.jsp";


		try {

			//入荷時のDB操作を一括で行う
			List<SlipBean> slipBeanList = slipCancelDAO.getSlip(storeCode);


			// JSON変換用のクラス
			ObjectMapper mapper = new ObjectMapper();

			//JSON文字列に変換
			String json = mapper.writeValueAsString(slipBeanList);

			//ヘッダ設定
			response.setContentType("application/json;charset=UTF-8");   //JSON形式, UTF-8

			//pwオブジェクト
			PrintWriter pw = response.getWriter();

			//出力
			pw.write(json);

			//クローズ
			pw.close();


		}catch (SqlException e) {

			// エラー内容表示
			e.printStackTrace();
			e.getMessage();


			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
			e.getMessage();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", ErroMesEnum.JSONERRORMES.getMes());

			RequestDispatcher rd = request.getRequestDispatcher(url);
			rd.forward(request, response);

		}

	}

}


