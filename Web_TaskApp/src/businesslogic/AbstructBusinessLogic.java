package businesslogic;

import dao.DAOManager;
import dao.DaoFactory;

//DefaultKibanServiceのシステム的な仕様
public abstract class AbstructBusinessLogic {

	//daofactoryクラスを取得
	DaoFactory factory = new DaoFactory();

	//DAOManagerのインスタンスを取得する
	DAOManager dao = factory.getDao("taskApp", "oracle");

}
