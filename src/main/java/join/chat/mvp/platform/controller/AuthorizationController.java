package join.chat.mvp.platform.controller;

import join.chat.mvp.platform.essential.RegistrationEntity;
import join.chat.mvp.platform.service.AccountService;
import join.chat.mvp.platform.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/v1/authorization")
public class AuthorizationController {
    private final AccountService accountService;
    private final VerificationService verificationService;

    @Autowired
    public AuthorizationController(final AccountService accountService,
                                   final VerificationService verificationService) {
        this.accountService = accountService;
        this.verificationService = verificationService;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody RegistrationEntity essential) {
        accountService.signUp(essential);
    }

    @GetMapping("/get-code")
    public String getVerificationCode(@RequestParam("phone") String phone,
                                      @RequestHeader("Accept-Language") Locale locale) {
        if (StringUtils.isEmpty(phone)) {
            throw new IllegalArgumentException("Can't send validation code, phone number is wrong");
        }
        //return verificationService.pushVerificationCode(phone, locale);
        return null;
    }
}
