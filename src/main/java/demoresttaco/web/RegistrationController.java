package demoresttaco.web;

import demoresttaco.data.UserRepository;
import demoresttaco.domain.User;
import demoresttaco.security.Registration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path = "/register")
@CrossOrigin(origins = "*")
public class RegistrationController {
    private UserRepository userRepository;
    private PasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserRepository userRepository, PasswordEncoder encoder){
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody Registration registration){
        User user = registration.convertUser(encoder);
        User saved = userRepository.save(user);
        log.info("Successfully registered user: " + saved);
        return ResponseEntity.ok().body("User successfully registered");
    }
}
