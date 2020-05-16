package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.TaskAppController;
import ui.Ui;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append("Served at:").append(request.getContextPath());

				//ログイン処理

				//コントローラーオブジェクト
				TaskAppController taskAppController = new TaskAppController();

				//確認用
				System.out.println("ログインサーブレット内ログイン処理中");
				if(taskAppController.Login(request)) {

					//ログイン成功時処理を行う
					taskAppController.InitProcess(request);

					//cookie作成して、セッションIDを格納し、リクエストにクッキーを格納
					String sessionId = request.getSession().getId();
					Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
					response.addCookie(sessionIdCookie);

					//次画面を格納する変数
					String url = null;

					//どの処理を行うかを判断し、ビジネスロジックに渡す。
					// 呼び出し元画面からデータ受け取り
					System.out.println("振り分け用文字列受取処理");
					String function = request.getParameter("functionStr");
					System.out.println(function);
					url = taskAppController.Logic(function, request);


					//次画面に遷移
					//request.setAttribute("message", "IDまたはパスワードが違います");
					System.out.println("LoginServlet処理終了");
					request.getRequestDispatcher(url).forward(request, response);
					//response.sendRedirect(url);

					//テスト用
					//response.sendRedirect("/Web_TaskApp/createtask.html");

				}else {
					//ログインエラー
					request.getRequestDispatcher(Ui.NOTLOGINUI).forward(request, response);
				}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
