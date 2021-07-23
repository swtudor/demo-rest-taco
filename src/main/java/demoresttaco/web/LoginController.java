package demoresttaco.web;

import demoresttaco.domain.AuthenticationRequest;
import demoresttaco.domain.AuthenticationResponse;
import demoresttaco.security.MyUserDetailsService;
import demoresttaco.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/authenticate")
@RestController
@CrossOrigin(origins = "*")
public class LoginController {
    private AuthenticationManager authManager;
    private MyUserDetailsService detailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationManager authManager, MyUserDetailsService detailsService, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.detailsService = detailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest login) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch(BadCredentialsException bce) {
            throw new Exception("no entry for you", bce);
        }
        final UserDetails userDetails = detailsService.loadUserByUsername(login.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
