package console_controller;

import java.util.ArrayList;

import businesslogic.BusinessLogic;
import businesslogic.BusinessLogicFactory;
import exception.falseLogionException;
import exception.notExistException;
import obj.Task;
import obj.UserContext;
import ui.UserInterface;

public class ConsoleTaskAppController {

	public static void main(String[] args) {

		//画面
		UserInterface ui = new UserInterface();
		//ユーザ情報格納オブジェクト
		UserContext usercontext = new UserContext();
		//ビジネスロジックのファクトリークラスを取得
		BusinessLogicFactory logicFactory = new BusinessLogicFactory();
		//ロジックを取得
		BusinessLogic logic = logicFactory.getLogic("taskApp");

		//画面表示・ユーザ情報取得
		ui.loginui(usercontext);

		//確認用
		//System.out.println(userContext.getUserId());
		//System.out.println(userContext.getPassword());

		//ユーザ情報を元にログインを行う
		try {
			//ログイン
			logic.login(usercontext);
			System.out.println("ログイン成功");

			while(true) {
				//メニュー画面
				String nextui = ui.menuui();
				//System.out.print(nextui);

				if (nextui == "list") {

					//一覧をサービスから取得
					ArrayList<Task> tasklist = logic.getList();

					//一覧を画面に渡し、表示
					ui.listui(tasklist);

				}else if(nextui == "create") {
					//新規作成画面
					Task task = new Task();
					ui.createui(task);

					//完成したtaskをDBに格納
					logic.taskstorage(task);

				}else if(nextui == "logout") {
					//ログアウト処理
					ui.logoutui();
					System.exit(0);
				}
			}

		 //ログイン失敗時
		} catch (falseLogionException e) {
			//e.printStackTrace();
			System.out.println("ログイン失敗");
		//ユーザIDが登録されていない
		} catch (notExistException e) {
			//e.printStackTrace();
			System.out.println("指定のユーザIDは登録されていません");
		//ログアウト失敗時
		}


	}

}
