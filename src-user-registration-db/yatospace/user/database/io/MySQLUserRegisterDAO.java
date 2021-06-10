package yatospace.user.database.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import yatospace.user.database.YatospaceDBConnectionPool;
import yatospace.user.database.data.MySQLUserRegisterDTO;
import yatospace.user.object.User;
import yatospace.user.util.Page;
import yatospace.user.util.UserCredentials;

/**
 * Адаптер који се односи на примопредају података о креденцијалима. 
 * @author MV
 * @version 1.0
 */
public class MySQLUserRegisterDAO {
	private YatospaceDBConnectionPool connectionPool;
	
	public static final String SQL_GET_USER_CREDENTIALS = "SELECT username, passwordcode FROM "+YatospaceDBConnectionPool.DATABASE+".yi_users WHERE username=?";
	public static final String SQL_LIST_USER_LIST_CREDENTIALS = "SELECT username, passwordcode FROM "+YatospaceDBConnectionPool.DATABASE+".yi_users WHERE username LIKE ? ORDER BY username ASC LIMIT ? OFFSET ?"; 
	public static final String SQL_INSERT_USER_CREDENTIALS = "INSERT INTO "+YatospaceDBConnectionPool.DATABASE+".yi_users(username, passwordcode) VALUES (?, ?)"; 
	public static final String SQL_RENAME_USERNAME = "UPDATE "+YatospaceDBConnectionPool.DATABASE+".yi_users SET username=? WHERE username=?";
	public static final String SQL_UPDATE_PASSWORD_RECORD = "UPDATE "+YatospaceDBConnectionPool.DATABASE+".yi_users SET passwordcode=?  WHERE username=?"; 
	public static final String SQL_DELETE_USER = "DELETE FROM "+YatospaceDBConnectionPool.DATABASE+".yi_users WHERE username=?"; 
	public static final String SQL_COUNT_USER_LIST_CREDENTIALS = "SELECT COUNT(username) FROM "+YatospaceDBConnectionPool.DATABASE+".yi_users";
	
	public MySQLUserRegisterDAO(YatospaceDBConnectionPool connectionPool) {
		this.connectionPool = connectionPool; 
	}
	
	public MySQLUserRegisterDTO get(String username) throws SQLException {
		Connection connection = connectionPool.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_GET_USER_CREDENTIALS)){
			statement.setString(1, username);
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					MySQLUserRegisterDTO dto = new MySQLUserRegisterDTO();
					UserCredentials credentials = new UserCredentials();
					credentials.setPasswordRecord(result.getString(2)).convertRecordToHashSaltCombination(); 
					credentials.setUser(new User(result.getString(1))); 
					dto.setCredentials(credentials);
					return dto; 
				}
			}
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return null;
	}
	
	
	public List<MySQLUserRegisterDTO> list(Page page) throws SQLException{
		if(page==null) throw new NullPointerException(); 
		if(page.getPageNo()==0) return new ArrayList<>(); 
		int pageSize = page.getPageSize(); 
		int pageNo = page.getPageNo()-1;
		String startFilter = page.getStartFilter().replace("%", "").replaceAll("_", "").replaceAll("\\\\",""); 
		Connection connection = connectionPool.checkOut(); 
		ArrayList<MySQLUserRegisterDTO> list = new ArrayList<>(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_LIST_USER_LIST_CREDENTIALS)){
			statement.setString(1, startFilter+"%");
			statement.setInt(2, pageSize);
			statement.setInt(3, pageSize*pageNo);
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					MySQLUserRegisterDTO dto = new MySQLUserRegisterDTO();
					UserCredentials credentials = new UserCredentials();
					credentials.setPasswordRecord(result.getString(2)).convertRecordToHashSaltCombination(); 
					credentials.setUser(new User(result.getString(1))); 
					dto.setCredentials(credentials);
					list.add(dto); 
				}
			}
		}catch(RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}catch(Exception ex) {
			ex.printStackTrace(System.out);
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return list; 
	}
	
	public boolean insert(MySQLUserRegisterDTO dto) throws SQLException {
		if(dto==null) return false; 
		if(dto.getCredentials()==null) return false; 
		if(dto.getCredentials().getUser()==null) return false;
		if(dto.getCredentials().getUser().getUsername().trim().length()==0) return false;
		if(dto.getCredentials().getPasswordRecord()==null) return false;
		if(dto.getCredentials().getPasswordRecord().length()==0) return false;
		if(dto.getCredentials().getPasswordRecord().split("\\$").length!=2) return false;
		Connection connection = connectionPool.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER_CREDENTIALS)){
			statement.setString(1, dto.getCredentials().getUser().getUsername());
			statement.setString(2, dto.getCredentials().getPasswordRecord());
			statement.execute(); 
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return true; 
	}
	
	public boolean rename(String username, String newUsername) throws SQLException { 
		if(username==null) return false; 
		if(newUsername==null) return false; 
		if(newUsername.trim().length()==0) return false;
		Connection connection = connectionPool.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_RENAME_USERNAME)){
			statement.setString(1, newUsername);
			statement.setString(2, username);
			statement.execute(); 
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return true;
	}
	
	
	public boolean updatePassword(String username, String newPasswordRecord) throws SQLException {
		if(username==null) return false; 
		if(newPasswordRecord==null) return false; 
		if(newPasswordRecord.trim().length()==0) return false;
		if(newPasswordRecord.split("\\$").length!=2) return false;
		Connection connection = connectionPool.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD_RECORD)){
			statement.setString(1, newPasswordRecord);
			statement.setString(2, username);
			statement.execute(); 
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return true;
	}
	
	public boolean testPlainPassword(String username, String password) throws SQLException {
		if(username==null) return false;
		if(password==null) return false; 
		MySQLUserRegisterDTO dto = get(username);
		if(dto==null) return false; 
		return dto.getCredentials().setPasswordPlain(password).check(); 
	}
	
	public boolean testRecordPassword(String username, String passwordRecord) throws SQLException {
		if(username==null) return false;
		if(passwordRecord==null) return false; 
		MySQLUserRegisterDTO dto = get(username);
		if(dto==null) return false; 
		return dto.getCredentials().getPasswordRecord().contentEquals(passwordRecord); 
	}
	
	public boolean remove(String username) throws SQLException {
		if(username==null) return false; 
		Connection connection = connectionPool.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
			statement.setString(1, username);
			statement.execute(); 
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return true;
	}
	
	public int count() throws SQLException{
		Connection connection = connectionPool.checkOut(); 
		try(PreparedStatement statement = connection.prepareStatement(SQL_COUNT_USER_LIST_CREDENTIALS)){
			try(ResultSet result = statement.executeQuery()){
				while(result.next()) {
					return result.getInt(1); 
				}
			}
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return 0; 
	}
}
