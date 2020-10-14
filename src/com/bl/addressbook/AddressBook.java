package com.bl.addressbook;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AddressBook {
	public enum IOservice {
		CONSOLE_IO, FILE_IO
	}

	public static final String ADDRESS_BOOK_FILES = "C:\\Users\\user\\eclipse-workspace\\AddressBook\\AddressBookFiles";
	static Scanner in = new Scanner(System.in);
	private List<Contact> contactList = new ArrayList<>();
	private Map<String, Contact> contactMap = new HashMap<>();
	static Map<String, AddressBook> addressBookMap = new HashMap<>();

	public AddressBook(String objectName) {
		addressBookMap.put(objectName, this);
	}

	public AddressBook(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public Map<String, Contact> getContactMap() {
		return contactMap;
	}

	public static void fetchingAddressBooksFromFiles() {
		addressBookMap = new HashMap<String, AddressBook>();
		Path filesPath = Paths.get(ADDRESS_BOOK_FILES);
		File[] addressBookFiles = filesPath.toFile().listFiles();
		for (File file : addressBookFiles) {
			AddressBookFileIOOperations fileReadObject = new AddressBookFileIOOperations(file.toPath());
			addressBookMap.put(file.getName().replaceFirst("[.][^.]+$", ""),
					new AddressBook(fileReadObject.readData()));
		}
	}

	public List<Contact> addNewContact() {
		Contact person = new Contact();
		System.out.print("First Name: ");
		person.setFirstName(in.next());
		System.out.print("Last Name: ");
		person.setLastName(in.next());
		if (contactMap.containsKey(person.getName())) {
			System.out.println("Duplicate Name.");
			return contactList;
		}
		System.out.print("Address: ");
		person.setAddress(in.next());
		System.out.print("City: ");
		person.setCity(in.next());
		System.out.print("State: ");
		person.setState(in.next());
		System.out.print("Zip: ");
		person.setZip(in.next());
		System.out.print("Phone Number: ");
		person.setPhoneNo(in.next());
		System.out.print("Email: ");
		person.setEmail(in.next());

		if (contactList.stream().anyMatch(other -> other.equals(person))) {
			System.out.println("Duplicate details.");
			return contactList;
		}
		contactList.add(person);
		contactMap.put(person.getName(), person);
		return contactList;
	}

	public void editContact(String firstName, String lastName) {
		String name = (firstName + " " + lastName).toLowerCase();

		if (contactMap.containsKey(name)) {
			System.out.println("What do you want to edit?");
			String s = in.next().toLowerCase();
			switch (s) {
			case ("address"):
				System.out.print("Enter new address: ");
				contactMap.get(name).setAddress(in.next());
				break;
			case ("city"):
				System.out.print("Enter new city: ");
				contactMap.get(name).setCity(in.next());
				break;
			case ("state"):
				System.out.print("Enter new state: ");
				contactMap.get(name).setState(in.next());
				break;
			case ("zip"):
				System.out.print("Enter zip: ");
				contactMap.get(name).setZip(in.next());
				break;
			case ("phoneno"):
				System.out.print("Enter phone no: ");
				contactMap.get(name).setPhoneNo(in.next());
				break;
			case ("email"):
				System.out.print("Enter new email: ");
				contactMap.get(name).setEmail(in.next());
				break;
			}

		} else
			System.out.println(name.toUpperCase() + " is not present in the Address Book.");
	}

	public void deleteContact(String firstName, String lastName) {
		String name = (firstName + " " + lastName).toLowerCase();
		if (contactMap.containsKey(name)) {
			System.out.println("Type 'Y'to confirm, else type anything.");
			if (in.next().toUpperCase().equals("Y"))
				contactMap.remove(name);
			else
				return;
		} else {
			System.out.println(name.toUpperCase() + " is not present in the Address Book.");
		}
	}

	public List<Contact> searchPersonsByCity(String city) {
		return contactList.stream().filter(person -> person.getCity().equals(city)).collect(Collectors.toList());
	}

	public List<Contact> searchPersonsByState(String state) {
		return contactList.stream().filter(person -> person.getState().equals(state)).collect(Collectors.toList());
	}

	private Map<String, List<Contact>> personsByCityMap = new TreeMap<>();

	public Map<String, List<Contact>> viewPersonsByCity() {
		contactList.stream()
				.forEach(person -> personsByCityMap.put(person.getCity(), searchPersonsByCity(person.getCity())));
		return personsByCityMap;
	}

	private Map<String, List<Contact>> personsByStateMap = new TreeMap<>();

	public Map<String, List<Contact>> viewPersonsByState() {
		contactList.stream()
				.forEach(person -> personsByStateMap.put(person.getState(), searchPersonsByState(person.getState())));
		return personsByStateMap;
	}

	public void countByCity() {
		Set<String> cities = viewPersonsByCity().keySet();
		cities.stream().forEach(city -> System.out
				.println(city + " contains: " + viewPersonsByCity().get(city).stream().count() + " persons."));
	}

	public void countByState() {
		Set<String> states = viewPersonsByCity().keySet();
		states.stream().forEach(state -> System.out
				.println(state + " contains: " + viewPersonsByCity().get(state).stream().count() + " persons."));
	}

	public void sortByName() {
		System.out.println("Sorting current address book by name: ");
		contactList.stream().sorted((a, b) -> a.getName().compareTo(b.getName())).forEach(System.out::println);
	}

	public void sortByCity() {
		System.out.println("Sorting current address book by city: ");
		contactList.stream().sorted((a, b) -> a.getCity().compareTo(b.getCity())).forEachOrdered(System.out::println);
	}

	public void sortByState() {
		System.out.println("Sorting current address book by state: ");
		contactList.stream().sorted((a, b) -> a.getState().compareTo(b.getState())).forEachOrdered(System.out::println);
	}

	public void sortByZip() {
		System.out.println("Sorting current address book by ZIP: ");
		contactList.stream().sorted((a, b) -> a.getZip().compareTo(b.getZip())).forEachOrdered(System.out::println);
	}
}
