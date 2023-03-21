package com.smsolutions.smartflex.service;

import com.smsolutions.smartflex.dto.ContactDto;
import com.smsolutions.smartflex.entity.ContactEntity;
import com.smsolutions.smartflex.repository.ContactRepository;
import com.smsolutions.smartflex.utils.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;


    @Override
    public ContactDto createContact(ContactDto contactDto) {
        ContactEntity contact = ContactMapper.mapToContactEntity(contactDto);
        ContactEntity savedContact = contactRepository.save(contact);
        ContactDto savedContactDto = ContactMapper.mapToContactDto(savedContact);
        return savedContactDto;
    }
}
