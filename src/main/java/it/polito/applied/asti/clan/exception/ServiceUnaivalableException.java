package it.polito.applied.asti.clan.exception;

public class ServiceUnaivalableException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3784288996362045061L;
	private String message;

	public ServiceUnaivalableException() {
		super();
	}

	public ServiceUnaivalableException(String message) {
		super();
		this.message = message;
	}

	public ServiceUnaivalableException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ServiceUnaivalableException: "+message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
