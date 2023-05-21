package com.example.bookhotel.controller;

import com.example.bookhotel.dtos.AccountDto;
import com.example.bookhotel.dtos.TokenDetails;
import com.example.bookhotel.dtos.user.RegisterDto;
import com.example.bookhotel.entities.TaiKhoan;
import com.example.bookhotel.exceptions.InvalidException;
import com.example.bookhotel.exceptions.UserNotFoundAuthenticationException;
import com.example.bookhotel.securities.CustomUserDetailsService;
import com.example.bookhotel.securities.JwtTokenUtils;
import com.example.bookhotel.securities.JwtUserDetails;
import com.example.bookhotel.securities.UserAuthenticationToken;
import com.example.bookhotel.service.User.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/rest/login")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final JwtTokenUtils jwtTokenUtils;


    private final RestTemplate restTemplate = new RestTemplate();
    private final UserService userService;
    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService,
                                    JwtTokenUtils jwtTokenUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.userService = userService;
    }

    @ApiOperation(value = "login form (username, password), avatar null")
    @PostMapping
    public ResponseEntity<TokenDetails> login(@Valid @RequestBody AccountDto dto) {
        UserAuthenticationToken authenticationToken = new UserAuthenticationToken(
                dto.getUsername(),
                dto.getPassword(),
                true
        );
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (UserNotFoundAuthenticationException | BadCredentialsException ex) {
            throw new InvalidException(ex.getMessage());
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        final JwtUserDetails userDetails = customUserDetailsService
                .loadUserByUsername(dto.getUsername());
        final TokenDetails result = jwtTokenUtils.getTokenDetails(userDetails, null);
        log.info(String.format("User %s login at %s", dto.getUsername(), new Date()));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> sayHello(Principal principal) {
        return new ResponseEntity<>(String.format("Hello %s", principal.getName()), HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<TaiKhoan> signup(@Valid @RequestBody RegisterDto registerDto){
        return new ResponseEntity<>(userService.signup(registerDto), HttpStatus.OK);
    }

}