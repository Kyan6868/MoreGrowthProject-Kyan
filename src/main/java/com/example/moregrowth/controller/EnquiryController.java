package com.example.moregrowth.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.moregrowth.model.Enquiry;
import com.example.moregrowth.service.EnquiryService;









@RestController
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;
    

    /*
     * 
     * Search Functions
     */
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

    /*
     * 
     * 
     * data provided functions
     */
    //provide total goodleads 
    @GetMapping("/totalgoodleads/{leads}")
    public long gettotalleads(@PathVariable String leads) {
        return enquiryService.countByTransactionOutcome(leads);
    }
    //provide Open Enquiry in Good leads
    @GetMapping("/Openleads")
    public long getOpenleads() {
        return  enquiryService.countByStatus("Open");
    }
    //provide Closed Enquiry in Good leads
    @GetMapping("/Closedleads")
    public long getClosedleads() {
        return  enquiryService.countByStatus("Closed");
    }
    //provide Lost Enquiry in Good leads
    @GetMapping("/Lostleads")
    public long getLostleads() {
        return  enquiryService.countByStatus("Lost");
    }
    //calc Conversion rate use closed/total
    @GetMapping("/ConversionRate")
    public Double getConversionRate() {
        return (double)enquiryService.countByStatus("Closed")/enquiryService.countByTransactionOutcome("Good");
    }
    //get yesterday Good leads
    @GetMapping("/goodleadseachday")
    public long getGoodleadseachday() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        long result = enquiryService.countByDate(yesterday);
        return result;
    }
    //get yesterday enquiries
    @GetMapping("/yesterdayenquiry")
    public long getyesterdayenquiry() {
        LocalDate today = LocalDate.now();
        //LocalDate today = LocalDate.of(2018,1 , 15);
        LocalDate yesterday = today.minusDays(1);
        long result =enquiryService.countByDate(yesterday);
        return result;
    }
    //percentage of all the contact method
    @GetMapping("/Contactpercentage/{method}")
    public Double getContactpercentage(@PathVariable String method) {
        return (double)enquiryService.countByContactMethod(method)/enquiryService.getTotalEnquiry();
    }


    /*
     * 
     * 
     * List both Good and bad leads
     */
    @PostMapping("/listgood")
    public List<Enquiry> listGood() {
        return enquiryService.findbyTransactionOutcome("Good");
    }

    @PostMapping("/listbad")
    public List<Enquiry> listbad() {
        return enquiryService.findbyTransactionOutcome("Bad");
    }
    

    

    /*
     * 
     * 
     * Management Functions
     */
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
