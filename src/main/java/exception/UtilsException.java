package exception;

public class UtilsException extends RuntimeException {
	private static final long serialVersionUID = -6107121310339669978L;

	private String errorCode;
	
	private String errorMessage;
	
	public UtilsException(String errorCode){
		this.errorCode = errorCode;
	}
	
	public UtilsException(String errorCode, Throwable e){
		super(e);
		this.errorCode = errorCode;
	}

	public UtilsException(String errorCode, String errorMessage) {
		super("errorCode="+errorCode+"|errorMessage="+errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
