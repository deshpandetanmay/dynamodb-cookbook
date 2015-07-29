package com.packtpub.dynamodb.cookbook.addressbook.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.packtpub.dynamodb.cookbook.addressbook.domain.Contact;

@Service
public interface AddressBookService {

	void addContact(Contact contact);

	List<Contact> getAllContacts(String userEmailId);

}
