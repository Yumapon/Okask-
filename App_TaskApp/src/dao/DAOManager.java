package dao;
import java.sql.Date;
import java.util.ArrayList;

import exception.DuplicationError;
import exception.notExistException;
import obj.Task;
import obj.UserContext;

//DAOが提供するサービス
public interface DAOManager {

	//入力されたユーザIDのパスワードを取得する
	String passwordCheckDAO(UserContext usercontext) throws notExistException;

	//タスクをtask_list(DB)に格納
	void taskstorageDAO(Date deadLine, String taskName, String content, String client);

	//一覧を取得
	ArrayList<Task> getListDAO();

	//タスクを削除する
	void deleteTaskDAO(String[] taskNumList);

	//ユーザIDを登録する
	void registerDAO(UserContext userContext) throws DuplicationError;

}
