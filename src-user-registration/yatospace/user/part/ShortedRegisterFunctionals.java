package yatospace.user.part;

import java.util.List;

import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;

/**
 * Скраћене функционалности за управљање корисницима у смислу само уношења шифре при промјена. 
 * @author MV
 * @version 1.0
 */
public interface ShortedRegisterFunctionals {
	public boolean add() throws PassportException; 
	public boolean remove(String oldPassword) throws PassportException; 
	public int count(); 
	public List<User> listRefresh() throws PaggingException;
	public List<User> listRefresh(String startFilter) throws PaggingException; 
	public List<User> listNext() throws PaggingException; 
	public List<User> listNext(String startFilter) throws PaggingException; 
	public List<User> listPrevious() throws PaggingException; 
	public List<User> listPrevious(String startFilter) throws PaggingException;  
	public boolean check() throws PassportException; 
	public boolean updateUsername(String newUsername, String oldPassword) throws PassportException; 
	public boolean updatePassword(String neoPassword, String oldPassword) throws PassportException;
	public boolean testContains() throws PassportException;
}
