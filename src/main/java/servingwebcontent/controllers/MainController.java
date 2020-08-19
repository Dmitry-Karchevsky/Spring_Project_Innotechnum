package servingwebcontent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import servingwebcontent.domain.Role;
import servingwebcontent.domain.Test;
import servingwebcontent.domain.User;
import servingwebcontent.repository.TestRepo;

@Controller
public class MainController {
    @Autowired
    private TestRepo testRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String testfilter,
                       Model model
    ){
        Iterable<Test> tests;

        if (testfilter != null && !testfilter.isEmpty())
            tests = testRepo.findByName(testfilter);
        else
            tests = testRepo.findAll();

        model.addAttribute("tests", tests);
        model.addAttribute("testfilter", testfilter);
        return "main";
    }

    /*@GetMapping("/main/{test}")
    public String userEditForm(@PathVariable Test test, Model model) {
        model.addAttribute("test", test);

        return "testConstructor";
    }*/

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String testName,
            Model model){
        Test test = new Test(testName, user);

        testRepo.save(test);
        Iterable<Test> tests = testRepo.findAll();
        model.addAttribute("tests", tests);
        return "main";
    }
}
