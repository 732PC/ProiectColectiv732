package org.example;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;


@Controller
public class DemoOverview {

    @GetMapping("/")
    public String overview(Model model) {
        return "sign-in";
    }
    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model){
        boolean isValidCookie = isValid(request);
        if(isValidCookie==true)
            return "home";
        else
            return "sign-in";
    }

    private boolean isValid(HttpServletRequest req){
        var xd = req.getCookies();
        if(xd != null ){
            for(var i : xd){
                if(Objects.equals(i.getName(), "myToken") && Objects.equals(i.getValue(), "audbsadus332432")){
                    return true;
                }
            }
            return false;
        }
        return false;

    }

}
