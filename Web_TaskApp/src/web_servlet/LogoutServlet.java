package web_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businesslogic.BusinessLogic;
import businesslogic.BusinessLogicFactory;
import ui.Ui;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//ビジネスロジックのファクトリークラスを取得
	BusinessLogicFactory logicFactory = new BusinessLogicFactory();
	//ロジックを取得
	BusinessLogic logic = logicFactory.getLogic("taskApp");
	//次画面を格納する変数
	String url = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
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
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().append("Served at:").append(request.getContextPath());

		//ログアウト
		logic.logout();

		//sessionを取得
		HttpSession session = request.getSession();

		//セッションの破棄
		session.invalidate();
		System.out.println("セッションの破棄を完了しました");
		System.out.println("ログアウト処理の完了");
		request.setAttribute("message", "ログアウトしました");
		url = Ui.LOGINUI;

		request.getRequestDispatcher(url).forward(request, response);
	}

}
