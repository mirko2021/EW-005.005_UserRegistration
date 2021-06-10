package yatospace.user.database.data;

import java.io.Serializable;

import yatospace.user.util.UserCredentials;

/**
 * Баратање са корисничким креденцијалима.
 * Објекат базе података. 
 * @author MV
 * @version 1.0
 */
public class MySQLUserRegisterDTO implements Serializable{
	private static final long serialVersionUID = 5768636480596760821L;
	private UserCredentials credentials;

	public UserCredentials getCredentials() {
		return credentials;
	}

	public void setCredentials(UserCredentials credentials) {
		this.credentials = credentials;
	} 
}
