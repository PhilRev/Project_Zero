package dev.crane.exceptions;

public interface InputValidator {

	boolean validateInput(String username) throws UnallowedException, TooShortException;

}
