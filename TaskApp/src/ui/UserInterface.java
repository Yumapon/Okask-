package ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import obj.Task;
import obj.UserContext;

//コンソールに表示する画面
public class UserInterface {

	//ログイン時の画面
	public UserContext loginui(UserContext userContext) {

		//ユーザIDを入力させる
		System.out.print("ユーザID＞");
		@SuppressWarnings("resource")
		Scanner idScanner = new Scanner(System.in);
		userContext.setUserId(idScanner.nextInt());

		//パスワードを入力させる
		System.out.print("パスワード＞");
		@SuppressWarnings("resource")
		Scanner passScanner = new Scanner(System.in);
		userContext.setPassword(passScanner.nextLine());

		//ユーザ情報を返す
		return userContext;
	}

	//メニュー画面
	public String menuui(){
		//次画面を格納する変数
		String nextui = null;

		//選択画面
		System.out.println("--------------------------------------------");
		System.out.println("■　□メニュー画面□　■");
		System.out.println("1:一覧" + "\n" + "\n" + "2:新規作成" + "\n" + "\n" + "3:ログアウト");
		System.out.println("--------------------------------------------");
		System.out.print("メニュー選択＞");

		//選択された数値を取得
		@SuppressWarnings("resource")
		Scanner selectnumscanner = new Scanner(System.in);
		String selectnum = selectnumscanner.next();

		//次画面を決定(1か2が入力されるまで)
		if(selectnum.equals("1")) {
			//一覧を選択された場合
			nextui = "list";
		}else if(selectnum.equals("2")) {
			//新規作成された場合
			nextui = "create";
		}else if(selectnum.equals("3")) {
			//ログアウトを選択された場合
			nextui = "logout";
		}else {
			//1か２以外を入力された場合
			System.out.println("1か２か３を選択してください");
			//メニューに戻る
			menuui();
		}
		return nextui;

	}

	//一覧表示
	public void listui(ArrayList<Task> tasklist) {

		//画面
		System.out.println("--------------------------------------------");
		System.out.println("■　□一覧画面□　■");

		//ループで一覧表示
		for(int i = 0; i < tasklist.size(); i++) {
			System.out.println(tasklist.get(i).list());
		}
		System.out.println("--------------------------------------------");

		System.out.println("終了するには何か入力してください");
		@SuppressWarnings({ "resource" })
		Scanner exitscanner = new Scanner(System.in);
		@SuppressWarnings("unused")
		String exitflag = exitscanner.nextLine();
	}

	//新規作成画面
	public Task createui(Task task) {

		//画面
		System.out.println("--------------------------------------------");
		System.out.println("■　□新規作成画面□　■");

		System.out.print("期限＞");
		@SuppressWarnings("resource")
		Scanner deadlinescanner = new Scanner(System.in);
		Date deadline = Date.valueOf(deadlinescanner.nextLine());
		task.setDeadline(deadline);

		System.out.print("タスク名＞");
		@SuppressWarnings("resource")
		Scanner tasknamescanner = new Scanner(System.in);
		task.setTaskname(tasknamescanner.nextLine());

		System.out.print("タスク内容＞");
		@SuppressWarnings("resource")
		Scanner contentscanner = new Scanner(System.in);
		task.setContent(contentscanner.nextLine());

		System.out.print("依頼者＞");
		@SuppressWarnings("resource")
		Scanner clientscanner = new Scanner(System.in);
		task.setClient(clientscanner.nextLine());

		System.out.println("--------------------------------------------");

		System.out.println("終了するには何か入力してください");
		@SuppressWarnings({ "resource" })
		Scanner exitscanner = new Scanner(System.in);
		@SuppressWarnings("unused")
		String exitflag = exitscanner.nextLine();

		return task;
	}

	public void logoutui() {
		System.out.println("ログアウトしました");
	}


}
