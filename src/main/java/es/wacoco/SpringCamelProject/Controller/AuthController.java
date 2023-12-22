package es.wacoco.SpringCamelProject.Controller;


import es.wacoco.SpringCamelProject.Service.CognitoAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final CognitoAuthService cognitoAuthService;

    @Autowired
    public AuthController(CognitoAuthService cognitoAuthService) {
        this.cognitoAuthService = cognitoAuthService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email) {
        try {
            cognitoAuthService.signUp(username, password, email);
            return ResponseEntity.status(HttpStatus.OK).body("Signup successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmSignUp(@RequestParam String username, @RequestParam String confirmationCode) {
        try {
            cognitoAuthService.confirmSignUp(username, confirmationCode);
            return ResponseEntity.ok("Confirmation successful");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
