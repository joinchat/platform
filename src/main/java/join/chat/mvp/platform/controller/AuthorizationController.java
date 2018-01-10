package join.chat.mvp.platform.controller;

import io.jsonwebtoken.Jwts;
import join.chat.mvp.platform.component.JWTTokenFactory;
import join.chat.mvp.platform.configuration.JWTConfiguration;
import join.chat.mvp.platform.essential.GuidEntity;
import join.chat.mvp.platform.essential.RegistrationEntity;
import join.chat.mvp.platform.essential.ValidationEntity;
import join.chat.mvp.platform.model.Account;
import join.chat.mvp.platform.model.Validation;
import join.chat.mvp.platform.service.AccountService;
import join.chat.mvp.platform.service.VerificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("/v1/authorization")
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    private final JWTTokenFactory jwtTokenFactory;
    private final JWTConfiguration jwtConfiguration;
    private final AccountService accountService;
    private final VerificationService verificationService;

    @Autowired
    public AuthorizationController(final JWTTokenFactory jwtTokenFactory,
                                   final JWTConfiguration jwtConfiguration,
                                   final AccountService accountService,
                                   final VerificationService verificationService) {
        this.jwtTokenFactory = jwtTokenFactory;
        this.jwtConfiguration = jwtConfiguration;
        this.accountService = accountService;
        this.verificationService = verificationService;
    }

    @GetMapping("/get-code")
    public ResponseEntity<?> getVerificationCode(@RequestParam("phone") String phone,
                                                 @RequestHeader("Accept-Language") Locale locale) throws InterruptedException {
        if (StringUtils.isEmpty(phone)) {
            // TODO: Add validation according to the format E.164
            logger.info("Can't send validation code, phone number is wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        final Optional<Account> account = accountService.findByPhone(phone);
        if (account.isPresent()) {
            logger.info("Can't send validation code, phone number already registered");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        verificationService.pushVerificationCode(phone, locale);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/verification")
    public ResponseEntity<GuidEntity> checkVerificationCode(@Valid @RequestBody ValidationEntity essential) {
        final Validation validation = verificationService.verifyVerificationCode(essential.getCode(),
                essential.getPhone());
        if (validation == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new GuidEntity(validation.getGuid()));
    }

    @PostMapping("/sign-up/{guid}")
    public ResponseEntity<?> signUp(@PathVariable String guid,
                                    @Valid @RequestBody RegistrationEntity essential) {

        final Optional<Validation> validation = verificationService.getByGuid(guid);
        if (!validation.isPresent()) {
            logger.info("Can't SignUp, GUID is wrong");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        final String phone = validation.get().getNumber();
        final Optional<Account> account = accountService.findByPhone(phone);
        if (account.isPresent()) {
            logger.info("Can't send validation code, phone number already registered");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        accountService.signUp(phone, essential);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final Account account = getSubjectAccount(request);

        // TODO: add support account authorities
        final User user = new User(account.getUsername(), account.getPassword(),
                emptyList());

        response.addHeader(JWTConfiguration.JWT_HEADER_STRING,
                JWTConfiguration.JWT_TOKEN_PREFIX + " " + jwtTokenFactory.getToken(user));
        response.setStatus(HttpStatus.OK.value());
    }

    private Account getSubjectAccount(HttpServletRequest request) {
        final String token = request.getHeader(JWTConfiguration.JWT_HEADER_STRING);

        if (token == null || StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("The \"" + JWTConfiguration.JWT_HEADER_STRING + "\" header not found");
        }
        // parse the token.
        final String subject = Jwts.parser()
                .setSigningKey(jwtConfiguration.getTokenSigningKey())
                .parseClaimsJws(token.replace(JWTConfiguration.JWT_TOKEN_PREFIX + " ", ""))
                .getBody().getSubject();
        Optional<Account> account = accountService.findByUsername(subject);
        if (!account.isPresent()) throw new UsernameNotFoundException(subject);

        return account.get();
    }
}
