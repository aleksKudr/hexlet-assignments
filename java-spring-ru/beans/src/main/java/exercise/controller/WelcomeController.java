package exercise.controller;

import exercise.Application;
import exercise.daytime.Daytime;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @GetMapping
    public String index() {
        Daytime daytime = new Application().getDaytaime();
        return String.format("it is %s now! Welcome to Spring!", daytime.getName());
    }
}
// END
