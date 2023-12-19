package es.wacoco.SpringCamelProject.Controller;

import es.wacoco.SpringCamelProject.Service.CognitoAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    private final CognitoAuthService cognitoAuthService;

    @Autowired
    public AuthController(CognitoAuthService cognitoAuthService) {
        this.cognitoAuthService = cognitoAuthService;
    }
}
