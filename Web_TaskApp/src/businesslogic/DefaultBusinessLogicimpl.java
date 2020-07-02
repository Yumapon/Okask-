package businesslogic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import exception.DuplicationError;
import exception.falseLogionException;
import exception.notExistException;
import obj.Task;
import obj.UserContext;

public class DefaultBusinessLogicimpl extends AbstructBusinessLogic implements BusinessLogic {

	@Override
	//ログイン処理の実装(ログイン失敗、該当のユーザIDが存在しない場合は例外処理発生)
	public void login(UserContext usercontext) throws falseLogionException, notExistException {

		//確認用
		System.out.println("ログイン機能スタート");

		//パスワードチェックDAOを呼び出す
		final String dbpass = dao.passwordCheckDAO(usercontext);
		//入力されたパスワード
		final String sysinpass = usercontext.getPassword();

		//パスワードが正しいかチェック
		if((sysinpass.equals(dbpass))) {
			System.out.println("パスワードが一致しました。");
		}else {
			//正しくなければ例外処理
			throw new falseLogionException();
		}

		System.out.println("ログインに成功しました。");
	}

	@Override
	//タスク番号採番の実装
	public String tasknum(String user_id) {

		//確認用
		System.out.println("タスク番号採番機能スタート");

		//一意な番号は、ユーザID+時間分秒ミリ秒の文字列とする
		Calendar cTime = Calendar.getInstance();
		//各要素の取得
		int hour = cTime.get(Calendar.HOUR_OF_DAY);
		int minute =  cTime.get(Calendar.MINUTE);
		int second =  cTime.get(Calendar.SECOND);
		int millisecond =  cTime.get(Calendar.MILLISECOND);

		String num = user_id + hour + minute + second + millisecond;

		//テスト用
		System.out.print("タスク番号は：" + num);

		return num;
	}

	@Override
	//タスクの格納機能
	public void taskstorage(Task task) {

		//確認用
		System.out.println("タスク格納機能スタート");

		//タスクの要素をとりだし、DAOに渡す。
		String taskNum = task.getTaskNum();
		Date deadline = task.getDeadline();
		String taskname = task.getTaskName();
		String content = task.getContent();
		String client = task.getClient();

		//受け取ったタスクをDAOに渡し格納する
		dao.taskstorageDAO(taskNum, deadline, taskname, content, client);
		System.out.println("タスク格納完了しました。");
	}

	@Override
	//task一覧取得
	public ArrayList<Task> getList() {
		//メンバ
		ArrayList<Task> tasklist = dao.getListDAO();

		return tasklist;
	}

	@Override
	//Task削除機能
	public void deleteTask(String[] taskNumList) {
		//DAO呼び出し
		System.out.println("タスクを削除します。");
		dao.deleteTaskDAO(taskNumList);
		System.out.println("タスクの削除が完了しました。");

	}

	@Override
	//user登録機能
	public void register(UserContext userContext) throws DuplicationError {

		//DAO呼び出し
		System.out.println("ユーザを登録します。");
		dao.registerDAO(userContext);
		System.out.println("ユーザの登録が完了しました。");

	}

	@Override
	public void logout() {
		//特にビジネスロジック側でやることはないので空実装

	}
}

