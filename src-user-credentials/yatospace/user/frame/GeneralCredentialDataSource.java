package yatospace.user.frame;

import java.util.List;
import java.util.Map;

import yatospace.user.controller.UserCredentialsController;
import yatospace.user.object.User;
import yatospace.user.part.ManervarCredentialsFunctionals;
import yatospace.user.part.OperationalCredentialsFunctionals;
import yatospace.user.util.SaltGeneratorEngine;

/**
 * Оквир функционалности које би требао да има извор података, када је у питању рад 
 * са корисницима. 
 * @author MV
 * @version 1.0
 */
public interface GeneralCredentialDataSource extends ManervarCredentialsFunctionals, OperationalCredentialsFunctionals{
	public SaltGeneratorEngine getSaltgenerator(); 
	public Map<String, UserCredentialsController> getCredentialscontrollers(); 
	public UserCredentialsController getController(String username);
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
