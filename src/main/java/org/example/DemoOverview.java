package org.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DemoOverview {

    @GetMapping("/")
    public String overview(Model model) {
        return "sign-in";
    }
    @GetMapping("/home")
    public String home(Model model){
        return "home";
    }

}
