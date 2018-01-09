package join.chat.mvp.platform.controller;

import join.chat.mvp.platform.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/v1/authorization")
public class AuthorizationController {
    final VerificationService verificationService;

    @Autowired
    public AuthorizationController(final VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "";
    }

    @GetMapping("/get-code")
    public String getVerificationCode(@RequestParam("phone") String phone,
                                      @RequestHeader("Accept-Language") Locale locale) {
        final Optional<String> number = phone == null ? Optional.empty() : Optional.of(phone);
        return null;
    }
}
