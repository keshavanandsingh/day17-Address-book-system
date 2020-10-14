package com.bl.addressbook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AddressBookFileIOOperations {

	public Path addressBookPath;

	public AddressBookFileIOOperations(Path addressBookPath) {
		this.addressBookPath = addressBookPath;
	}

	public void writeData(List<Contact> addressBook) {
		StringBuffer contactBufferString = new StringBuffer();
		addressBook.stream().forEach(contact -> {
			contactBufferString.append(contact.toString());
		});

		try {
			Files.write(addressBookPath, contactBufferString.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Contact> readData() {
		List<Contact> contactReadList = new ArrayList<Contact>();
		try {
			Files.lines(addressBookPath).map(line -> line.trim()).forEach(line -> {
				String[] data = line.split("[a-zA-Z ]+:\\s");
				String fullName = data[1].trim();
				String[] name = fullName.split(" ");
				String address = data[2].trim();
				String city = data[3].trim();
				String state = data[4].trim();
				String zip = data[5].trim();
				String phoneNumber = data[6].trim();
				String email = data[7].trim();
				Contact newContact = new Contact(name[0], name[1], address, city, state, zip, phoneNumber, email);
				contactReadList.add(newContact);
			});
		} catch (IOException e) {
		}
		return contactReadList;
	}
}