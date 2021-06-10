package yatospace.user.controller;

import java.util.List;

import yatospace.user.constants.RegistrationExceptionMessageCodebook;
import yatospace.user.frame.CompactCredentialDataSource;
import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;
import yatospace.user.util.Page;
import yatospace.user.util.Passport;

/**
 * Контролер за креденцијале и операције, постоји задржавње података. 
 * @author MV
 * @version 1.0
 */
public class DirectCompactCredentialController extends DirectCredentialController implements CompactCredentialDataSource{ 
	private Page page; 
	private Passport passport; 
	

	public Page getPage() {
		return page;
	}

	public DirectCompactCredentialController setPage(Page page) {
		this.page = page;
		return this;
	}

	public Passport getPassport() {
		return passport;
	}

	public DirectCompactCredentialController setPassport(Passport passport) {
		this.passport = passport;
		return this; 
	}

	public DirectCompactCredentialController resetPage() {
		page = null; 
		return this; 
	}
	
	public DirectCompactCredentialController resetPassword() {
		passport = null; 
		return this; 
	}
	
	public Page getOrGeneratePage() {
		if(page==null) page = new Page(); 
		return page; 
	}
	
	public Passport getOrGeneratePassport() {
		if(passport==null) passport = new Passport();
		return passport; 
	}
	
	public boolean hasPage() {
		return page!=null; 
	}
	
	public boolean hasPassport() {
		return passport!=null; 
	}

	@Override
	public boolean add(String username, String password) {
		return super.add(username, password);
	}

	
	public boolean add() throws PassportException{
		try {
			if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
			return super.add(passport.getUsername(), passport.getPassword());
		}finally {
			passport.setPassword(getController(passport.getUsername()).getCredentialsTool().getPasswordRecord());
		}
	}
	
	
	public boolean remove() throws PassportException{
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return super.remove(passport.getUsername());
	}
	
	@Override
	public boolean remove(String username) {
		return super.remove(username);
	}
	
	public int count() {
		return super.count();
	}
	
	@Override
	public List<User> list(int pageNo, int pageSize, String startFilter) {
		return super.list(pageNo, pageSize, startFilter);
	}

	public List<User> listRefresh() throws PaggingException{
		if(!hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET); 
		return super.list(page.getPageNo(), page.getPageSize(), page.getStartFilter());
	}
	
	public List<User> listRefresh(String startFilter) throws PaggingException{
		if(!hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET); 
		page.setStartFilter(startFilter);
		return super.list(page.getPageNo(), page.getPageSize(), page.getStartFilter());
	}
	
	public List<User> listNext() throws PaggingException{
		if(!hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET); 
		page.next(); 
		return super.list(page.getPageNo(), page.getPageSize(), page.getStartFilter());
	}
	
	public List<User> listNext(String startFilter) throws PaggingException{
		if(!hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET); 
		page.next();
		page.setStartFilter(startFilter);
		return super.list(page.getPageNo(), page.getPageSize(), page.getStartFilter());
	}
	
	public List<User> listPrevious() throws PaggingException{
		if(!hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET); 
		page.previous(); 
		return super.list(page.getPageNo(), page.getPageSize(), page.getStartFilter());
	}
	
	public List<User> listPrevious(String startFilter) throws PaggingException{
		if(!hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET); 
		page.previous();
		page.setStartFilter(startFilter);
		return super.list(page.getPageNo(), page.getPageSize(), page.getStartFilter());
	}
	
	@Override
	public boolean check(String username, String password) {
		return super.check(username, password);
	}

	public boolean check() throws PassportException{
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return super.checkRecord(passport.getUsername(), passport.getPassword());
	}
	
	@Override
	public boolean updateUsername(String username, String newUsername) {
		return super.updateUsername(username, newUsername);
	}

	@Override
	public boolean updatePassword(String username, String neoPassword) {
		return super.updatePassword(username, neoPassword);
	}

	@Override
	public boolean testContains(String username) {
		return super.testContains(username);
	}

	@Override
	public boolean updateUsername(String newUsername) throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return super.updateUsername(passport.getUsername(), newUsername);
	}

	@Override
	public boolean updatePassword(String neoPassword) throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return super.updateUsername(passport.getUsername(), neoPassword);
	}

	@Override
	public boolean testContains() throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return super.testContains(passport.getUsername());
	}

	@Override
	public boolean checkRecord(String username, String passwordRecord) {
		return super.checkRecord(username, passwordRecord);
	}
}
