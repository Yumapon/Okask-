package app_servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app_controller.App_TaskAppController;
import obj.TaskList;

/**
 * Servlet implementation class TaskAppServlet
 */
@WebServlet("/TaskAppServlet")
public class TaskAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskAppServlet() {
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
		//確認用
				System.out.println("TaskAppServletに到達");

				// TODO Auto-generated method stub
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				
				//Jsonの先にこれがついてた
				//response.getWriter().append("Served at:").append(request.getContextPath());

				//ログイン確認
				//session,Cookieを取得、新規作成は行わない
				HttpSession session = request.getSession(false);
				Cookie[] cookies = request.getCookies();
				Cookie sessionIdCookie = null;
				String sessionId = null;
				for(int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("sessionId")) {
						sessionIdCookie = cookies[i];
						sessionId = sessionIdCookie.getValue();

						//確認用
						System.out.println("Cookiに格納されているのは" + sessionId);
						System.out.println("Sessionに格納されているのは" + session.getId());
					}
				}
				if(session != null && sessionIdCookie != null && sessionId.equals(session.getId()))
				{
					//login確認できたら次へ
					//コントローラーオブジェクト
					App_TaskAppController taskAppController = new App_TaskAppController();
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
					//response.sendRedirect(url);

					//テスト用
					//response.sendRedirect("/Web_TaskApp/createtask.html");

				}else {
					//ログインエラー ここもtaskAppControllerに入れた方が良いかも
					//確認用
					System.out.println("ログイン失敗");
				}

	}

}
