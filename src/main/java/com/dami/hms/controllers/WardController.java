package com.dami.hms.controllers;

import com.dami.hms.entities.RoomDetail;
import com.dami.hms.entities.WardDetail;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.RoomService;
import com.dami.hms.services.WardDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/wardDetail")
public class WardController {
    @Autowired
    private WardDetailService wardDetailService;

    private List<Column> getWardDetailColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("wardId", "Ward ID"));
        columns.add(new Column("wardName", "Ward Name"));
        columns.add(new Column("wardRate", "Ward Rate"));
        columns.add(new Column("wardDesc", "Description"));
        return columns;
    }

    private List<Field> getWardDetailFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("wardId", "Ward ID", "text", true));
        fields.add(new Field("wardName", "Ward Name", "text", true));
        fields.add(new Field("wardRate", "Ward Rate", "number", true));
        fields.add(new Field("wardDesc", "Description", "textarea", false));
        return fields;
    }

    ///  warddetailsfields

    @GetMapping
    public String listWardDetails(Model model) {
        List<WardDetail> wardDetails = wardDetailService.getWarmDetails();
        model.addAttribute("title", "Ward Details");
        model.addAttribute("items", wardDetails);
        model.addAttribute("columns", getWardDetailColumns());
        model.addAttribute("entityPath", "wardDetail");
        return "wardDetail/index";
    }

    @GetMapping("/new")
    public String newWardDetail(Model model) {
        String newWardId = wardDetailService.generateNextWardId();
        model.addAttribute("title", "New Ward Detail");
        model.addAttribute("entityPath", "wardDetail");
        model.addAttribute("item", new WardDetail());
        model.addAttribute("fields", getWardDetailFields());
        model.addAttribute("idValue", newWardId);
        return "wardDetail/new";
    }


    @PostMapping("/save")
    public String saveWardDetails(@ModelAttribute WardDetail wardDetail) {
        wardDetailService.saveWardDetail(wardDetail);
        return "redirect:/wardDetail";
    }

    @GetMapping("/back")
    public String back() {
        return "menu";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/wardDetail";
    }

    @GetMapping("/delete/{id}")
    public String deleteWardDetails(@PathVariable String id) {
        wardDetailService.deleteWardDetail(id);
        return "redirect:/wardDetail";
    }

//    @GetMapping("/search")
//    public String searchWardDetails(@RequestParam("q") String query, @RequestParam(value = "searchColumn", required = false) String searchColumn, Model model) {
//        List<WardDetail> wardDetails;
//        if (searchColumn == null || searchColumn.isEmpty()) {
//            wardDetails = wardDetailService.searchWardDetailByColumn(query, "roomId");
//        } else {
//            wardDetails = wardDetailService.searchWardDetailByColumn(query, searchColumn);
//        }
//
//        model.addAttribute("title", "Ward Details");
//        model.addAttribute("items", wardDetails);
//        model.addAttribute("columns", getWardDetailColumns());
//        model.addAttribute("entityPath", "wardDetail");
//
//        return "wardDetail/index";
//    }


    @GetMapping("/edit/{id}")
    public String editWardDetail(@PathVariable String id, Model model) {
        WardDetail wardDetail = wardDetailService.getWardDetailById(id);
        List<Field> fields = getWardDetailFields();
        model.addAttribute("title", "Ward Details");
        model.addAttribute("entityPath", "wardDetail");
        model.addAttribute("fields", fields);
        model.addAttribute("idValue", id);
        model.addAttribute("item", wardDetail);
        return "wardDetail/edit";
    }


    @GetMapping("/back/new")
    public String backNew(Model model) {
        List<WardDetail> wardDetails = wardDetailService.getWarmDetails();
        model.addAttribute("title", "Ward Details");
        model.addAttribute("items", wardDetails);
        model.addAttribute("columns", getWardDetailColumns());
        model.addAttribute("entityPath", "wardDetail");
        return "wardDetail/index";
    }


    @GetMapping("/search")
    public String searchWardDetails(@RequestParam("q") String query, @RequestParam(value = "searchColumn", required = false) String searchColumn, Model model) {
        List<WardDetail> wardDetails;
        if (searchColumn == null || searchColumn.isEmpty()) {
            wardDetails = wardDetailService.searchWardDetailByColumn(query, "roomId");
        } else {
            wardDetails = wardDetailService.searchWardDetailByColumn(query, searchColumn);
        }

        model.addAttribute("title", "Ward Details");
        model.addAttribute("items", wardDetails);
        model.addAttribute("columns", getWardDetailColumns());
        model.addAttribute("entityPath", "wardDetail");

        return "wardDetail/index";
    }

}
