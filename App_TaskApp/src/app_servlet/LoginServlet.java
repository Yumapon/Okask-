package app_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app_controller.App_TaskAppController;
import obj.TaskList;

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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//ログイン処理
		//コントローラーオブジェクト
		App_TaskAppController taskAppController = new App_TaskAppController();

		//確認用
		System.out.println("ログインサーブレット内ログイン処理中");
		System.out.println(request.getParameter("functionStr"));
		if(request.getParameter("functionStr") != null) {

			//ログイン処理を行う
			System.out.println("サーブレット:ログイン処理開始");
			taskAppController.Login(request);

			//ログイン成功時処理を行う
			taskAppController.InitProcess(request);

			//cookie作成して、セッションIDを格納し、リクエストにクッキーを格納
			String sessionId = request.getSession().getId();
			Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
			response.addCookie(sessionIdCookie);

			//
			TaskList tasklist = new TaskList();

			//どの処理を行うかを判断し、ビジネスロジックに渡す。
			// 呼び出し元画面からデータ受け取り
			System.out.println("振り分け用文字列受取処理");
			String function = request.getParameter("functionStr");
			System.out.println(function);
			tasklist = taskAppController.Logic(function, request);

			ObjectMapper mapper = new ObjectMapper();
	        try {
	            //JavaオブジェクトからJSONに変換
	            String taskListJson = mapper.writeValueAsString(tasklist);
	            //JSONの出力
	            response.getWriter().write(taskListJson);
	            //出力されるJSONの確認
	            System.out.println(taskListJson);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }

		}else {
			System.out.println("functionStrはnullです");
			response.getWriter().append("サーブレット待機中").append(request.getContextPath());
		}

	}

}
