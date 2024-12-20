package me.tofaa.cdn.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebUiController {

    @GetMapping("/uploader")
    public String uploaderUi() {
        return "uploader";
    }

}
