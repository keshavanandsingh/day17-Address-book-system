package com.bl.addressbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.opencsv.exceptions.*;

public class AddressBookCSVFileOperations {
	private Path csvFilePath;

	public AddressBookCSVFileOperations(Path csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public void writeCSVData(List<Contact> addressBook) {
		try (Writer writer = Files.newBufferedWriter(csvFilePath)) {
			StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder<Contact>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(addressBook);
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		}
	}

	public List<Contact> readCSVData() {
		try (Reader reader = Files.newBufferedReader(csvFilePath)) {
			CsvToBeanBuilder<Contact> builder = new CsvToBeanBuilder<Contact>(reader);
			CsvToBean<Contact> csvToBean = builder.withType(Contact.class).withIgnoreLeadingWhiteSpace(true).build();
			List<Contact> addressBook = csvToBean.parse();
			return addressBook;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
