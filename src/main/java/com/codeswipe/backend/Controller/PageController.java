package com.codeswipe.backend.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String landingPage() {
        return "forward:/landingPage.html";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "forward:/loginPage.html";
    }

    @GetMapping("/home")
    public String homePage() {
        return "forward:/homePage.html";
    }

    @GetMapping("/project")
    public String myProjects() {
        return "forward:/myProjects.html";
    }

    @GetMapping("/project/new")
    public String newProject() {
        return "forward:/newProject.html";
    }

    @GetMapping("/project/edit")
    public String editProject() {
        return "forward:/editProject.html";
    }

    @GetMapping("/project/interested")
    public String interested() {
        return "forward:/interested.html";
    }

    @GetMapping("/profile")
    public String profile() {
        return "forward:/profile.html";
    }
}

