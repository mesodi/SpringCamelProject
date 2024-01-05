package es.wacoco.SpringCamelProject.Auth.Controller;


import es.wacoco.SpringCamelProject.Auth.Service.CognitoAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final CognitoAuthService cognitoAuthService;

    @Autowired
    public AuthController(CognitoAuthService cognitoAuthService) {
        this.cognitoAuthService = cognitoAuthService;
    }
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            Model model) {
        try {
            cognitoAuthService.signUp(username, password, email);
            return "redirect:/confirm";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/confirm")
    public String showConfirmPage() {
        return "confirm";
    }

    @PostMapping("/confirm")
    public String confirm(
            @RequestParam String username,
            @RequestParam String confirmationCode,
            Model model) {
        try {
            cognitoAuthService.confirmSignUp(username, confirmationCode);
            return "redirect:/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "confirm";
        }
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {
        try {
            // Call the signIn method from CognitoAuthService
            cognitoAuthService.signIn(username, password);
            return "redirect:/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }
}
