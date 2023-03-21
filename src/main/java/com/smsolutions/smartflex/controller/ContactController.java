package com.smsolutions.smartflex.controller;

import com.smsolutions.smartflex.dto.ContactDto;
import com.smsolutions.smartflex.entity.ContactEntity;
import com.smsolutions.smartflex.repository.ContactRepository;
import com.smsolutions.smartflex.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("contacts")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contact) {
        ContactDto savedContact = contactService.createContact(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

}
