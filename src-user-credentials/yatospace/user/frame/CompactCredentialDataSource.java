package yatospace.user.frame;

import java.util.List;

import yatospace.user.controller.DirectCompactCredentialController;
import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;
import yatospace.user.util.Page;
import yatospace.user.util.Passport;

/**
 * Компактне функционалности за извор података и баратање подацима о регистрацији корисника. 
 * @author MV
 * @version 1.0
 */
public interface CompactCredentialDataSource extends GeneralCredentialDataSource, ShortedCredentialFunctional{
	public Page getPage();
	public DirectCompactCredentialController setPage(Page page); 
	public Passport getPassport();
	public DirectCompactCredentialController setPassport(Passport passport);
	public DirectCompactCredentialController resetPage();
	public DirectCompactCredentialController resetPassword();
	public Page getOrGeneratePage(); 
	public Passport getOrGeneratePassport(); 
	public boolean hasPage();
	public boolean hasPassport();
	public boolean add(String username, String password);
	public boolean add() throws PassportException; 
	public boolean remove() throws PassportException; 
	public boolean remove(String username); 
	public int count(); 
	public List<User> list(int pageNo, int pageSize, String startFilter);
	public List<User> listRefresh() throws PaggingException;
	public List<User> listRefresh(String startFilter) throws PaggingException; 
	public List<User> listNext() throws PaggingException; 
	public List<User> listNext(String startFilter) throws PaggingException; 
	public List<User> listPrevious() throws PaggingException; 
	public List<User> listPrevious(String startFilter) throws PaggingException; 
	public boolean check(String username, String password); 
	public boolean check() throws PassportException; 
	public boolean updateUsername(String username, String newUsername); 
	public boolean updatePassword(String username, String neoPassword);
	public boolean testContains(String username);
	public boolean updateUsername(String newUsername) throws PassportException; 
	public boolean updatePassword(String neoPassword) throws PassportException;
	public boolean testContains() throws PassportException;
}
