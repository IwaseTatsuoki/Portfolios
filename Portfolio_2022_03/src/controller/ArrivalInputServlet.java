package controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ItemBean;
import inventoryEnum.ErroMesEnum;
import model.ArraivalDAO;
import model.SqlException;

/**
 * Servlet implementation class ArrivalInputServlet
 */
public class ArrivalInputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArrivalInputServlet() {
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
		HttpSession session =  request.getSession();

		//画面遷移URL
		String url = "index.jsp";

		//比較する伝票コードを取得
		//,の前の伝票コードのみ切り出し
		String slipCode = request.getParameter("slipCode").split(",")[0];

		//受け取った値をBeanにいれる（商品コードと出荷数）
		//入荷した商品のリスト
		List<ItemBean> arraivalItemBeanList = new LinkedList<ItemBean>();

		//入荷の画面で入力した商品コードと個数を配列で取得
		String[] itemCode = request.getParameterValues("itemCode" );
		String[] itemCount = request.getParameterValues("itemCount" );

		//itemcodeとitemCountは同じ数なのでどちらかの数だけ回す
		for (int i = 0; i < itemCount.length; i++) {

			ItemBean inputBean = new ItemBean(itemCode[i], Integer.parseInt(itemCount[i]));

			arraivalItemBeanList.add(inputBean);

		}

		ArraivalDAO arraivalDAO = new ArraivalDAO();

		try {

			//入荷時のDB操作を一括で行う
			boolean arraivalResult =  arraivalDAO.arraivalDB(slipCode, arraivalItemBeanList);

			//入荷した商品と出荷伝票に差異があればfalseがかえってくる
			if(!arraivalResult) {

				url = "StoreListServlet?url=arraivalInput.jsp";
				session.setAttribute("erroMess", ErroMesEnum.ARRAIVALMISMATCH.getMes());

			}else {

				session.setAttribute("success","成功");
			}

		}catch (SqlException e) {

			// エラー内容表示
			e.printStackTrace();
			e.getMessage();

			//エラー遷移先はとりあえず全部index.jsp
			url = e.getERRORURL();

			//エラーメッセージを渡す
			request.setAttribute("erroMess", e.getERRORMESS());

		}

		//更新押したときに同じ処理を避けるため。
		response.sendRedirect(url);


	}

}
