package web_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import businesslogic.BusinessLogic;
import businesslogic.BusinessLogicFactory;
import exception.DuplicationError;
import obj.UserContext;
import ui.Ui;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//ビジネスロジックのファクトリークラスを取得
	BusinessLogicFactory logicFactory = new BusinessLogicFactory();
	//ロジックを取得
	BusinessLogic logic = logicFactory.getLogic("taskApp");
	//次画面用url
	String url = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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

		//登録処理

		int user_id = Integer.parseInt(request.getParameter("user_id"));
		String password = request.getParameter("password");
		//String name = request.getParameter("name");

		//serviceに受け渡す用のUserContextを作成する
		UserContext userContext = new UserContext(user_id, password);
		//サービスの登録機能を呼び出す
		try {

			System.out.println(user_id);
			System.out.println(password);

			logic.register(userContext);
			/*
			 * 業務的にユーザ登録時に他何かやることあればここに追記
			 * トランザクションの管理はBL層で行うので。
			 */
			//ログイン画面を返す
			request.setAttribute("message", "ユーザの登録が完了しました。 早速ログインしましょう！");
			url = Ui.LOGINUI;
		} catch (DuplicationError e) {
			//ユーザID重複の際の挙動
			request.setAttribute("message", "指定されたユーザIDはすでにアカウントが存在しています。");
			url = Ui.REGISTERUI;
		}finally {
			//次画面に遷移
			request.getRequestDispatcher(url).forward(request, response);
		}
	}

}
