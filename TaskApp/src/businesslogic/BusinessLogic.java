package businesslogic;

import java.util.ArrayList;

import exception.DuplicationError;
import exception.falseLogionException;
import exception.notExistException;
import obj.Task;
import obj.UserContext;

public interface BusinessLogic {

	//ログインロジック
	void login(UserContext usercontext) throws falseLogionException, notExistException;

	//ログアウトロジック
	void logout();

	//Task保存ロジック
	void taskstorage(Task task);

	//一覧取得ロジック
	ArrayList<Task> getList();

	//Task削除ロジック
	void deleteTask(String[] taskNumList);

	//user登録ロジック
	void register(UserContext userContext) throws DuplicationError;

}
