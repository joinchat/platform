package join.chat.mvp.platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authorization")
public class AuthorizationController {

    @GetMapping("/sign-up")
    public String signUp() {
        return "Hello world !!!";
    }
}
