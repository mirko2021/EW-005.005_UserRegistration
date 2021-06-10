package yatospace.user.part;

import java.util.Map;

import yatospace.user.controller.UserCredentialsController;
import yatospace.user.util.SaltGeneratorEngine;

/**
 * Функционалности које се ондосе за баратање са креденцијалним вриједностима и контролама. 
 * @author MV
 * @version 1.0
 */
public interface ManervarCredentialsFunctionals {
	public SaltGeneratorEngine getSaltgenerator(); 
	public Map<String, UserCredentialsController> getCredentialscontrollers(); 
	public UserCredentialsController getController(String username);
}
