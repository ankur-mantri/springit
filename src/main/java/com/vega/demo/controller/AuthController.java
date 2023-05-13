package com.vega.demo.controller;

import com.vega.demo.domain.UserSpringIt;
import com.vega.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "auth/profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new UserSpringIt());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid UserSpringIt user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            // The appliction is not catching binding errors. This needs to be further analyzed
             logger.info("There were errors in registration");
             model.addAttribute("user", user);
             model.addAttribute("validationErrors",bindingResult.getAllErrors());
             return "auth/register";
        } else {
            UserSpringIt newUser = userService.register(user);
            logger.info("There seems to be NO ERRORS in registration");
            redirectAttributes.addAttribute("id",newUser.getId())
                        .addFlashAttribute("success", true);
            return "redirect:/register";
        }
    }

    @GetMapping("/activate/{email}/{activationCode}")
    public String activate(@PathVariable String email, @PathVariable String activationCode) {
        Optional<UserSpringIt> user = userService.findByEmailAndActivationCode(email,activationCode);
        if(user.isPresent()){
            UserSpringIt newUser = user.get();
            newUser.setEnabled(true);
            newUser.setConfirmPassword(newUser.getPassword()); // this is required to bypass the Validations
            userService.save(newUser);
            userService.sendWelcomeEmail(newUser);
            return "auth/activated";
        }
        return "redirect:/";
    }
}

