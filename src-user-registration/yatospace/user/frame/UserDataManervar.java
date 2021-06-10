package yatospace.user.frame;

import yatospace.user.part.ManervarRegisterFunctionals;
import yatospace.user.part.OperationaRegisterFunctionals;
import yatospace.user.part.PassprotRegisterFunctionals;
import yatospace.user.part.ShortedRegisterFunctionals;

/**
 * Уопштење за прости манервар и контролу радом са подацима корисничког регистра и регистрацијом. 
 * @author MV
 * @version 1.0
 */
public interface UserDataManervar extends PassprotRegisterFunctionals, OperationaRegisterFunctionals, ManervarRegisterFunctionals, ShortedRegisterFunctionals{}
