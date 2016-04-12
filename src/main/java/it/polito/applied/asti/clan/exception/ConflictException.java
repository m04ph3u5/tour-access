package it.polito.applied.asti.clan.exception;

public class ConflictException extends Exception{
	
	private String message;

	public ConflictException() {
		super();
	}

	public ConflictException(String message) {
		super();
		this.message = message;
	}

	public ConflictException(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "ConflictException: "+message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}

