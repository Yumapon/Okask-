package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstructDAOManager {

	//メンバ
	Connection conn = null;
	String url = "jdbc:mysql://localhost:3306/TaskDB";
	String user = "root";
	String password = "MBnxncyj12";

	//sql文
	String getpasssql = "SELECT password FROM UserContext WHERE id = ?;";//パスワード取得用
	String stragetasksql = "INSERT INTO task_list VALUES (?, ?, ?, ?, ?);";//task格納用
	String getlistsql = "SELECT * FROM task_list;";//リスト取得用
	String deletetasksql = "DELETE FROM task_list WHERE ";//タスク削除用（１）
	String deletetasksqlwhere = "num = ?";//タスク削除用（２）
	String deketetasksqlor = " OR ";//タスク削除用（３）
	String deletetasksqlend = ";";//タスク削除用（４）

	//コンストラクタ
	public AbstructDAOManager() {
		//コネクションの確立
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {//コネクションの確立に失敗
			System.out.println("DBとの接続に失敗しました");
			e.printStackTrace();
		}
	}

}
