package yatospace.user.part;

/**
 * Постављање параметара за пропуснице за операције модификације. 
 * @author MV
 * @version 1.0
 */
public interface PassprotRegisterFunctionals {
	public String getCheckPassportRecord();
	public PassprotRegisterFunctionals setCheckPassportRecord(String passport);
	public String getOrGenerateCheckPassportRecord(); 
	public boolean hasCheckPassportRecord();
}
