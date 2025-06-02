package com.dami.hms.controllers;

import com.dami.hms.entities.Services;
import com.dami.hms.models.Column;
import com.dami.hms.models.Field;
import com.dami.hms.services.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServicesService servicesService;

    private List<Column> getServiceColumns() {
        List<Column> columns = new ArrayList<>();
        columns.add(new Column("channelServiceId", "Service ID"));
        columns.add(new Column("channelService", "Service Name"));
        columns.add(new Column("durationOfService", "Duration of Service(mins)"));
        columns.add(new Column("chargeForService", "Service Charge"));
        columns.add(new Column("serviceNotes", "Service Notes"));
        return columns;
    }

    private List<Field> getServiceFields() {
        List<Field> fields = new ArrayList<>();
        fields.add(new Field("channelServiceId", "Service ID", "text", true));
        fields.add(new Field("channelService", "Service Name", "text", true));
        fields.add(new Field("durationOfService", "Duration Of Service(mins)", "number", true));
        fields.add(new Field("chargeForService", "Service Charge", "text", false));
        fields.add(new Field("serviceNotes", "Notes", "text", false));
        return fields;
    }

    @GetMapping
    public String listServices(Model model) {
        List<Services> services = servicesService.getAllServices();
        model.addAttribute("title", "Service Details");
        model.addAttribute("items", services);
        model.addAttribute("columns", getServiceColumns());
        model.addAttribute("entityPath", "service");
        return "service/index";
    }

    @GetMapping("/refresh")
    public String refresh(){
        return "redirect:/service";
    }

    @GetMapping("/new")
    public String newService(Model model) {
        String serviceId = servicesService.generateNextServiceId();
        model.addAttribute("title", "Service");
        model.addAttribute("entityPath", "service");
        model.addAttribute("fields", getServiceFields());
        model.addAttribute("idValue", serviceId);
        return "service/new";
    }

    @GetMapping("/search")
    public String searchServices(@RequestParam("q") String query, @RequestParam(value = "searchColumn", required = false) String searchColumn, Model model) {
        List<Services> services;
        if (searchColumn == null || searchColumn.isEmpty()) {
            services = servicesService.searchServices(query);
        } else {
            services = servicesService.searchServicesByColumn(query, searchColumn);
        }

        model.addAttribute("title", "Service Details");
        model.addAttribute("items", services);
        model.addAttribute("columns", getServiceColumns());
        model.addAttribute("entityPath", "service");

        return "service/index";
    }

    @GetMapping("/back")
    public String back() {
        return "menu";
    }

    @GetMapping("/back/new")
    public String backNew(Model model) {
        List<Services> services = servicesService.getAllServices();

        model.addAttribute("title", "Service Details");
        model.addAttribute("items", services);
        model.addAttribute("columns", getServiceColumns());
        model.addAttribute("entityPath", "service");
        return "service/index";
    }

    @PostMapping("/save")
    public String saveService(@ModelAttribute Services service, @RequestParam("channelServiceId") String serviceId) {
        service.setChannelServiceId(serviceId);
        servicesService.saveService(service);
        return "redirect:/service";
    }

    @GetMapping("/edit/{id}")
    public String editService(@PathVariable String id, Model model) {
        Services service = servicesService.getServiceById(id);
        List<Field> fields = getServiceFields();
        model.addAttribute("title", "Service");
        model.addAttribute("entityPath", "service");
        model.addAttribute("fields", fields);
        model.addAttribute("idValue", id);
        model.addAttribute("item", service);
        return "service/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteService(@PathVariable String id) {
        servicesService.delete(id);
        return "redirect:/service";
    }
}