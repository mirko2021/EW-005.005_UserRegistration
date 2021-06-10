package yatospace.user.controller;

import java.util.List;

import yatospace.user.constants.RegistrationExceptionMessageCodebook;
import yatospace.user.frame.UserDataManervar;
import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;

/**
 * Контрола регистровања корисника помоћу објекта у програму као базе података.
 * Укључчује валидацију лозинке из рекорда или класичне.  
 * @author MV
 * @version 1.0
 */
public class DirectRegistrationController implements UserDataManervar{
	private DirectCompactCredentialController engine = new DirectCompactCredentialController(); 
	private String checkingPasswordRecord; 
	
	@Override
	public String getCheckPassportRecord() {
		return checkingPasswordRecord;
	}

	@Override
	public DirectRegistrationController setCheckPassportRecord(String passport) {
		checkingPasswordRecord = passport; 
		return this;
	}

	@Override
	public String getOrGenerateCheckPassportRecord() {
		return checkingPasswordRecord;
	}

	@Override
	public boolean hasCheckPassportRecord() {
		return checkingPasswordRecord != null;
	}

	@Override
	public boolean register(String username, String password) {
		return engine.add(username, password); 
	}

	@Override
	public boolean delete(String username, String oldPassword) { 
		if(!engine.check(username, oldPassword)) return false;
		return engine.remove(username); 
	}

	@Override
	public int count() {
		return engine.count();
	}

	@Override
	public List<User> list(int pageNo, int pageSize, String startFilter) {
		return engine.list(pageNo, pageSize, startFilter);
	}

	@Override
	public boolean check(String username, String password) {
		return engine.check(username, password);
	}

	@Override
	public boolean updateUsername(String username, String newUsername, String oldPassword) {
		if(!engine.check(username, oldPassword)) return false;
		return engine.updateUsername(username, newUsername);
	}

	@Override
	public boolean updatePassword(String username, String neoPassword, String oldPassword) {
		if(!engine.check(username, oldPassword)) return false;
		return engine.updatePassword(username, neoPassword);
	}

	@Override
	public boolean testContains(String username) {
		return engine.testContains(username);
	}

	@Override
	public DirectCompactCredentialController getDataSource() {
		return engine;
	}

	@Override
	public boolean add() throws PassportException {
		if(!engine.hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		return engine.add();
	}

	@Override
	public boolean remove(String oldPassword) throws PassportException {
		if(!engine.hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		if(!engine.check(engine.getPassport().getUsername(), oldPassword)) return false; 
		return engine.remove();
	}

	@Override
	public List<User> listRefresh() throws PaggingException {
		if(!engine.hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		return engine.listRefresh();
	}

	@Override
	public List<User> listRefresh(String startFilter) throws PaggingException {
		if(!engine.hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		return engine.listRefresh(startFilter);
	}

	@Override
	public List<User> listNext() throws PaggingException {
		if(!engine.hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		return engine.listNext();
	}

	@Override
	public List<User> listNext(String startFilter) throws PaggingException {
		if(!engine.hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		return engine.listNext(startFilter);
	}

	@Override
	public List<User> listPrevious() throws PaggingException {
		if(!engine.hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		return engine.listPrevious();
	}

	@Override
	public List<User> listPrevious(String startFilter) throws PaggingException {
		if(!engine.hasPage()) throw new PaggingException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		return engine.listPrevious(startFilter);
	}

	@Override
	public boolean check() throws PassportException {
		if(!engine.hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return engine.check();
	}

	@Override
	public boolean updateUsername(String newUsername, String oldPassword) throws PassportException {
		if(!engine.hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		if(!engine.check(engine.getPassport().getUsername(), oldPassword)) return false;
		return engine.updateUsername(newUsername);
	}

	@Override
	public boolean updatePassword(String neoPassword, String oldPassword) throws PassportException {
		if(!engine.hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		if(!engine.check(engine.getPassport().getUsername(), oldPassword)) return false;
		return engine.updatePassword(neoPassword);
	}

	@Override
	public boolean testContains() throws PassportException {
		if(!engine.hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET);
		return engine.testContains();
	}

	@Override
	public boolean checkRecord(String username, String passwordRecord) {
		return engine.checkRecord(username, passwordRecord);
	}
}
