package servingwebcontent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import servingwebcontent.domain.Question;
import servingwebcontent.domain.Test;
import servingwebcontent.domain.User;
import servingwebcontent.repository.QuestionRepo;
import servingwebcontent.repository.TestRepo;

import java.util.List;

@Controller
@RequestMapping("/testConstructor")
@PreAuthorize("hasAuthority('ADMIN')")
public class TestController {
    @Autowired
    private TestRepo testRepo;

    @Autowired
    private QuestionRepo questionRepo;

    @GetMapping("{test}")
    public String test(@PathVariable Test test, Model model){
        Iterable<Question> questions;
        List<Question> tempQuestions = questionRepo.findByTest(test);

        if (tempQuestions != null && !tempQuestions.isEmpty())
            questions = tempQuestions;
        else
            questions = null;

        model.addAttribute("questions", questions);
        model.addAttribute("test", test);
        return "/testConstructor";
    }

    @GetMapping()
    public String testConstructor(@RequestParam("testId") Test test,
                                  Model model){
        Iterable<Question> questions;
        List<Question> tempQuestions = questionRepo.findByTest(test);

        if (tempQuestions != null && !tempQuestions.isEmpty())
            questions = tempQuestions;
        else
            questions = null;

        model.addAttribute("questions", questions);
        model.addAttribute("test", test);

        return "/testConstructor";
    }

    @PostMapping
    public String saveQuestions(@RequestParam(required = false, defaultValue = "") String questionText,
                                @RequestParam("testId") Test test,
                                Model model
    ) {
        Question question = new Question(test, questionText);

        questionRepo.save(question);

        /*model.addAttribute("questions", questionRepo.findAll());
        model.addAttribute("test", test);*/

        return "redirect:/main";
    }
}
