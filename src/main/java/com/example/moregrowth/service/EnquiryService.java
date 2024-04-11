package com.example.moregrowth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moregrowth.mapper.EnquiryRepository;
import com.example.moregrowth.model.Enquiry;
@Service
public class EnquiryService {

    @Autowired
    private static EnquiryRepository enquiryRepository;

    public EnquiryService(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    public List<Enquiry> getAllEnquiries() {
        return enquiryRepository.findAll();
    }

    public static void deleteById(String id) {
        enquiryRepository.deleteById(id);
        System.out.println("Delete Sucess");
    }

    public static Enquiry save(Enquiry enquiry) { 
        if (enquiry.getId() != null && enquiryRepository.existsById(enquiry.getId())) {
            // 如果Enquiry存在，更新它
            return enquiryRepository.save(enquiry);
        } else {
            // 如果Enquiry不存在，可以选择抛出异常或保存新的Enquiry
            throw new RuntimeException("Enquiry not found with id " + enquiry.getId());
            // 或者可以选择创建一个新的Enquiry
            // return enquiryRepository.save(new Enquiry(...));
            }
    }

    public Optional<Enquiry> findById(String id) {        
        return enquiryRepository.findById(id);
    }

    public List<Enquiry> findByName(String name) {
        return enquiryRepository.findByName(name);
    }

    public List<Enquiry> findByPhoneNumber(String phoneNumber) {
        return enquiryRepository.findByPhoneNumber(phoneNumber);
    }

    

}
