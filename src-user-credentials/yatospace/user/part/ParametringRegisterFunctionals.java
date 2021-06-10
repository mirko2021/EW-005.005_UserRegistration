package yatospace.user.part;

import yatospace.user.util.Page;
import yatospace.user.util.Passport;

/**
 * Параметризације контролера за баратање креденцијалима. 
 * @author MV
 * @version 1.0
 */
public interface ParametringRegisterFunctionals {
	public Page getPage();
	public ParametringRegisterFunctionals setPage(Page page); 
	public Passport getPassport();
	public ParametringRegisterFunctionals setPassport(Passport passport);
	public ParametringRegisterFunctionals resetPage();
	public ParametringRegisterFunctionals resetPassword();
	public Page getOrGeneratePage(); 
	public Passport getOrGeneratePassport(); 
	public boolean hasPage();
	public boolean hasPassport();
}
