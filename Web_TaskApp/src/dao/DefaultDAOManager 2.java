package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exception.notExistException;
import obj.UserContext;

public class DefaultDAOManager extends AbstructDAOManager implements DAOManager {

	//メンバ
	PreparedStatement pre_statement;

	//デフォルトコンストラクタ
	public DefaultDAOManager() {
		super();
	}

	@Override
	//入力されたIDのパスワードを検索する
	public String passwordCheckDAO(UserContext usercontext) throws notExistException {

		//テスト用：文字列passwordを返す
		//return "password";

		//メンバ
		int id = usercontext.getUserId();
		String password = null;

		//dao
		try {
			//パスワード取得用のSQLを用意
			pre_statement = conn.prepareStatement(getpasssql);
			pre_statement.setInt(1, id);

			//SQL実行
			ResultSet result_set = pre_statement.executeQuery();
			result_set.next();
			password = result_set.getString("password");

		} catch (SQLException e) {
			System.out.println("SQLエラーです");
			//e.printStackTrace();
		}

		return password;
	}

	@Override
	//タスクオブジェクトをtask_list(DB)に格納
	public void taskstorageDAO(String taskNum, Date deadLine, String taskName, String content, String client) {
		try {
			//task格納用のSQLを用意
			pre_statement = conn.prepareStatement(stragetasksql);
			pre_statement.setString(1, taskNum);
			pre_statement.setDate(2, deadLine);
			pre_statement.setString(3, taskName);
			pre_statement.setString(4, content);
			pre_statement.setString(5, client);

			//テスト用
			//System.out.print(stragetasksql);
			//System.out.print(pre_statement);

			//SQL実行(iには更新行数が格納される)
			@SuppressWarnings("unused")
			int i = pre_statement.executeUpdate();

			} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	//DBからタスク一覧を取得
	public ResultSet getListDAO() {

		//メンバ
		ResultSet result_set = null;

		try {
			//list取得用のSQLを用意
			pre_statement = conn.prepareStatement(getlistsql);

			//SQL実行
			result_set = pre_statement.executeQuery();

			//確認用
			result_set.last();
			int number_of_row = result_set.getRow();
			System.out.println(number_of_row);
			result_set.beforeFirst();   //最初に戻る

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result_set;
	}

	@Override
	//DBから指定されたタスクを削除する
	public void deleteTaskDAO(String[] taskNumList) {

		//taskNumListの長さから、SQLを作成する
		for(int i = 0; i < taskNumList.length; i++)
		{
			if(i > 0)
			{
				deletetasksql = deletetasksql + deketetasksqlor + deletetasksqlwhere;
			}else
			{
				deletetasksql = deletetasksql + deletetasksqlwhere;
			}
		}
			deletetasksql = deletetasksql + deletetasksqlend;

			//確認用
			System.out.println(deletetasksql);

			//task削除用のSQLを用意
			try {
				pre_statement = conn.prepareStatement(deletetasksql);
				for(int i = 0; i < taskNumList.length; i++)
				{
					pre_statement.setString(i+1, taskNumList[i]);
				}

				//確認用
				System.out.println(pre_statement);

				//SQL実行(iには更新行数が格納される)
				@SuppressWarnings("unused")
				int i = pre_statement.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

	}

}
