package obj;

import java.sql.Date;
import java.text.SimpleDateFormat;

//タスクオブジェクト
public class Task {

	//コンストラクタ
	public Task() {

	}

	public Task(String taskNum, Date deadLine, String taskName, String content, String client) {
		this.taskNum = taskNum;
		this.deadLine = deadLine;
		this.taskName = taskName;
		this.content = content;
		this.client = client;
	}
	//メンバ
	//タスク番号
	private String taskNum;
	//期限
	private Date deadLine;
	//タスク名
	private String taskName;
	//タスク内容
	private String content;
	//依頼主
	private String client;

	//getter,setter
	public Date getDeadline() {
		return deadLine;
	}
	public void setDeadline(Date deadLine) {
		this.deadLine = deadLine;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskname(String taskName) {
		this.taskName = taskName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(String taskNum) {
		this.taskNum = taskNum;
	}

	//文字列でDate型返す
	public String getStringDate () {
		String stringDate = new SimpleDateFormat("yyyy-MM-dd").format(deadLine);
		return  stringDate;
	}

	//一覧表示用メソッド
	public String list() {
		String list = deadLine + " " + taskName + " " + content + " " + client;
		return list;
	}


}
