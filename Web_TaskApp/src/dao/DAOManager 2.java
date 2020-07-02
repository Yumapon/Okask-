package dao;
import java.sql.Date;
import java.sql.ResultSet;

import exception.notExistException;
import obj.UserContext;

//DAOが提供するサービス
public interface DAOManager {

	//入力されたユーザIDのパスワードを取得する
	String passwordCheckDAO(UserContext usercontext) throws notExistException;

	//タスクをtask_list(DB)に格納
	void taskstorageDAO(String taskNum, Date deadLine, String taskName, String content, String client);

	//一覧を取得
	ResultSet getListDAO();

	//タスクを削除する
	void deleteTaskDAO(String[] taskNumList);

}
