package order.payment.parser.exception;

public class ParsingException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParsingException(String msg, Throwable e) {
		super(msg, e);
	}

}
