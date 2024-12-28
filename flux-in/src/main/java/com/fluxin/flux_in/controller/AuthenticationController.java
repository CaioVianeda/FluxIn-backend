package com.fluxin.flux_in.controller;

import com.fluxin.flux_in.dto.authenticationDTO.AuthenticationDTO;
import com.fluxin.flux_in.dto.authenticationDTO.TokenJwtDTO;
import com.fluxin.flux_in.infra.security.TokenService;
import com.fluxin.flux_in.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity login (@RequestBody AuthenticationDTO data) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        var authentication = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.buildToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenJwtDTO(tokenJWT));
    }
}
