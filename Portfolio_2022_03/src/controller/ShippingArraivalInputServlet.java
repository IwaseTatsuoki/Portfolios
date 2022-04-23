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

import bean.EntryItemBean;
import inventoryEnum.ErroMesEnum;
import model.SqlException;
import model.StoreItemSlipDAO;

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

		String flag = request.getParameter("flag");

		String sender = request.getParameter("sender");

		String slipCode = request.getParameter("slipCode");

		StoreItemSlipDAO storeItemSlipDAO = new StoreItemSlipDAO();

		String url = "index.jsp";


		try {

			List<EntryItemBean> entryItemBeanList = null;

			if(flag.equals("shipping")) {


				entryItemBeanList = storeItemSlipDAO.getEntryItemList(sender);

			}else if(flag.equals("arraival")) {

				entryItemBeanList = storeItemSlipDAO.getSlipItem(slipCode);

			}


			// JSON変換用のクラス
			ObjectMapper mapper = new ObjectMapper();

			//JSON文字列に変換
			String json = mapper.writeValueAsString(entryItemBeanList);

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


