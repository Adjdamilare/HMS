package com.dami.hms.controllers;

import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.RoomType;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roomType")
public class RoomTypeController {
    @Autowired
    private RoomService roomService;

    private List<Column> getRoomTypeColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("roomType", "Room Type"));
        columns.add(new Column("roomRates", "Room Rate"));
        columns.add(new Column("notes", "Notes"));
        return columns;
    }

    private List<Field> getRoomTypeFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("roomType", "Room Type", "text", true));
        fields.add(new Field("roomRates", "Room Rate", "number", true));
        fields.add(new Field("notes", "Notes", "text", true));
        return fields;
    }

    @GetMapping
    public String listRoomTypes(Model model) {
        List<RoomType> roomTypes = roomService.getRoomTypes();

        model.addAttribute("title", "Room Types");
        model.addAttribute("items", roomTypes);
        model.addAttribute("columns", getRoomTypeColumns());
        model.addAttribute("entityPath", "roomType");

        return "roomTypes/index";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/roomType";
    }

    @GetMapping("/back")
    public String back() {
        return "menu";
    }

    @GetMapping("/new")
    public String newRoomTypeSchedule(Model model) {
        model.addAttribute("title", "New Room Type");
        model.addAttribute("entityPath", "roomType");
        model.addAttribute("room", new RoomType()); // Add an empty schedule object
        return "roomTypes/new";
    }


    @PostMapping("/save")
    public String saveRoomTypes(@ModelAttribute RoomType roomType) {
        roomService.saveRoomType(roomType);
        return "redirect:/roomType";
    }


    @GetMapping("/edit/{id}")
    public String editRoomType(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
            RoomType roomType = roomService.getRoomType(id);
            model.addAttribute("title", "Edit Room Type");
            model.addAttribute("entityPath", "roomType");
            model.addAttribute("roomType", roomType);
            return "roomTypes/edit";
    }

    @PostMapping("/update")
    public String updateRoomType(@ModelAttribute RoomType roomType, RedirectAttributes redirectAttributes) {
        try {
            roomService.saveRoomType(roomType);
            redirectAttributes.addFlashAttribute("successMessage", "Room Type updated successfully");
            return "redirect:/roomType";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update Room Type");
            return "redirect:/roomType";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteRoomDetails(@PathVariable String id) {
        roomService.deleteRoomType(id);
        return "redirect:/roomType";
    }



//
@GetMapping("/search")
public String searchRoomTypes(@RequestParam("q") String query, @RequestParam(value = "searchColumn", required = false) String searchColumn, Model model) {
    List<RoomType> roomTypes;
    if (searchColumn == null || searchColumn.isEmpty()) {
        roomTypes = roomService.searchRoomTypeByColumn(query, "roomId");
    } else {
        roomTypes = roomService.searchRoomTypeByColumn(query, searchColumn);
    }

    model.addAttribute("title", "Room Type");
    model.addAttribute("items", roomTypes);
    model.addAttribute("columns", getRoomTypeColumns());
    model.addAttribute("entityPath", "roomType");

    return "roomTypes/index";
}
}
