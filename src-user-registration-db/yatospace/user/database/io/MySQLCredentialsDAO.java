package yatospace.user.database.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import yatospace.user.database.YatospaceDBConnectionPool;
import yatospace.user.database.data.MySQLCredentialsDTO;
import yatospace.user.util.SaltGeneratorEngine;

/**
 * Односи се на карактеристике односно парамтеризацију 
 * контроле за креденцијале односно за регистре. 
 * Листа заузаетих салт кодова. 
 * @author MV
 * @version 1.0
 */
public class MySQLCredentialsDAO {
	private YatospaceDBConnectionPool connectionPool;
	public static final String SQL_GET_ALL_PASSWORDS_RECORDS = "SELECT passwordcode FORM "+YatospaceDBConnectionPool.DATABASE+".yi_users";
	
	public YatospaceDBConnectionPool getConnectionPool() {
		return connectionPool;
	} 
	
	public MySQLCredentialsDAO(YatospaceDBConnectionPool pool) {
		if(pool==null) throw new NullPointerException(); 
		this.connectionPool = pool; 
	}
	
	public MySQLCredentialsDTO getSaltGenerator() throws SQLException {
		MySQLCredentialsDTO dto = new MySQLCredentialsDTO(); 
		SaltGeneratorEngine saltGenerator = new SaltGeneratorEngine(); 
		dto.setSlatGenerator(saltGenerator);
		
		Connection connection = connectionPool.checkOut();
		try (PreparedStatement statement = connection.prepareStatement(SQL_GET_ALL_PASSWORDS_RECORDS)){
			try(ResultSet result = statement.executeQuery()){
				ArrayList<String> usedSaltCodes = new ArrayList<>(); 
				while(result.next()) {
					String passwordRecord = result.getString(1); 
					if(passwordRecord==null) continue; 
					String[] parts = passwordRecord.split("\\$");
					if(parts.length!=2) continue;
					usedSaltCodes.add(parts[0]);
				}
				saltGenerator.setGenerateCodes(usedSaltCodes);
			}
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			connectionPool.checkIn(connection);
		}
		return dto; 
	}
}
