package es.wacoco.SpringCamelProject.Service;

import es.wacoco.SpringCamelProject.Cognito.AwsCredentials;
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
            logger.info("Initiating user sign-up for username: {}", username);
            SignUpRequest request = SignUpRequest.builder()
                    .clientId(awsCredentials.getCognitoClientId())
                    .username(username)
                    .password(password)
                    .build();

            awsCredentials.getCognitoClient().signUp(request);

            // Log confirmation message
            logger.info("User sign-up successful for username: {}", username);
    }

    public void confirmSignUp(String username, String confirmationCode) {

            logger.info("Confirming sign-up for username: {}", username);
            ConfirmSignUpRequest request = ConfirmSignUpRequest.builder()
                    .clientId(awsCredentials.getCognitoClientId())
                    .username(username)
                    .confirmationCode(confirmationCode)
                    .build();

            awsCredentials.getCognitoClient().confirmSignUp(request);
            //add newly confirmed user to a default group
            addUserToGroup(username, "Default");

            // Log confirmation message
            logger.info("Confirmation successful for username: {}", username);

    }

    public void addUserToGroup(String username, String groupName) {

            // Add the user to the specified group
            AdminAddUserToGroupRequest addUserToGroupRequest = AdminAddUserToGroupRequest.builder()
                    .groupName(groupName)
                    .username(username)
                    .userPoolId(awsCredentials.getCognitoPoolId())
                    .build();
            awsCredentials.getCognitoClient().adminAddUserToGroup(addUserToGroupRequest);

            // Log confirmation message
            logger.info("User added to group: {} - {}", username, groupName);
    }
}
