package obj;

//ユーザ情報格納オブジェクト
public class UserContext {

	//メンバ
	private int userId;
	private String name;
	private String password;

	//setter getter
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
