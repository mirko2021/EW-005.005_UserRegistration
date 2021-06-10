package yatospace.user.part;

import java.util.List;

import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;

/**
 * Оперативност при скраћеним функционалстима. 
 * @author MV
 * @version 1.0
 */
public interface ShortedOperationalFunctional {
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
