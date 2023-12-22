package es.wacoco.SpringCamelProject.Service;

import es.wacoco.SpringCamelProject.Cognito.AwsCredentials;
import es.wacoco.SpringCamelProject.error.DuplicateEmailException;
import es.wacoco.SpringCamelProject.error.ErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;
@Service
public class CognitoAuthService {

    private static final Logger logger = LoggerFactory.getLogger(CognitoAuthService.class);
    private final AwsCredentials awsCredentials;

    @Autowired
    public CognitoAuthService(AwsCredentials awsCredentials) {
        this.awsCredentials = awsCredentials;
    }

    public void signUp(String username, String password, String email) {
        try {
            // Check if the email is already in use
            boolean isEmailRegistered = isEmailAlreadyRegistered(email);
            if (isEmailRegistered) {
                throw  new DuplicateEmailException(email);
            }

            logger.info("Initiating user sign-up for username: {}", username);

            SignUpRequest request = SignUpRequest.builder()
                    .clientId(awsCredentials.getCognitoClientId())
                    .username(username)
                    .password(password)
                    .userAttributes(AttributeType.builder().name("email").value(email).build())
                    .build();

            awsCredentials.getCognitoClient().signUp(request);

            // Log confirmation message
            logger.info("User sign-up successful for username: {}", username);
        } catch (DuplicateEmailException e) {
            // Handle the specific duplicate email exception
            ErrorHandler.handleDuplicateEmailError(e.getEmail());
        } catch (UsernameExistsException e) {
            // Handle the specific duplicate username exception
            ErrorHandler.handleDuplicateUsernameError(username);
        } catch (InvalidPasswordException e) {
            ErrorHandler.handleWeakPasswordError();
        } catch (Exception e) {
            // Handle other exceptions
            ErrorHandler.handleGenericError(username, e);
        }
    }


    public void confirmSignUp(String username, String confirmationCode) {
        try {
            logger.info("Confirming sign-up for username: {}", username);

            ConfirmSignUpRequest request = ConfirmSignUpRequest.builder()
                    .clientId(awsCredentials.getCognitoClientId())
                    .username(username)
                    .confirmationCode(confirmationCode)
                    .build();

            awsCredentials.getCognitoClient().confirmSignUp(request);

            // Add newly confirmed user to a Admin group
            addUserToGroup(username);

            // Log confirmation message
            logger.info("Confirmation successful for username: {}", username);
        } catch (CodeMismatchException e) {
            ErrorHandler.handleInvalidConfirmationCodeError(username, e);
        } catch (Exception e) {
            ErrorHandler.handleGenericError(username, e);
        }
    }

    private void addUserToGroup(String username) {
        try {
            // Add the user to the specified group
            AdminAddUserToGroupRequest addUserToGroupRequest = AdminAddUserToGroupRequest.builder()
                    .groupName("Admin")
                    .username(username)
                    .userPoolId(awsCredentials.getCognitoPoolId())
                    .build();
            awsCredentials.getCognitoClient().adminAddUserToGroup(addUserToGroupRequest);

            // Log confirmation message
            logger.info("User added to group: {} - {}", username, "Default");
        } catch (Exception e) {
            ErrorHandler.handleGenericError(username, e);
        }
    }
    // Check if the email is already registered
    private boolean isEmailAlreadyRegistered(String email) {
        try {
            AdminGetUserRequest request = AdminGetUserRequest.builder()
                    .username(email)
                    .userPoolId(awsCredentials.getCognitoPoolId())
                    .build();
//            logger.info("checking isEmailAlreadyRegistered {}", request);

            awsCredentials.getCognitoClient().adminGetUser(request);
            return true; // User with this email already exists
        } catch (UserNotFoundException e) {
            return false; // User with this email does not exist
        }
    }
}
