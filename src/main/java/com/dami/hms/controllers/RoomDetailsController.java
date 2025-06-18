package com.dami.hms.controllers;

import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.RoomType;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.DoctorService;
import com.dami.hms.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/roomDetails")
public class RoomDetailsController {
    @Autowired
    private RoomService roomService;

    private List<Column> getRoomDetailsColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("roomId", "Room ID"));
        columns.add(new Column("roomType", "Room Type"));
        columns.add(new Column("roomDescription", "Room Description"));
        return columns;
    }

    private List<Field> getRoomDetailsFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("roomId", "Room ID", "text", true));
        fields.add(new Field("roomType", "Room Type", "text", true));
        fields.add(new Field("roomDescription", "Room Description", "text", true));
        return fields;
    }

    @GetMapping
    public String listRoomDetails(Model model) {
        List<RoomDetail> roomDetails = roomService.getRoomDetails();

        model.addAttribute("title", "Room Details");
        model.addAttribute("items", roomDetails);
        model.addAttribute("columns", getRoomDetailsColumns());
        model.addAttribute("entityPath", "roomDetails");

        return "roomDetails/index";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/roomDetails";
    }

    @GetMapping("/new")
    public String newRoom(Model model) {
        model.addAttribute("idValue", roomService.generateNextRoomId());
        model.addAttribute("roomTypes", roomService.getRoomTypes());
        return "roomDetails/new";
    }



    @GetMapping("/edit/{id}")
    public String editRoomDetails(@PathVariable String id, Model model) {
        RoomDetail roomDetail = roomService.getRoomDetailById(id);

        if (roomDetail == null) {
            return "redirect:/roomDetails";
        }

        List<RoomType> roomTypes = roomService.getRoomTypes(); // Load available room types

        model.addAttribute("title", "Edit Room Details");
        model.addAttribute("entityPath", "roomDetails");
        model.addAttribute("item", roomDetail);
        model.addAttribute("roomTypes", roomTypes); // Pass room types to template
        model.addAttribute("idValue", id);

        return "roomDetails/edit";
    }


    @PostMapping("/update")
    public String updateRoomDetails(@RequestParam String roomId,
                                    @RequestParam String roomType,
                                    @RequestParam String roomDescription,
                                    RedirectAttributes redirectAttributes) {
        try {
            RoomDetail existingRoom = roomService.getRoomDetailById(roomId);
            existingRoom.setRoomType(roomType);
            existingRoom.setRoomDescription(roomDescription);

            roomService.saveRoomDetail(existingRoom);

            redirectAttributes.addFlashAttribute("successMessage", "Room details updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update room details.");
            return "redirect:/roomDetails/edit/" + roomId;
        }

        return "redirect:/roomDetails";
    }


    @PostMapping("/save")
    public String saveRoomDetails(@ModelAttribute RoomDetail roomDetail) {
        roomService.saveRoomDetail(roomDetail);
        return "redirect:/roomDetails";
    }




    @GetMapping("/back")
    public String back() {
        return "menu";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoomDetails(@PathVariable String id) {
        roomService.deleteRoomDetail(id);
        return "redirect:/roomDetails";
    }

    @GetMapping("/back/new")
    public String backNew(Model model) {
        List<RoomDetail> roomDetails = roomService.getRoomDetails();
        model.addAttribute("title", "Room Details");
        model.addAttribute("items", roomDetails);
        model.addAttribute("columns", getRoomDetailsColumns());
        model.addAttribute("entityPath", "roomDetails");
        return "roomDetails/index";
    }

    @GetMapping("/search")
    public String searchRoomDetails(@RequestParam("q") String query, @RequestParam(value = "searchColumn", required = false) String searchColumn, Model model) {
        List<RoomDetail> roomDetails;
        if (searchColumn == null || searchColumn.isEmpty()) {
            roomDetails = roomService.searchRoomDetailByColumn(query, "roomId");
        } else {
            roomDetails = roomService.searchRoomDetailByColumn(query, searchColumn);
        }

        model.addAttribute("title", "Room Details");
        model.addAttribute("items", roomDetails);
        model.addAttribute("columns", getRoomDetailsColumns());
        model.addAttribute("entityPath", "roomDetails");

        return "roomDetails/index";
    }

}
