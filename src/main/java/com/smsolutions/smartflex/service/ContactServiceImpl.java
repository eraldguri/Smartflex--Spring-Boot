package com.smsolutions.smartflex.service;

import com.smsolutions.smartflex.dto.ContactDto;
import com.smsolutions.smartflex.entity.ContactEntity;
import com.smsolutions.smartflex.repository.ContactRepository;
import com.smsolutions.smartflex.utils.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;


    @Override
    public List<ContactDto> getContacts() {
        List<ContactEntity> contacts = contactRepository.findAll();
        return contacts.stream().map(ContactMapper::mapToContactDto).collect(Collectors.toList());
    }

    @Override
    public ContactDto getContactById(Integer contactId) {
        Optional<ContactEntity> optionalContact = contactRepository.findById(Long.valueOf(contactId));
        ContactEntity contact = optionalContact.get();
        return ContactMapper.mapToContactDto(contact);
    }

    @Override
    public ContactDto createContact(ContactDto contactDto) {
        ContactEntity contact = ContactMapper.mapToContactEntity(contactDto);
        ContactEntity savedContact = contactRepository.save(contact);
        ContactDto savedContactDto = ContactMapper.mapToContactDto(savedContact);
        return savedContactDto;
    }

    @Override
    public ContactDto updateContact(ContactDto contact) {
        ContactEntity existingContact = contactRepository.findById(Long.valueOf(contact.getId())).get();
        existingContact.setFirstName(contact.getFirstName());
        existingContact.setLastName(contact.getLastName());
        existingContact.setBirthDate(contact.getBirthDate());
        existingContact.setPhone(contact.getPhone());
        existingContact.setOtherPhone(contact.getOtherPhone());
        existingContact.setMobile(contact.getMobile());
        existingContact.setSecondaryEmail(contact.getSecondaryEmail());
        existingContact.setStreet(contact.getOtherStreet());
        existingContact.setCity(contact.getCity());
        existingContact.setCountry(contact.getCountry());
        existingContact.setZipCode(contact.getZipCode());
        existingContact.setState(contact.getState());
        existingContact.setOtherStreet(contact.getOtherStreet());
        existingContact.setOtherCity(contact.getOtherCity());
        existingContact.setOtherCountry(contact.getOtherCountry());
        existingContact.setOtherZipCode(contact.getOtherZipCode());
        existingContact.setOtherState(contact.getOtherState());
        existingContact.setDescription(contact.getDescription());

        ContactEntity updatedContact = contactRepository.save(existingContact);
        return ContactMapper.mapToContactDto(updatedContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        contactRepository.deleteById(contactId);
    }
}
