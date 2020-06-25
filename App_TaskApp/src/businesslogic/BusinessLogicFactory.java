package businesslogic;

public class BusinessLogicFactory {

	BusinessLogic logic = null;

	public BusinessLogic getLogic(String appname) {

		if(appname.equals("taskApp")) {
			logic = new DefaultBusinessLogicimpl();
		}

		return logic;
	}

}
