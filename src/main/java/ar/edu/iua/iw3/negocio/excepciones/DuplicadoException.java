package ar.edu.iua.iw3.negocio.excepciones;

public class DuplicadoException extends Exception {

	private static final long serialVersionUID = 5472509512704239504L;

	public DuplicadoException() {
	
	}

	public DuplicadoException(String message) {
		super(message);
		
	}

	public DuplicadoException(Throwable cause) {
		super(cause);
		
	}

	public DuplicadoException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DuplicadoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
