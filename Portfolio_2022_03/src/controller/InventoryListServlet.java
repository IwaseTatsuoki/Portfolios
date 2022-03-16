package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.InventoryBean;
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String url = "inventoryList.jsp";

		String storeCode = request.getParameter("storeCode");

		try {

			InventoryDAO inventoryDAO = new InventoryDAO();

			List<InventoryBean> inventoryBeanList = inventoryDAO.getInventoryList(storeCode);

			request.setAttribute("inventoryBeanList", inventoryBeanList);

		} catch (SqlException e) {

			// エラー内容表示
			e.printStackTrace();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

			url = e.getERRORURL();

		}


		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
