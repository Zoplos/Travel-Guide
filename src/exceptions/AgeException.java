package exceptions;

public class AgeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AgeException() {
		System.out.println("Please enter a valid age.");
	}

}
