package com.smsolutions.smartflex.controller;

import com.smsolutions.smartflex.dto.ContactDto;
import com.smsolutions.smartflex.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("create-contact")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contact) {
        ContactDto savedContact = contactService.createContact(contact);
        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }

    @GetMapping("contacts")
    public ResponseEntity<List<ContactDto>> getContacts() {
        List<ContactDto> contacts = contactService.getContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<ContactDto> getContactById(@PathVariable Integer contactId) {
        ContactDto contact = contactService.getContactById(contactId);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ContactDto> updateContact(@PathVariable("id") Integer contactId, @RequestBody ContactDto contact) {
        contact.setId(contactId);
        ContactDto updatedContact = contactService.updateContact(contact);
        return new ResponseEntity<>(updatedContact, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") Long contactId) {
        contactService.deleteContact(contactId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}
