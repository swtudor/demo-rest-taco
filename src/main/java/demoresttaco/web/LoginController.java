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
    private MyUserDetailsService muds;
    private JwtUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationManager authManager, MyUserDetailsService muds, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.muds = muds;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authReq) throws Exception {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getUsername(), authReq.getPassword()));
        } catch(BadCredentialsException bce) {
            throw new Exception("no entry for you", bce);
        }
        UserDetails userDetails = muds.loadUserByUsername(authReq.getUsername());
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
