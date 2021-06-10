package yatospace.user.registration.application;

import yatospace.user.config.center.UserRegistrationConfigCenter;
import yatospace.user.config.engine.UserRegistrationConfigEngine;
import yatospace.user.database.YatospaceDBConnectionPool;
import yatospace.user.database.controller.MySQLRegistrationController;
import yatospace.user.database.io.MySQLCredentialsDAO;
import yatospace.user.database.io.MySQLUserRegisterDAO;

/**
 * Уопштена контролна тачка када су у питању рад са регистрима, односно регистрацијом корисника. 
 * @author MV
 * @version 1.0
 */
public class GeneralUserRegistrationControlPoint {
	private final UserRegistrationConfigEngine configurationEngine = UserRegistrationConfigCenter.cofigurationsEngine; 
	private final YatospaceDBConnectionPool connectionPool = YatospaceDBConnectionPool.getConnectionPool(configurationEngine.getDatabaseConfigurations());
	private final MySQLUserRegisterDAO dataSource = new MySQLUserRegisterDAO(connectionPool); 
	private final MySQLCredentialsDAO parametersSource = new MySQLCredentialsDAO(connectionPool);
	private final MySQLRegistrationController controller = new MySQLRegistrationController(dataSource, parametersSource);
	
	
	public UserRegistrationConfigEngine getConfigurationEngine() {
		return configurationEngine;
	}
	public YatospaceDBConnectionPool getConnectionPool() {
		return connectionPool;
	}
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
