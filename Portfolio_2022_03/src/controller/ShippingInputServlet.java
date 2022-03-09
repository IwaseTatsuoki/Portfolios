package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ItemBean;
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
		String url = "index.jsp";

		//送り元店舗と送り先店舗を取得
		String sender = request.getParameter("sender");
		String sendingAddress = request.getParameter("sendingAddress");

		//受け取った値をinputBeanにいれる（商品コードと出荷数）
		//そのBeanをListにいれる
		List<ItemBean> slipItemBeanList = new LinkedList<ItemBean>();


		String[] itemCode = request.getParameterValues("itemCode" );
		String[] itemCount = request.getParameterValues("itemCount" );

		//ブランクは入る
		for (int i = 0; i < itemCount.length; i++) {


			if (itemCode[i] != null && !itemCode[i].isEmpty() && itemCount[i] != null && !itemCount[i].isEmpty()) {

				ItemBean inputBean = new ItemBean(itemCode[i], Integer.parseInt(itemCount[i]));

				slipItemBeanList.add(inputBean);
			}
		}


		ShippingDAO shippingDAO = new ShippingDAO();

		try {

			//DB更新、追加
			shippingDAO.shippingDB(slipItemBeanList,sender,sendingAddress);

		}catch (SqlException e) {

			// エラー内容表示
			e.printStackTrace();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

		}

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);


	}

}
