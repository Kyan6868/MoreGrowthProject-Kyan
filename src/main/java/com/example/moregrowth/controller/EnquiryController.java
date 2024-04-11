package com.example.moregrowth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.moregrowth.model.Enquiry;
import com.example.moregrowth.service.EnquiryService;



@RestController
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    //view all the enquiries
    @GetMapping("/all")
    public List<Enquiry> getAll() {
        return enquiryService.getAllEnquiries();
    }

    //search specific enquiry by using ID
    @GetMapping("/search/id/{id}")
    public Optional<Enquiry> getEnquiryById(@PathVariable String id) {
        return enquiryService.findById(id);
    }
    //search specific enquiry by using name
    @GetMapping("/search/name/{name}")
    public List<Enquiry> getEnquiryByName(@PathVariable String name) {
        return enquiryService.findByName(name);
    }

    //search specific enquiry by using name
    @GetMapping("/search/phoneNumber/{phoneNumber}")
    public List<Enquiry> getEnquiryByPhoneNumber(@PathVariable String phoneNumber) {
        return enquiryService.findByPhoneNumber(phoneNumber);
    }
    


    //update
    @PutMapping("/enquiries/{id}")
    public Enquiry updateEnquiry(@PathVariable String id, @RequestBody Enquiry enquiry) {
        enquiry.setId(id);
        return EnquiryService.save(enquiry);
    }
    
    //delete specific enquiry
    @DeleteMapping("delete/{id}")
    public void deleteEnquiry(@PathVariable String id) {
        EnquiryService.deleteById(id);
    }

    
    


    
}
