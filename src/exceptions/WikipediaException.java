package exceptions;

public class WikipediaException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WikipediaException() {
		System.out.println("City not found.");
	}

}
