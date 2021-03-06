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
	String getpasssql = "SELECT password FROM UserContext WHERE id = ?";//パスワード取得用
	String stragetasksql = "INSERT INTO task_list VALUES (?, ?, ?, ?, ?)";//task格納用
	String getlistsql = "SELECT * FROM task_list";//リスト取得用
	String deletetasksql = "DELETE FROM task_list WHERE ";//タスク削除用（１）
	String deletetasksqlwhere = "num = ?";//タスク削除用（２）
	String deketetasksqlor = " OR ";//タスク削除用（３）
	String deletetasksqlend = ";";//タスク削除用（４）
	String registersql = "INSERT INTO UserContext VALUES (?, ?)";

	//コンストラクタ
	public AbstructDAOManager() {
		//コネクションの確立
		try {
			//Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);

			//conn.setAutoCommit(false);
		} catch (SQLException /*| ClassNotFoundException*/ e) {//コネクションの確立に失敗
			System.out.println("DBとの接続に失敗しました");
			e.printStackTrace();
		}
	}

	//commitメソッド
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			//コミット失敗
			try {
				conn.rollback();
				e.printStackTrace();

			} catch (SQLException e1) {
				//rollback失敗はもう無理。
				e1.printStackTrace();
			}

		}
	}

}
