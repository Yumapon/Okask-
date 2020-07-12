package dao;

public class DaoFactory {

	DAOManager dao = null;

	public DAOManager getDao(String appname, String dbname) {
		if(dbname.equals("oracle")) {
			if(dbname.contentEquals("taskApp")) {
				//タスクアプリ用のDAOを生成
				dao = new DefaultDAOManager();
			}

		}
		return dao;
	}

}
