package com.packtpub.dynamodb.cookbook.addressbook.services;

import java.util.List;

import com.packtpub.dynamodb.cookbook.addressbook.domain.Contact;

public class AddressBookServiceTest {

	public static void main(String args[]) {
		AddressBookService addressBookService = new AddressBookServiceImpl();
		List<Contact> contacts = addressBookService
				.getAllContacts("tanmay@user.com");
		for (Contact contact : contacts) {
			System.out.println(contact.getFname());
		}
	}
}
