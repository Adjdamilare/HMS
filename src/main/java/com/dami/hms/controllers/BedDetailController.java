package com.dami.hms.controllers;

import com.dami.hms.entities.BedDetail;
import com.dami.hms.entities.Outpatient;
import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.WardDetail;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.repositories.BedDetailsRepository;
import com.dami.hms.services.RoomService;
import com.dami.hms.services.WardDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bedDetails") // changed to plural
public class BedDetailController {
    @Autowired
    private BedDetailsRepository bedDetailsRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private WardDetailService wardDetailService;

    @GetMapping("/index") // also updated here
    public String listBeds(Model model) {
        model.addAttribute("beds", bedDetailsRepository.findByStatus((byte) 0));
        return "bedDetails/index";
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        // Generate next bed ID
        String newBedId = generateNextBedId();
        System.out.println(newBedId);

        // Get available rooms and wards
        List<RoomDetail> roomDetails = roomService.getRoomDetails(); // Assuming roomService is autowired
        List<WardDetail> wardDetails = wardDetailService.getWarmDetails(); // Assuming wardDetailService is autowired

        BedDetail newBed = new BedDetail();
        newBed.setBedId(newBedId);

        model.addAttribute("bed", newBed);
        model.addAttribute("idValue", newBedId);
        model.addAttribute("roomDetails", roomDetails);
        model.addAttribute("wardDetails", wardDetails);

        return "bedDetails/new";
    }

    // Add this method inside BedDetailController class
    private String generateNextBedId() {
        Optional<BedDetail> lastBedOptional = bedDetailsRepository.findAll(Sort.by(Sort.Direction.DESC, "bedId")).stream().findFirst();

        if (lastBedOptional.isPresent()) {
            String lastBedId = lastBedOptional.get().getBedId();
            int lastNumber = Integer.parseInt(lastBedId.substring(2)); // assuming format like BD001
            return String.format("BD%03d", lastNumber + 1);
        } else {
            return "BD001"; // First bed ID
        }
    }



    @PostMapping("/save")
    public String saveBed(@ModelAttribute BedDetail bed,
                          @RequestParam("roomWard") String roomWard,
                          RedirectAttributes redirectAttributes) {
        try {
            bed.setStatus((byte) 0); // Ensure status remains active
            bed.setRoomWard(roomWard);
            bedDetailsRepository.save(bed);
            redirectAttributes.addFlashAttribute("successMessage", "Bed saved successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save bed.");
            return "redirect:/bedDetails/new";
        }
        return "redirect:/bedDetails/index";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String bedId, Model model) {
        Optional<BedDetail> bedOptional = bedDetailsRepository.findById(bedId);
        if (bedOptional.isEmpty()) {
            throw new IllegalArgumentException("Invalid bed ID: " + bedId);
        }

        BedDetail bed = bedOptional.get();
        List<RoomDetail> roomDetails = roomService.getRoomDetails();
        List<WardDetail> wardDetails = wardDetailService.getWarmDetails();

        model.addAttribute("bed", bed);
        model.addAttribute("roomDetails", roomDetails);
        model.addAttribute("wardDetails", wardDetails);

        return "bedDetails/edit";
    }



}

