package dev.crane.exceptions;

public class InputValidatorImpl implements InputValidator {

	public boolean validateInput(String username) throws UnallowedException, TooShortException {
		
		if (username.contains(" ")) {
			UnallowedException cue = new UnallowedException();
			throw cue;
		}
		
		if (username.length() < 6) {
			TooShortException tse = new TooShortException(username.length());
			throw tse;
		}
		
		return true;
	}
	
}
