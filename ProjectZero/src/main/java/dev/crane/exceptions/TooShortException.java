package dev.crane.exceptions;

public class TooShortException extends Exception {

	private static final long serialVersionUID = 1L;

	int nameLenght;
	
	public TooShortException(int nameLength) {
		super("Username must be at least 6 letters long!");
		this.nameLenght = nameLength;
	}

	public int getNameLenght() {
		return nameLenght;
	}

	public void setNameLenght(int nameLenght) {
		this.nameLenght = nameLenght;
	}

}
