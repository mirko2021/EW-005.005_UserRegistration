package yatospace.user.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yatospace.user.frame.GeneralCredentialDataSource;
import yatospace.user.object.User;
import yatospace.user.util.SaltGeneratorEngine;

/**
 * Издвојен директни контролер за баратање са операцијама корисника.
 * @author MV
 * @version 1.0
 */
public class DirectCredentialController implements GeneralCredentialDataSource{
	private  SaltGeneratorEngine saltGenerator = new SaltGeneratorEngine(); 
	private  HashMap<String, UserCredentialsController> credentialsControllers = new HashMap<>();
	
	public SaltGeneratorEngine getSaltgenerator() {
		return saltGenerator;
	}
	
	public Map<String, UserCredentialsController> getCredentialscontrollers() {
		return new HashMap<>(credentialsControllers);
	} 
	
	public UserCredentialsController getController(String username) {
		return credentialsControllers.get(username); 
	}
	
	public boolean add(String username, String password) { 
		if(credentialsControllers.containsKey(username)) return false; 
		UserCredentialsController controller = new UserCredentialsController(); 
		controller.setSaltGeneratorTool(saltGenerator);
		controller.setUser(new User(username)); 
		boolean passwordOK = false; 
		if(controller.setGoodPassword(password).length()>0) {
			passwordOK=true;
			controller.getCredentialsTool().hashPasswordToAll().resetPasswordPlain(); 
		}
		if(!passwordOK) return false; 
		credentialsControllers.put(username, controller);
		return true;  
	}
	
	public boolean remove(String username) {
		if(!credentialsControllers.containsKey(username)) return false;
		credentialsControllers.remove(username);
		return true; 
	}
	
	public int count() {
		return credentialsControllers.size(); 
	}
	
	public List<User> list(int pageNo, int pageSize, String startFilter) {
		try {
			if(pageNo<0)   pageNo=0;
			if(pageSize<1) pageSize=1; 
			if(startFilter==null) startFilter=""; 
			
			if(pageNo==0) return new ArrayList<>(); 
			pageNo--;
			
			int a = pageNo*pageSize;
			int b = pageNo*pageSize+pageSize;
	
			ArrayList<String> users = new ArrayList<>(credentialsControllers.keySet()); 
			ArrayList<String> target = users; users = new ArrayList<>(); 
			
			for(String str: target) if(str.startsWith(startFilter)) users.add(str); 
			Collections.sort(users);
			
			a = Math.min(a, users.size());
			b = Math.min(b, users.size());
			
			ArrayList<User> result = new ArrayList<>();
			
			for(int i=a;i<b;i++)
				result.add(new User(users.get(i))); 
			
			return result; 
		}catch(Exception ex) {
			return new ArrayList<>(); 
		}
	}
	
	
	public boolean check(String username, String password) {		
		UserCredentialsController controller = credentialsControllers.get(username); 
		if(controller==null) { return false; }
		
		boolean result = controller.checkPassword(password);		
		controller.getCredentialsTool().resetPasswordPlain();
		
		return result;
	}
	
	
	public boolean updateUsername(String username, String newUsername) {
		UserCredentialsController controller = credentialsControllers.get(username); 
		if(controller==null) { return false; }
		
		UserCredentialsController newController = credentialsControllers.get(newUsername);
		if(newController!=null) { return false; }
		
		credentialsControllers.remove(username); 
		controller.getUser().setUsername(newUsername);
		credentialsControllers.put(newUsername, controller);
		
		return true;
	}
	
	public boolean updatePassword(String username, String neoPassword) {
		UserCredentialsController controller = credentialsControllers.get(username); 
		if(controller==null) { return false; }
		
		controller.setPassword(neoPassword); 
		controller.getCredentialsTool().resetPasswordPlain(); 
		
		return true;
	}
	
	public boolean testContains(String username) {
		return credentialsControllers.containsKey(username); 
	}

	@Override
	public boolean checkRecord(String username, String passwordRecord) {
		UserCredentialsController controller = credentialsControllers.get(username); 
		if(controller==null) { return false; }
		
		boolean result = controller.getCredentialsTool().getPasswordRecord().contentEquals(passwordRecord);		
		return result;
	}
}
