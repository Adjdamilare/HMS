package com.dami.hms.controllers;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/global")
public class GlobalController {

    @GetMapping
    public String global() {
        return "global/index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("selectedOption") String selectedOption) {

        switch (selectedOption) {
            case "doctor":
                return "redirect:/doctor";
            case "room":
                return "redirect:/room";
            case "service":
                return "redirect:/service";
            case "roomType":
                return "redirect:/roomType";
            case "doctorSchedule":
                return "redirect:/doctorSchedule";
            case "serviceSchedule":
                return "redirect:/serviceSchedule";
            default:
                return "global/index";
        }
    }

}
