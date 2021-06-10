package yatospace.user.lang;

/**
 * Грешке услед страничења. 
 * @author MV
 * @version 1.0
 */
public class PaggingException extends RuntimeException{
	private static final long serialVersionUID = -4304470401937462463L;

	public PaggingException() {
		super();
	}

	public PaggingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PaggingException(String arg0) {
		super(arg0);
	}

	public PaggingException(Throwable arg0) {
		super(arg0);
	}
}
