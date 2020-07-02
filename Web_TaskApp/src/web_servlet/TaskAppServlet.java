package web_servlet;

import java.io.IOException;
import java.sql.Date;
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
import obj.Task;
import ui.Ui;

/**
 * Servlet implementation class TaskAppServlet
 */
@WebServlet("/TaskAppServlet")
public class TaskAppServlet extends HttpServlet {
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
    public TaskAppServlet() {
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




		//確認用
				System.out.println("TaskAppServletに到達");

				// TODO Auto-generated method stub
				request.setCharacterEncoding("UTF-8");
				response.setContentType("text/html; charset=UTF-8");
				response.getWriter().append("Served at:").append(request.getContextPath());

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
						System.out.println("Cookieに格納されているのは" + sessionId);
						System.out.println("Sessionに格納されているのは" + session.getId());
						if(sessionId != session.getId()) {
							System.out.println("セッションIDが一致しました。同一クライアントからのリクエストです。");
						}
					}
				}
				if(session != null && sessionIdCookie != null && sessionId.equals(session.getId()))
				{
					//login確認できたら次へ
					//どの処理を行うかを判断し、ビジネスロジックに渡す。
					// 呼び出し元画面からデータ受け取り
					String function = request.getParameter("functionStr");
					//業務ロジック呼び出し
					//新規作成画面からTaskを作成してセッションに格納する
					if(function.equals("createtask"))
					{
						System.out.println("タスク新規作成機能を実行しています。");

						//リクエストから新規作成するタスクの内容を取得
						//String user_id = (String)session.getAttribute("user_id");
						//System.out.println("きた" + user_id);
						String taskNum = logic.tasknum("1111111111111111111111111111111111112");
						Date deadLine = Date.valueOf(request.getParameter("deadline"));
						String taskName = request.getParameter("taskname");
						String content = request.getParameter("content");
						String client = request.getParameter("client");

						//Taskオブジェクトを発行
						Task task = new Task(taskNum, deadLine, taskName, content, client);
						//Task task = new Task(deadLine, taskName, content, client);

						//Taskをセッションに格納
						session.setAttribute("newTask", task);

						//確認画面を返す
						url = Ui.CREATECHECKUI;
						//System.out.println("urlセット完了");

					}

					//確認画面にて確認したTaskを保存する
					else if(function.equals("confirmtask"))
					{
						System.out.println("タスクを格納しています。");
						Task task = (Task)session.getAttribute("newTask");
						//task格納機能
						logic.taskstorage(task);

						//一覧画面へ遷移
						//タスク一覧を取得
						ArrayList<Task> tasklist = logic.getList();
						//タスク一覧をセッションに格納
						session.setAttribute("tasklist", tasklist);
						//一覧画面を返す
						url = Ui.LISTUI;
						//System.out.println("urlセット完了");

					}
					//アカウント登録画面に遷移
					else if(function.equals("goregisterui"))
					{
						//登録画面を返す
						url = Ui.REGISTERUI;
						//System.out.println("urlセット完了");

					}

					//一覧画面に遷移
					else if( function.equals("golistui"))
					{
						//確認用
						System.out.println("一覧画面に遷移します。");

						//タスク一覧を取得
						ArrayList<Task> tasklist = logic.getList();

						//確認用
						System.out.println("タスク一覧を取得しました");

						//タスク一覧をセッションに格納
						session.setAttribute("tasklist", tasklist);

						//一覧画面を返す
						url = Ui.LISTUI;
						//System.out.println("urlセット完了");

					}
					//Task削除機能
					else if(function.equals("deletetask"))
					{
						//確認用
						System.out.println("Task削除機能を開始します。");

						//requestから削除するIDを取得する
						String[] taskNumList = request.getParameterValues("task");

						if(taskNumList != null) {
							//確認用
							System.out.println("削除するタスクを特定しました");

							//タスクを削除
							logic.deleteTask(taskNumList);

							//タスク一覧を取得
							ArrayList<Task> tasklist = logic.getList();
							//タスク一覧をセッションに格納
							session.setAttribute("tasklist", tasklist);

							//System.out.println("urlセット完了");
						}else {
							request.setAttribute("message", "削除する際はなにか選択してください。");
						}
						//一覧画面を返す
						url = Ui.LISTUI;
					}

					//確認用
					//System.out.println("setされているURLは" + url + "です。");


				}else {
					//確認用
					//System.out.println("ログイン失敗");
					url = Ui.NOTLOGINUI;
				}

				request.getRequestDispatcher(url).forward(request, response);
	}
}
