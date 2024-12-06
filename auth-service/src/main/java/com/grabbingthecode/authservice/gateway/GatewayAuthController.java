package com.grabbingthecode.authservice.gateway;

import com.grabbingthecode.authservice.securityConfig.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/gateway-request/validate")
@RequiredArgsConstructor
@Slf4j
public class GatewayAuthController {

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    @PostMapping()
    public boolean validateToken(@RequestBody String token) {
        log.info("Received token from the Gateway-Service: {}", token);
        String username = jwtService.extractUserName(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtService.isTokenValid(token, userDetails);
    }
}
