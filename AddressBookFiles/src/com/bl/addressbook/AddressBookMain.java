package com.bl.addressbook;

import java.io.File;
import java.nio.file.*;
import java.util.*;

public class AddressBookMain {
	private static HashMap<String, AddressBook> addressBookMap;
	public static final String ADDRESS_BOOK_FILES = "C:\\Users\\user\\eclipse-workspace\\AddressBook\\AddressBookFiles";
	private static Scanner sc = new Scanner(System.in);

	public static void fetchingAddressBooksFromFiles() {
		addressBookMap = new HashMap<String, AddressBook>();
		Path dictionaryPath = Paths.get(ADDRESS_BOOK_FILES);
		File[] addressBookFiles = dictionaryPath.toFile().listFiles();
		for (File file : addressBookFiles) {
			AddressBookFileIOOperations fileReadObject = new AddressBookFileIOOperations(file.toPath());
			addressBookMap.put(file.getName().replaceFirst("[.][^.]+$", ""),
					new AddressBook(fileReadObject.readData()));
		}
	}

	private static void operations(AddressBook addressBook) {
		System.out.println("Add new contact: type 1" + "\nEdit someone's details: type 2" + "\nDelete a contact: type 3"
				+ "\nGo Back to Main Menu" + "\nSearch by city: type 5" + "\nSearch by state: type 6"
				+ "\nExit the procces: type 0");
		System.out.print("Enter operation code: ");
		String option = sc.next();
		switch (option) {
		case "1":
			addressBook.addNewContact();
			break;
		case "2":
			System.out.println("Enter the person's name you want to edit: ");
			addressBook.editContact(sc.next(), sc.next());
			break;
		case "3":
			System.out.println("Enter the person's name to delete his/her details: ");
			addressBook.deleteContact(sc.next(), sc.next());
			break;
		case "4":
			mainMenu();
			break;
		case "5":
			System.out.println("Enter the city name to search: ");
			System.out.println(addressBook.searchPersonsByCity(sc.next()));
			break;
		case "6":
			System.out.println("Enter the state name to search: ");
			System.out.println(addressBook.searchPersonsByState(sc.next()));
			break;
		case "0":
			System.out.println("Exiting...");
			return;
		default:
			System.out.println("Wrong option entered");
		}
		operations(addressBook);
	}

	private static void mainMenu() {
		System.out.println("**Main Menu**" + "\nSelect Addressbook: Press 1" + "\nExit: Press 0");
		String menu = sc.next();
		switch (menu) {
		case "1":
			System.out.println("Availbale addressbooks => " + AddressBook.addressBookMap.keySet()
					+ "\nEnter addressbook name properly: ");
			String addressBookName = sc.next();
			if (AddressBook.addressBookMap.containsKey(addressBookName))
				operations(AddressBook.addressBookMap.get(addressBookName));
			else
				System.out.println("Wrong address book name. Try again.");
			break;
		case "0":
			System.out.println("Exiting...");
			return;
		default:
			System.out.println("Wrong menu code entry. Try again.");
		}
		mainMenu();
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Address Book Program!");

		// Fetching address book from files
		fetchingAddressBooksFromFiles();
		// Entering into to main menu
		// mainMenu();

		AddressBook newAddressBook = new AddressBook("newAddressBook");
		Path newAdPath = Paths
				.get("C:\\Users\\user\\eclipse-workspace\\AddressBook\\AddressBookFiles\\NewAddressBook.txt");
		AddressBookFileIOOperations addressBookFileIOOperations = new AddressBookFileIOOperations(newAdPath);
		// Using readData method to write using console into the file
		addressBookFileIOOperations.writeData(newAddressBook.addNewContact());
		// Sorting each address book
		for (String adbk : addressBookMap.keySet()) {
			addressBookMap.get(adbk).sortByCity();
		}

	}
}