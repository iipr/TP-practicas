package tp.pr5.instructions.exceptions;

public class WrongInstructionFormatException extends Exception {



	public WrongInstructionFormatException() {
		// TODO Auto-generated constructor stub
	}

	public WrongInstructionFormatException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	private String message;	
	private static final long serialVersionUID = 1L;	//Daba warning
}
