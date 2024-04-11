package com.example.moregrowth.mapper;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.moregrowth.model.Enquiry;


@Repository
public interface EnquiryRepository extends MongoRepository<Enquiry, String> {
    // Seems basic methods are all included in the MongoDB original library
    @Query
    public List<Enquiry> findByName(String name);

    @Query
    public List<Enquiry> findByPhoneNumber(String phoneNumber);
}
