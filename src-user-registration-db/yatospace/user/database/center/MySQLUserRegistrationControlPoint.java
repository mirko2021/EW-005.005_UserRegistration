package yatospace.user.database.center;

import yatospace.user.database.YatospaceDBConnectionPool;
import yatospace.user.database.controller.MySQLRegistrationController;
import yatospace.user.database.io.MySQLCredentialsDAO;
import yatospace.user.database.io.MySQLUserRegisterDAO;

/**
 * Контролна тачка када су у питању рад са регистром и регистрација корисника. 
 * @author MV
 * @version 1.0
 */
public class MySQLUserRegistrationControlPoint {
	private final YatospaceDBConnectionPool connectionPool = YatospaceDBConnectionPool.getConnectionPool();
	private final MySQLUserRegisterDAO dataSource = new MySQLUserRegisterDAO(connectionPool); 
	private final MySQLCredentialsDAO parametersSource = new MySQLCredentialsDAO(connectionPool);
	private final MySQLRegistrationController controller = new MySQLRegistrationController(dataSource, parametersSource);
	
	
	public MySQLUserRegisterDAO getDataSource() {
		return dataSource;
	}
	public MySQLCredentialsDAO getParametersSource() {
		return parametersSource;
	}
	public MySQLRegistrationController getController() {
		return controller;
	}
}
