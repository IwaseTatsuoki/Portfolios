package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.SlipCancelDAO;
import model.SqlException;

/**
 * Servlet implementation class SlipDeleteServlet
 */
public class SlipCancelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SlipCancelServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String[] cancelSlipCodes = request.getParameterValues("cancelCheck");

		String url = "index.jsp";

		SlipCancelDAO slipCancelDAO = new SlipCancelDAO();

		try {

			slipCancelDAO.slipCancel(cancelSlipCodes);

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
