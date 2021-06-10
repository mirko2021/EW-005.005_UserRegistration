package yatospace.user.part;

import java.util.List;

import yatospace.user.object.User;

/**
 * Функционалности које се односе на регистрацију и баратање са вриједностима за регистровање корисниак.. 
 * @author MV
 * @version 1.0
 */
public interface OperationaRegisterFunctionals {
	public boolean register(String username, String password); 
	public boolean delete(String username, String oldPassword); 
	public int count();
	public List<User> list(int pageNo, int pageSize, String startFilter);
	public boolean check(String username, String password);
	public boolean checkRecord(String username, String passwordRecord);
	public boolean updateUsername(String username, String newUsername, String oldPassword); 
	public boolean updatePassword(String username, String neoPassword, String oldPassword); 
	public boolean testContains(String username);
}
