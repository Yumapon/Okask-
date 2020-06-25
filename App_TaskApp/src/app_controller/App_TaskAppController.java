package app_controller;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import businesslogic.BusinessLogic;
import businesslogic.BusinessLogicFactory;
import exception.falseLogionException;
import exception.notExistException;
import obj.Task;
import obj.TaskList;
import obj.UserContext;

public class App_TaskAppController {
	//次画面格納変数
	String url = null;
	//ユーザ情報格納オブジェクト
	UserContext usercontext = new UserContext();
	//ビジネスロジックのファクトリークラスを取得
	BusinessLogicFactory logicFactory = new BusinessLogicFactory();
	//ロジックを取得
	BusinessLogic logic = logicFactory.getLogic("taskApp");
	//タスク一覧格納オブジェクト
	TaskList tasklist = new TaskList();

	//ログイン
	public boolean Login(final HttpServletRequest request) {
			//確認用
			System.out.println("TaskAppControllerのログイン機能開始");
			//リクエストからusercontextを作成
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String password = request.getParameter("password");
			usercontext.setUserId(user_id);
			usercontext.setPassword(password);

			//ログイン機能
			try {
				logic.login(usercontext);
			} catch (falseLogionException | notExistException e) {
				// ログイン失敗
				request.setAttribute("message", "もう一度ログインし直してください");
				return false;
			}
			//ユーザーコンテキストをセッションに格納(本当はパスワードをセッションに保持させるのはだめ)
			//session.setAttribute("usercontext", usercontext);

			return true;

		}

	//ログイン成功時初期処理
	public void InitProcess(final HttpServletRequest request) {

		//requestからユーザ名取得
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		System.out.println(user_id);

		//sessionの再発行
		//request.getSession(true).invalidate();
		//確認用
		HttpSession session = request.getSession();
		String beforeSessionId = session.getId();
		session.invalidate();

		session = request.getSession(true);
		String afterSessionId = session.getId();

		//確認用
		System.out.println(beforeSessionId);
		System.out.println(afterSessionId);
		System.out.println("session再発行の完了");

		//sessionにユーザ名格納
		session.setAttribute("user_id", user_id);
		System.out.println("セッションにユーザID(" + user_id + ")格納完了");
	}

	public TaskList Logic(String function, final HttpServletRequest request) {

		//session取得
		HttpSession session = request.getSession();
		//確認用
		System.out.println("TaskAppContrller到達");

		//機能振り分け
		System.out.println("機能振り分け");
		System.out.println(function);

		//一覧画面に遷移
		if( function.equals("golistui"))
		{
				System.out.println("一覧画面遷移機能");

				//タスク一覧を取得
				tasklist.setTasklist(logic.getList());

				//確認用
				System.out.println("一覧画面遷移機能 タスク一覧取得完了");

				//タスク一覧をセッションに格納
				session.setAttribute("tasklist", tasklist);
				//確認用
				System.out.println("一覧画面遷移機能 タスク一覧セッションに格納完了");
		}

		//Task削除機能
		else if(function.equals("deletetask"))
		{
			System.out.println("タスク削除機能");

			//requestから削除するIDを取得する
			String[] taskNumList = request.getParameterValues("task");

			//確認用
			System.out.println("削除ID取得完了");

			//確認用
			for(int i = 0; i < taskNumList.length; i++) {
				System.out.println(taskNumList[i]);
			}

			//タスクを削除
			logic.deleteTask(taskNumList);

			//タスク一覧を取得
			tasklist.setTasklist(logic.getList());

			//確認用
			System.out.println("一覧画面遷移機能 タスク一覧取得完了");

		}
		else if(function.equals("createtask"))
		{
			System.out.println("createTask機能開始");
			//新規作成するタスクの番号を発行
			//リクエストから新規作成するタスクの内容を取得
			System.out.println("タスク取得開始");
			String taskName = request.getParameter("taskname");
			System.out.println(taskName);
			String content = request.getParameter("content");
			System.out.println(content);
			Date deadLine = Date.valueOf(request.getParameter("deadline"));
			System.out.println(deadLine);
			String client = request.getParameter("client");
			System.out.println(client);

			//Taskオブジェクトを発行
			Task task = new Task();
			task.setDeadline(deadLine);
			task.setTaskname(taskName);
			task.setContent(content);
			task.setClient(client);

			//確認用
			System.out.println(task.list());

			//task格納機能
			logic.taskstorage(task);

			//タスク一覧を取得
			tasklist.setTasklist(logic.getList());

		}

		return tasklist;

	}

}
