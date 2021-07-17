package com.cognixia.jump.coreProject;
//Matthew Roger
//7/16/2021

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeManagment {
	// to avoid having to pass these around
	private static ArrayList<Employee> employeeList = new ArrayList<Employee>();
	private static File records = new File("resources/employees.txt");

	public static void main(String[] args) {
		boolean flag = true;
		// create the file if it dosn't exist
		if (!records.exists()) {
			try {
				records.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			loadEmployees();
		}
		//single scanner to pass around
		Scanner input = new Scanner(System.in);

		// main logic loop
		while (flag) {
			System.out.println("***Employee Managment System***");
			System.out.println(employeeList.size() + " Employees on record.");
			System.out.println("Press 1 to list Employees");
			System.out.println("Press 2 to view Employees by Department");
			System.out.println("Press 3 to add an Employee");
			System.out.println("Press 4 to remove an Employee");
			System.out.println("Press 0 to save and quit");

			int choice = -1;

			while (choice < 0 || choice > 4) {
				try {
					System.out.println("Enter Choice:");
					choice = input.nextInt();
				} catch (Exception e) {
					System.out.println("Invalid input");
					// clear scan buffer
					input.next();
				}
			}

			// clear scan buffer
			input.nextLine();

			// main logic switch
			switch (choice) {
			case 0:
				//quit the main loop
				flag = false;
				break;
			case 1:
				for (Employee employee : employeeList) {
					System.out.println(employee);
				}
				break;
			case 2:
				listDepartments(input);
				break;
			case 3:
				addEmployee(input);
				break;
			case 4:
				removeEmployee(input);
				break;
			default:
				// should never reach here
				break;
			}
		}

		input.close();
		// update changes to files before exiting program
		writeData();
	}

	public static void loadEmployees() {
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(records))) {
			// run until end of file
			//could also use != null instead
			while (true) {
				employeeList.add((Employee) reader.readObject());
			}
		} catch (EOFException e) {
			// eof - no error in this case
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void writeData() {
		File csv = new File("resources/employees.csv");
		// write the employee list to the data file
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(records, false))) {
			employeeList.forEach(emp -> {
				try {
					writer.writeObject(emp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//write the employee list to the csv file
		try (BufferedWriter csvWriter = new BufferedWriter(new FileWriter(csv, false))) {
			employeeList.forEach(emp -> {
				try {
					csvWriter.write(emp.toString() + ('\n'));;
				} catch (IOException error) {
					error.printStackTrace();
				}
			}); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void listDepartments(Scanner input) {
		System.out.println("Select Department Number:");
		// list departments to user
		Department[] departList = Department.values();
		for (int i = 0; i < departList.length; i++) {
			System.out.println(i + " = " + departList[i]);
		}
		int choice = -1;
		// get a valid department
		while (choice < 0 || choice >= departList.length) {
			try {
				choice = input.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid input");
				// clear scan buffer
				input.next();
			}
		}
		// get department enum object
		Department depart = departList[choice];
		//use a stream to print every employee of that department
		employeeList.stream()
		.filter(emp -> emp.getDepartment() == depart)
		.forEach(System.out::println);
	}

	public static void addEmployee(Scanner input) {
		//new Employee object to be saved
		Employee temp = new Employee();
		try {
			// set up new employee
			System.out.println("Enter Employee Name:");
			temp.setName(input.nextLine());

			System.out.println("Enter Job Title:");
			temp.setTitle(input.nextLine());

			System.out.println("Select Department Number:");
			// list departments
			Department[] departList = Department.values();
			for (int i = 0; i < departList.length; i++) {
				System.out.println(i + " = " + departList[i]);
			}
			int choice = -1;
			// get a valid department
			while (choice < 0 || choice >= departList.length) {
				try {
					choice = input.nextInt();
				} catch (Exception e) {
					System.out.println("Invalid input");
					// clear scan buffer
					input.next();
				}
			}

			temp.setDepartment(departList[choice]);

			// add employee to the list
			employeeList.add(temp);

		} catch (Exception e) {
			System.out.println("e");
			// clear scan buffer
			input.next();
		}
	}

	public static void removeEmployee(Scanner input) {
		String name;
		boolean found = false;
		try {
			System.out.println("Enter Employee Name:");
			name = input.nextLine();
			System.out.println("Searching for " + name);
			// search for the name
			// then remove the first instance found
			for (int i = 0; i < employeeList.size(); i++) {
				if (employeeList.get(i).getName().equals(name)) {
					employeeList.remove(i);
					found = true;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("e");
			// clear scan buffer
			input.next();
		}

		if (found)
			System.out.println("Employee Removed");
		else
			System.out.println("Employee Not Found");
	}

}
