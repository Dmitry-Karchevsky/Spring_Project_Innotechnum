package servingwebcontent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import servingwebcontent.domain.Role;
import servingwebcontent.domain.User;
import servingwebcontent.repository.UserRepo;

import java.time.LocalDate;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          @RequestParam(required = false, defaultValue = "") String firstName,
                          @RequestParam(required = false, defaultValue = "") String lastName,
                          @RequestParam(required = false, defaultValue = "") String organization,
                          @RequestParam(required = false, defaultValue = "") String email,
                          Model model
    ){
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null){
            model.addAttribute("message", "User exists!");
            return "registration";
        }

        user.setActive(true);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setOrganization(organization);
        user.setEmail(email);
        user.setRegdate(LocalDate.now());
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}