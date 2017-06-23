package fr.eni.formation.banque;

public class BanqueException extends Exception {

	private static final long serialVersionUID = 6736454924663578605L;

	public BanqueException(String message) {
		super(message);
	}

	public BanqueException(String message, Throwable cause) {
		super(message, cause);
	}

}
