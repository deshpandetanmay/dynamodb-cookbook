package com.packtpub.dynamodb.cookbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.packtpub.dynamodb.cookbook.addressbook.domain.Contact;
import com.packtpub.dynamodb.cookbook.addressbook.services.AddressBookService;

@Controller
public class AddressbookController {
	@Autowired
	private AddressBookService addressBookService;

	@RequestMapping(value = "/v1/contact", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public void addContact(@RequestBody Contact contact) {

		addressBookService.addContact(contact);

	}

	@RequestMapping(value = "/v1/contact/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<Contact> getAllContacts(@RequestParam String userEmail) {

		return addressBookService.getAllContacts(userEmail);

	}

}
