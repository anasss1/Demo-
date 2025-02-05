package com.example.Non-Block;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody LoginRequest loginRequest) {
        // Validation simple (peut être remplacé par un service réactif)
        if ("user".equals(loginRequest.getUsername()) && "password".equals(loginRequest.getPassword())) {
            return jwtTokenProvider.generateToken(loginRequest.getUsername())
                    .map(token -> "Bearer " + token);
        } else {
            return Mono.error(new RuntimeException("Invalid credentials"));
        }
    }
}

