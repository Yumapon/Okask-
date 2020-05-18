package controller;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import exception.falseLogionException;
import exception.falseLogoutException;
import exception.notExistException;
import obj.Task;
import obj.UserContext;
import service.DefaultServiceimpl;
import service.Service;
import ui.Ui;

public class TaskAppController {
	//次画面格納変数
	String url = null;
	//ユーザ情報格納オブジェクト
	UserContext usercontext = new UserContext();
	//基盤サービスを取得
	Service service = new DefaultServiceimpl();

	//ログイン
	public boolean Login(final HttpServletRequest request) {
			//リクエストからusercontextを作成
			int user_id = Integer.parseInt(request.getParameter("user_id"));
			String password = request.getParameter("password");
			usercontext.setUserId(user_id);
			usercontext.setPassword(password);

			//ログイン機能
			try {
				service.login(usercontext);
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

	//ログアウト
	public boolean Logout(final HttpServletRequest request) {

		//ログアウト機能
		try {
			//ログアウト処理
			service.logout(request.getSession());
			request.setAttribute("message", "ログアウトしました");

		} catch (falseLogoutException e) {
			//ログアウト失敗（ありえる？）
			System.out.println("ログアウト失敗");
			return false;
		}

		return true;
	}

	public String Logic(String function, final HttpServletRequest request) {

		//session取得
		HttpSession session = request.getSession();
		//確認用
		System.out.println("TaskAppContrller到達");

		//機能振り分け
			System.out.println("機能振り分け");
			System.out.println(function);


			//新規作成画面からTaskを作成してセッションに格納する
			if(function.equals("createtask"))
			{
				//新規作成するタスクの番号を発行
				String taskNum = service.tasknum();
				//リクエストから新規作成するタスクの内容を取得
	            Date deadLine = Date.valueOf(request.getParameter("deadline"));
				String taskName = request.getParameter("taskname");
				String content = request.getParameter("content");
				String client = request.getParameter("client");

				//Taskオブジェクトを発行
				Task task = new Task(taskNum, deadLine, taskName, content, client);

				//確認用
				System.out.println(task.list());

				//Taskをセッションに格納
				session.setAttribute("newTask", task);

				//確認画面を返す
				url = Ui.CREATECHECKUI;
				System.out.println("urlセット完了");

			}

			//確認画面にて確認したTaskを保存する
			else if(function.equals("confirmtask"))
			{
				//sessionからTaskを取得
				Task task = (Task)session.getAttribute("newTask");

				//確認用
				System.out.println(task.list());

				//task格納機能
				service.taskstorage(task);

				//一覧画面へ遷移
				//タスク一覧を取得
				ArrayList<Task> tasklist = service.getList();
				//タスク一覧をセッションに格納
				session.setAttribute("tasklist", tasklist);
				//一覧画面を返す
				url = Ui.LISTUI;
				System.out.println("urlセット完了");

			}
			//アカウント作成
			else if(function.equals("register"))
			{
				//登録機能

				//ログイン画面を返す
				url = Ui.LOGINUI;
				System.out.println("urlセット完了");

			}

			//新規作成画面に遷移
			else if(function.equals("gocreatetaskui"))
			{
				//新規作成画面を返す
				url = Ui.CREATEUI;
				System.out.println("urlセット完了");

			}

			//アカウント登録画面に遷移
			else if(function.equals("goregisterui"))
			{
				//登録画面を返す
				url = Ui.REGISTERUI;
				System.out.println("urlセット完了");

			}

			//一覧画面に遷移
			else if( function.equals("golistui"))
			{
				//確認用
				System.out.println("一覧画面遷移機能");

				//タスク一覧を取得
				ArrayList<Task> tasklist = service.getList();

				//確認用
				System.out.println("一覧画面遷移機能 タスク一覧取得完了");

				//タスク一覧をセッションに格納
				session.setAttribute("tasklist", tasklist);
				//確認用
				System.out.println("一覧画面遷移機能 タスク一覧セッションに格納完了");

				//一覧画面を返す
				url = Ui.LISTUI;
				System.out.println("urlセット完了");

			}
			//Task削除機能
			else if(function.equals("deletetask"))
			{
				//確認用
				System.out.println("Task削除機能");

				//requestから削除するIDを取得する
				String[] taskNumList = request.getParameterValues("task");

				//確認用
				System.out.println("削除ID取得完了");

				//確認用
				for(int i = 0; i < taskNumList.length; i++) {
					System.out.println(taskNumList[i]);
				}

				//タスクを削除
				service.deleteTask(taskNumList);

				//タスク一覧を取得
				ArrayList<Task> tasklist = service.getList();
				//タスク一覧をセッションに格納
				session.setAttribute("tasklist", tasklist);
				//一覧画面を返す
				url = Ui.LISTUI;
				System.out.println("urlセット完了");
			}

		System.out.println(url);
		return url;
	}

}
