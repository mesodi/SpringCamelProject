package es.wacoco.SpringCamelProject.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CodeMismatchException;

public class ErrorHandler {
    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public static void handleDuplicateUsernameError(String username) {
        String errorMessage = "Username '" + username + "' is already taken. Please choose a different username.";
        logger.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }
    public static void handleDuplicateEmailError(String email) {
        String errorMessage = "Email '" + email + "' is already registered. Please use a different email address.";
        logger.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }

    public static void handleWeakPasswordError() {
        String errorMessage = "Password is too weak. Please choose a stronger password.";
        logger.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }

    public static void handleInvalidConfirmationCodeError(String username, CodeMismatchException e) {
        String errorMessage = "Invalid confirmation code for username: " + username + ". Please double-check and try again.";
        logger.error(errorMessage, e);
        throw new RuntimeException(errorMessage, e);
    }

    public static void handleOtherRegisterError(String username, Exception e) {
        String errorMessage = "An unexpected error occurred during registering: " + username;
        logger.error(errorMessage, e);
        throw new RuntimeException(errorMessage, e);
    }
    public static void handleConfirmationError(String username, Exception e) {
        String errorMessage = "An unexpected error occurred during Confirmation with the User: " + username;
        logger.error(errorMessage, e);
        throw new RuntimeException(errorMessage, e);
    }
}