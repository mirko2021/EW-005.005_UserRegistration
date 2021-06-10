package yatospace.user.database.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yatospace.user.constants.RegistrationExceptionMessageCodebook;
import yatospace.user.controller.DirectCompactCredentialController;
import yatospace.user.controller.UserCredentialsController;
import yatospace.user.database.data.MySQLUserRegisterDTO;
import yatospace.user.database.io.MySQLCredentialsDAO;
import yatospace.user.database.io.MySQLUserRegisterDAO;
import yatospace.user.frame.CompactCredentialDataSource;
import yatospace.user.lang.PaggingException;
import yatospace.user.lang.PassportException;
import yatospace.user.object.User;
import yatospace.user.util.Page;
import yatospace.user.util.Passport;
import yatospace.user.util.SaltGeneratorEngine;
import yatospace.user.util.UserCredentials;

/**
 * Контролер који се односи на унос и баратање са корисничким креденцијалима.
 * @author MV
 * @version 1.0
 */
public class MySQLCredentialsController extends DirectCompactCredentialController implements CompactCredentialDataSource{
	private MySQLUserRegisterDAO dataSource; 
	private MySQLCredentialsDAO parameterSoucre; 
	
	private SaltGeneratorEngine saltGenerator; 
	private HashMap<String, UserCredentialsController> controllers = new HashMap<>(); 
	
	private Page page; 
	private Passport passport; 
	
	public MySQLCredentialsController(MySQLUserRegisterDAO dataSource, MySQLCredentialsDAO parameterSoucre) {
		if(dataSource==null) throw new NullPointerException();
		if(parameterSoucre==null) throw new NullPointerException();
		this.dataSource = dataSource; 
		this.parameterSoucre = parameterSoucre; 
		try{this.saltGenerator = parameterSoucre.getSaltGenerator().getSlatGenerator(); }catch(Exception ex) {this.saltGenerator = new SaltGeneratorEngine();}
	}
	
	
	public MySQLUserRegisterDAO getDataSource() {
		return dataSource;
	}

	public MySQLCredentialsDAO getParameterSoucre() {
		return parameterSoucre;
	}

	@Override
	public SaltGeneratorEngine getSaltgenerator() {
		return saltGenerator;
	}

	
	@Override
	public Map<String, UserCredentialsController> getCredentialscontrollers() {
		throw new UnsupportedOperationException(); 
	}

	@Override
	public UserCredentialsController getController(String username) {
		try {
			UserCredentialsController controller = controllers.get(username); 
			if(controller!=null) return controller; 
			MySQLUserRegisterDTO dto = dataSource.get(username);
			if(dto==null) return null;
			if(dto.getCredentials()==null) return null; 
			controller = new UserCredentialsController(); 
			controller.setCredentialsTool(dto.getCredentials()).setUser(new User(username)); 
			controllers.put(username, controller); 
			return controller;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public boolean checkRecord(String username, String passwordRecord) {
		try {
			return dataSource.testRecordPassword(username, passwordRecord);
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public Page getPage() {
		return page;
	}

	@Override
	public MySQLCredentialsController setPage(Page page) {
		this.page = page; 
		return this;
	}

	@Override
	public Passport getPassport() {
		return passport;
	}

	@Override
	public MySQLCredentialsController setPassport(Passport passport) {
		this.passport = passport;
		return this;
	}

	@Override
	public MySQLCredentialsController resetPage() {
		page = null;
		return this;
	}

	@Override
	public MySQLCredentialsController resetPassword() {
		passport=null; 
		return this;
	}

	@Override
	public Page getOrGeneratePage() {
		if(page!=null) return page; 
		page = new Page();
		return page;
	}

	@Override
	public Passport getOrGeneratePassport() {
		if(passport==null) return passport; 
		passport = new Passport(); 
		return passport;
	}

	@Override
	public boolean hasPage() {
		return page!=null;
	}

	@Override
	public boolean hasPassport() {
		return passport!=null;
	}

	@Override
	public boolean add(String username, String password) {
		try {
			if(username==null) return false;
			if(password==null) return false; 
			MySQLUserRegisterDTO dto = new MySQLUserRegisterDTO(); 
			UserCredentials credentials = new UserCredentials();
			credentials.setPasswordSalt(saltGenerator.generateNewCode()); 
			dto.setCredentials(credentials);
			credentials.setUser(new User(username)); 
			credentials.setPasswordPlain(password); 
			credentials.hashPasswordToAll(); 
			credentials.resetPasswordPlain(); 
			return dataSource.insert(dto); 
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean add() throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		try {
			MySQLUserRegisterDTO dto = new MySQLUserRegisterDTO(); 
			UserCredentials credentials = new UserCredentials();
			dto.setCredentials(credentials);
			credentials.setUser(new User(passport.getUsername())); 
			credentials.setPasswordRecord(passport.getPassword()); 
			return dataSource.insert(dto); 
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean remove() throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		try {
			return dataSource.remove(passport.getUsername());
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally {
			controllers.remove(passport.getUsername());
		}
	}

	@Override
	public boolean remove(String username) {
		try {
			return dataSource.remove(username);
		}catch(RuntimeException ex) {
			throw ex;
		}catch(Exception ex) {
			throw new RuntimeException(ex);
		}finally{
			controllers.remove(username);
		}
	}

	@Override
	public int count() {
		try {
			return dataSource.count();
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> list(int pageNo, int pageSize, String startFilter) {
		try {
			Page page = new Page(); 
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			page.setStartFilter(startFilter);
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> listRefresh() throws PaggingException {
		if(!hasPage()) throw new RuntimeException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		try {
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> listRefresh(String startFilter) throws PaggingException {
		if(!hasPage()) throw new RuntimeException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		try {
			Page page = new Page();
			page.setPageNo(this.page.getPageNo());
			page.setPageSize(this.page.getPageSize());
			page.setStartFilter(startFilter);
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> listNext() throws PaggingException {
		if(!hasPage()) throw new RuntimeException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		try {
			page.next();
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> listNext(String startFilter) throws PaggingException {
		if(!hasPage()) throw new RuntimeException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		try {
			page.next();
			Page page = new Page();
			page.setPageNo(this.page.getPageNo());
			page.setPageSize(this.page.getPageSize());
			page.setStartFilter(startFilter);
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> listPrevious() throws PaggingException {
		if(!hasPage()) throw new RuntimeException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		try {
			page.previous();
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public List<User> listPrevious(String startFilter) throws PaggingException {
		if(!hasPage()) throw new RuntimeException(RegistrationExceptionMessageCodebook.PAGGING_NOT_SET);
		try {
			page.previous();
			Page page = new Page();
			page.setPageNo(this.page.getPageNo());
			page.setPageSize(this.page.getPageSize());
			page.setStartFilter(startFilter);
			ArrayList<User> list = new ArrayList<>(); 
			for(MySQLUserRegisterDTO dto : dataSource.list(page))
				list.add(dto.getCredentials().getUser());
			return list;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public boolean check(String username, String password) {
		try {
			return dataSource.testPlainPassword(username, password);
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public boolean check() throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		try {
			return dataSource.testRecordPassword(passport.getUsername(), passport.getPassword());
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public boolean updateUsername(String username, String newUsername) {
		boolean success = false; 
		try {
			return success = dataSource.rename(username, newUsername);
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			if(!success) return false; 
			UserCredentialsController controller = controllers.get(username); 
			if(controller==null) return success;
			controller.getUser().getUser().setUsername(newUsername);
			controllers.remove(username); 
			controllers.put(newUsername, controller); 
		}
	}

	@Override
	public boolean updatePassword(String username, String neoPassword) {
		boolean success = false;
		String passwordRecord = "";
		try {
			UserCredentials workerFormCredentials = new UserCredentials(); 
			workerFormCredentials.setPasswordSalt(dataSource.get(username).getCredentials().getPasswordSalt()); 
			workerFormCredentials.setUser(new User(username)).setPasswordPlain(neoPassword).hashPasswordToAll(); 
			success = dataSource.updatePassword(username, workerFormCredentials.getPasswordRecord());
			if(success) passwordRecord = dataSource.get(username).getCredentials().getPasswordRecord(); 
			return success; 
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			if(!success) return false; 
			if(controllers.get(username)==null) return success; 
			controllers.get(username).getCredentialsTool().setPasswordRecord(passwordRecord).convertRecordToHashSaltCombination();
		}
	}

	@Override
	public boolean testContains(String username) {
		try {
			return dataSource.get(username) != null;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}

	@Override
	public boolean updateUsername(String newUsername) throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		boolean success = false; 
		try {
			return success = dataSource.rename(passport.getUsername(), newUsername);
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			if(!success) return false; 
			UserCredentialsController controller = controllers.get(passport.getUsername()); 
			if(controller==null) return success;
			controller.getUser().getUser().setUsername(newUsername);
			controllers.remove(passport.getUsername()); 
			controllers.put(newUsername, controller); 
		}
	}

	@Override
	public boolean updatePassword(String neoPassword) throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		boolean success = false; 
		String  newPasswordRecord = ""; 
		try {
			UserCredentials workerFormCredentials = new UserCredentials(); 
			workerFormCredentials.setPasswordSalt(dataSource.get(passport.getPassword()).getCredentials().getPasswordSalt()); 
			workerFormCredentials.setUser(new User(passport.getPassword())).setPasswordPlain(neoPassword).hashPasswordToAll();
			success = dataSource.updatePassword(passport.getUsername(), workerFormCredentials.getPasswordRecord());
			if(success) newPasswordRecord = dataSource.get(passport.getUsername()).getCredentials().getPasswordRecord(); 
			return success; 
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}finally {
			if(!success) return false; 
			if(controllers.get(passport.getUsername())==null) return success; 
			controllers.get(passport.getUsername()).getCredentialsTool().setPasswordRecord(newPasswordRecord).convertRecordToHashSaltCombination();
		}
	}

	@Override
	public boolean testContains() throws PassportException {
		if(!hasPassport()) throw new PassportException(RegistrationExceptionMessageCodebook.PASSPORT_NOT_SET); 
		try {
			return dataSource.get(passport.getUsername())!=null;
		}catch(RuntimeException ex) {
			throw ex; 
		}catch(Exception ex) {
			throw new RuntimeException(ex); 
		}
	}
}
