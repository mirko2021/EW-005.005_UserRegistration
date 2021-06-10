package yatospace.user.lang;

/**
 * Грешке код приступнице. 
 * @author MV
 * @version 1.0
 */
public class PassportException extends RuntimeException{
	private static final long serialVersionUID = 7560588648930550742L;

	public PassportException() {
		super();
	}

	public PassportException(String message, Throwable cause) {
		super(message, cause);
	}

	public PassportException(String message) {
		super(message);
	}

	public PassportException(Throwable cause) {
		super(cause);
	}
}
