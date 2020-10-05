package socketserver;
/**
 *  A custom exception class we can throw back up the hierarchy to catch critical errors.
 *  All other exceptions should be caught and handled in such way that the application does not end.
 *  
 * @author Michael Reeeb
 *
 */
public class SmartMeterServerException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SmartMeterServerException(String message) {
		super(message);
	}

}
