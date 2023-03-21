package com.smsolutions.smartflex.service;


import com.smsolutions.smartflex.dto.ContactDto;

import java.util.List;

public interface ContactService {
    List<ContactDto> getContacts();
    ContactDto getContactById(Integer contactId);
    ContactDto createContact(ContactDto contactDto);
    ContactDto updateContact(ContactDto contact);
    void deleteContact(Long contactId);
}
