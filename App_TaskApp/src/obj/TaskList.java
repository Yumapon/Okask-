package obj;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskList {

	//メンバ
	//タスク一覧
	@JsonProperty("tasklist")
	private ArrayList<Task> tasklist;

	//sette getter
	public ArrayList<Task> getTasklist() {
		return tasklist;
	}

	public void setTasklist(ArrayList<Task> tasklist) {
		this.tasklist = tasklist;
	}



}
