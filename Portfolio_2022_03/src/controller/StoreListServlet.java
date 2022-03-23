package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ItemInfoBean;
import bean.StoreBean;
import model.InventoryDAO;
import model.ItemInfoDAO;
import model.SqlException;

/**
 * Servlet implementation class StoreListServlet
 */
public class StoreListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StoreListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String nextPage = request.getParameter("next");
		String url = null;

		switch (nextPage) {

		case "inventoryList":

			url = "inventoryList.jsp";

			try {

				ItemInfoBean itemInfoBean = new ItemInfoDAO().getItemInfo();

				request.setAttribute("itemInfoBean", itemInfoBean);

			}catch(SqlException e) {

				// エラー内容表示
				e.printStackTrace();

				//エラーメッセージを渡す
				request.setAttribute("erroMess", e.getERRORMESS());

				//エラー時遷移先
				url = e.getERRORURL();
			}


			break;

		case "slipList":

			url = "slipList.jsp";


			break;
		}

		try {

			List<StoreBean> storeBeanList = new InventoryDAO().getStoreList();

			request.setAttribute("storeBeanList", storeBeanList);

		}catch(SqlException e) {

			// エラー内容表示
			e.printStackTrace();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

			//エラー時遷移先
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
