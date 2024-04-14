package BlogBackned.controller;

import BlogBackned.request.UserRequest;
import BlogBackned.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid  @RequestBody  UserRequest userRequest) {
        System.out.println("login");
        return ResponseEntity.ok(authService.authenticate(userRequest));
    }
}
