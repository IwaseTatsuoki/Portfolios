package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.InputBean;
import model.ShippingDAO;
import model.SqlException;

/**
 * Servlet implementation class ShippingServlet
 */
public class ShippingInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShippingInputServlet() {
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
		request.setCharacterEncoding("UTF-8");

		//画面遷移URL
		String url = "shippingInput.jsp";

		//送り元店舗と送り先店舗を取得
		String sender = request.getParameter("sender");
		String sendingAddress = request.getParameter("sendingAddress");

		//受け取った値をinputBeanにいれる（商品コードと出荷数）
		//そのBeanをListにいれる
		List<InputBean> inputBeanList = new ArrayList<InputBean>();

		int i = 0;

		//番号順にとってきてなくなったら終了
		while(request.getParameter("itemCode_" + i) != null) {

			String itemCode = request.getParameter("itemCode_" + i);
			int itemCount = Integer.parseInt(request.getParameter("shippingCount_" + i));

			InputBean inputBean = new InputBean(itemCode,itemCount);

			inputBeanList.add(inputBean);

			i++;
		}


		ShippingDAO shippingDAO = new ShippingDAO();

		try {

			//DB更新、追加
			shippingDAO.shippingDB(inputBeanList,sender,sendingAddress);

		}catch (SqlException e) {

			// エラー内容表示
			e.printStackTrace();

			//エラー遷移先はとりあえず全部index.jsp
			url = e.getERRORURL();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

		}

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);


	}

}
