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

		//比較する伝票コードを取得
		String slipCode = request.getParameter("slipCode");


		//受け取った値をBeanにいれる（商品コードと出荷数）
		//入荷した商品のリスト
		List<ItemBean> arraivalItemBeanList = new LinkedList<ItemBean>();

		//入荷の画面で入力した商品コードと個数を配列で取得
		String[] itemCode = request.getParameterValues("itemCode" );
		String[] itemCount = request.getParameterValues("itemCount" );

		//itemcodeとitemCountは同じ数なのでどちらかの数だけ回す
		for (int i = 0; i < itemCount.length; i++) {

			//nullと空文字じゃない場合beanを生成しリストに加える
			//ブランクは入るがDBに登録する前にBeanの中身がDBに登録されているか確認するので問題ない
			if (itemCode[i] != null && !itemCode[i].isEmpty() && itemCount[i] != null && !itemCount[i].isEmpty()) {

				ItemBean inputBean = new ItemBean(itemCode[i], Integer.parseInt(itemCount[i]));

				arraivalItemBeanList.add(inputBean);
			}
		}


		ArraivalDAO arraivalDAO = new ArraivalDAO();

		try {

			//入荷時のDB操作を一括で行う
			boolean arraivalResult =  arraivalDAO.arraivalDB(slipCode, arraivalItemBeanList);

			//入荷した商品と出荷伝票に差異があればfalseがかえってくる
			if(!arraivalResult) {

				url = "arraivalInput.jsp";
				request.setAttribute("erroMess", ErroMesEnum.ARRAIVALMISMATCH.getMes());

			}else {

				request.setAttribute("erroMess", "成功");
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

		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);


	}

}
