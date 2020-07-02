package web_servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businesslogic.BusinessLogic;
import businesslogic.BusinessLogicFactory;
import exception.falseLogionException;
import exception.notExistException;
import obj.Task;
import obj.UserContext;
import ui.Ui;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
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
    public LoginServlet() {
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

		//ログイン処理

		//確認用
		System.out.println("LoginServletに到達しました。ログイン処理を開始します。");

		//入力情報を加工する
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String password = request.getParameter("password");

		//リクエストからusercontextを作成
		UserContext usercontext = new UserContext();
		usercontext.setUserId(user_id);
		usercontext.setPassword(password);

			//ログイン処理
			try {
				logic.login(usercontext);

				//ログイン成功時処理を行う
				System.out.println("ログイン初期処理を開始します。");
				InitProcess(request);

				//cookie作成して、セッションIDを格納し、リクエストにクッキーを格納
				String sessionId = request.getSession().getId();
				Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
				response.addCookie(sessionIdCookie);

				//確認用
				System.out.println("一覧画面に遷移します。");

				//タスク一覧を取得
				ArrayList<Task> tasklist = logic.getList();

				//確認用
				System.out.println("タスク一覧を取得しました");

				//タスク一覧をセッションに格納
				HttpSession session = request.getSession(false);
				session.setAttribute("tasklist", tasklist);

				//一覧画面を返す
				url = Ui.LISTUI;

			} catch (falseLogionException | notExistException e) {
				//ログインエラー
				System.out.println("ログインエラー");
				request.setAttribute("message", "ユーザIDかpasswordが間違っています。");
				url = Ui.LOGINUI;
			}finally {
				//次画面に遷移
				System.out.println("LoginServletの処理を終了します。");
				request.getRequestDispatcher(url).forward(request, response);
			}
	}

	//ログイン成功時初期処理
		private void InitProcess(final HttpServletRequest request) {

			//requestからユーザ名取得
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			//System.out.println(user_id);

			//sessionの再発行
			//request.getSession(true).invalidate();
			//確認用
			HttpSession session = request.getSession();
			String beforeSessionId = session.getId();
			session.invalidate();

			session = request.getSession(true);
			String afterSessionId = session.getId();

			//確認用
			if(beforeSessionId != afterSessionId) {
				System.out.println("session再発行の完了");
			}

			//sessionにユーザ名格納
			session.setAttribute("user_id", user_id);
			System.out.println("セッションにユーザID(" + user_id + ")を格納しました");
		}
	}

