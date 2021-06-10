package yatospace.user.frame;

import java.util.List;

import yatospace.user.controller.DirectCompactCredentialController;
import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;
import yatospace.user.part.ParametringRegisterFunctionals;
import yatospace.user.part.ShortedOperationalFunctional;
import yatospace.user.util.Page;
import yatospace.user.util.Passport;

/**
 * Скраћене функцоналности које се односе на рад са корисницима, у служби регистровања и брисања. 
 * @author MV
 * @version 1.0
 */
public interface ShortedCredentialFunctional extends ParametringRegisterFunctionals, ShortedOperationalFunctional{
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
	public boolean add() throws PassportException; 
	public boolean remove() throws PassportException; 
	public int count(); 
	public List<User> listRefresh() throws PaggingException;
	public List<User> listRefresh(String startFilter) throws PaggingException; 
	public List<User> listNext() throws PaggingException; 
	public List<User> listNext(String startFilter) throws PaggingException; 
	public List<User> listPrevious() throws PaggingException; 
	public List<User> listPrevious(String startFilter) throws PaggingException;  
	public boolean check() throws PassportException; 
	public boolean updateUsername(String newUsername) throws PassportException; 
	public boolean updatePassword(String neoPassword) throws PassportException;
	public boolean testContains() throws PassportException;
}
