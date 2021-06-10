package yatospace.user.database.data;

import java.io.Serializable;

import yatospace.user.util.SaltGeneratorEngine;

/**
 * Објекат примопредаје података атрибута о креденцијалима. 
 * @author MV
 * @version 1.0
 */
public class MySQLCredentialsDTO implements Serializable{
	private static final long serialVersionUID = -2233747317267331532L;
	private SaltGeneratorEngine slatGenerator;
	
	public SaltGeneratorEngine getSlatGenerator() {
		return slatGenerator;
	}

	public void setSlatGenerator(SaltGeneratorEngine slatGenerator) {
		this.slatGenerator = slatGenerator;
	}
}
