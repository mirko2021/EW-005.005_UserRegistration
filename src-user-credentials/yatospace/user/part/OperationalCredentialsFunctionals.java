package yatospace.user.part;

import java.util.List;

import yatospace.user.object.User;

/**
 * Функционалности које се односе на регистрацију и баратање са вриједностима за регистровање корисниак.. 
 * @author MV
 * @version 1.0
 */
public interface OperationalCredentialsFunctionals {
	public boolean add(String username, String password); 
	public boolean remove(String username); 
	public int count();
	public List<User> list(int pageNo, int pageSize, String startFilter);
	public boolean check(String username, String password);
	public boolean updateUsername(String username, String newUsername); 
	public boolean updatePassword(String username, String neoPassword); 
	public boolean testContains(String username);
	public boolean checkRecord(String username, String passwordRecord);
}
