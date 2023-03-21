package com.smsolutions.smartflex.utils;

import com.smsolutions.smartflex.dto.ContactDto;
import com.smsolutions.smartflex.entity.ContactEntity;

public class ContactMapper {

    public static ContactDto mapToContactDto(ContactEntity contact) {
        ContactDto contactDto = new ContactDto();
        contactDto.setId(contact.getId());
        contactDto.setFirstName(contact.getFirstName());
        contactDto.setLastName(contact.getLastName());
        contactDto.setBirthDate(contact.getBirthDate());
        contactDto.setPhone(contact.getPhone());
        contactDto.setOtherPhone(contact.getOtherPhone());
        contactDto.setMobile(contact.getMobile());
        contactDto.setSecondaryEmail(contact.getSecondaryEmail());
        contactDto.setStreet(contact.getStreet());
        contactDto.setCity(contact.getCity());
        contactDto.setCountry(contact.getCountry());
        contactDto.setZipCode(contact.getZipCode());
        contactDto.setState(contact.getState());
        contactDto.setOtherState(contact.getOtherState());
        contactDto.setOtherCity(contact.getOtherCity());
        contactDto.setOtherCountry(contact.getOtherCountry());
        contactDto.setOtherZipCode(contact.getOtherZipCode());
        contactDto.setOtherState(contact.getOtherState());
        contactDto.setDescription(contact.getDescription());
        contactDto.setCreatedAt(contact.getCreatedAt());
        contactDto.setUpdatedAt(contact.getUpdatedAt());
        return contactDto;
    }

    public static ContactEntity mapToContactEntity(ContactDto contactDto) {
        ContactEntity contact = new ContactEntity();
        contact.setId(contactDto.getId());
        contact.setFirstName(contactDto.getFirstName());
        contact.setLastName(contactDto.getLastName());
        contact.setBirthDate(contactDto.getBirthDate());
        contact.setPhone(contactDto.getPhone());
        contact.setOtherPhone(contactDto.getOtherPhone());
        contact.setMobile(contactDto.getMobile());
        contact.setSecondaryEmail(contactDto.getSecondaryEmail());
        contact.setStreet(contactDto.getStreet());
        contact.setCity(contactDto.getCity());
        contact.setCountry(contactDto.getCountry());
        contact.setZipCode(contactDto.getZipCode());
        contact.setState(contactDto.getState());
        contact.setOtherState(contactDto.getOtherState());
        contact.setOtherCity(contactDto.getOtherCity());
        contact.setOtherCountry(contactDto.getOtherCountry());
        contact.setOtherZipCode(contactDto.getOtherZipCode());
        contact.setOtherState(contactDto.getOtherState());
        contact.setDescription(contactDto.getDescription());
        contact.setCreatedAt(contactDto.getCreatedAt());
        contact.setUpdatedAt(contactDto.getUpdatedAt());
        return contact;
    }
}
