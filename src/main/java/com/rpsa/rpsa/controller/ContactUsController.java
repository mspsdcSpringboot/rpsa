package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.Contacts;
import com.rpsa.rpsa.service.LoadHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/public")
public class ContactUsController {

    @Autowired
    private LoadHomeService loadHomeService;

    @GetMapping("/contact_us")
    public String getContact(Model model) {
        List<Contacts> contacts = loadHomeService.getContacts();
        model.addAttribute("contacts", contacts);
        return "/pages/contact_us/contact_us";
    }
}
