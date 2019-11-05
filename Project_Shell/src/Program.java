/*
 * This program is a shell for the CS4513 Individual Project
 * OU 2019 Fall Semester CS 4513-001
 * Author: Treyson Martin
 * This program simple passes commands from the user and sends them 
 * to a Azure DB to execute queries like insert, update, select, and delete commands
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.*;

public class Program 
{
	// Connect to database
	 final static String hostName = "mart3422-sql-server.database.windows.net";
	 final static String dbName = "cs-dsa-4513-sql-db";
	 final static String user = "mart3422";
	 final static String password = "Atticus666418!";
	 final static String url =
	String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;host"
	+ "NameInCertificate=*.database.windows.net;loginTimeout=30;",
	 hostName, dbName, user, password);
	
	public static void main(String[] args) 
	{
		//Display welcome message and call method that prints out options.
		System.out.println("Welcome to JS Accounting System.");
		System.out.println("Below are options 1-18 that you can use.");
		
		Scanner scanner = new Scanner(System.in);
		int option = 0;
		do 
		{
			DisplayOptions();
			
			option = scanner.nextInt();
			scanner.nextLine();

			switch(option)
			{
				case 1:
					InsertCustomer(0);
					break;
				case 2:
					InsertDepartment(0);
					break;
				case 3:
					InsertAssemblyInfo();
					break;
				case 4:
					InsertProcessInfo();
					InsertDepartment(0);
					break;
				case 5:
					CreateAccount();
					break;
				case 6:
					InsertJobInfo();
					break;
				case 7:
					ConfirmJobCompletion();
					break;
				case 8:
					InsertTransactionInfo();
					break;
				case 9:
					RetrieveAssemblyCost();
					break;
				case 10:
					RetrieveDepLaborTime();
					break;
				case 11:
					RetrieveProcesses();
					break;
				case 12:
					RetrieveJobsCompleted();
					break;
				case 13:
					RetrieveCustomers();
					break;
				case 14:
					DeleteCutJobs();
					break;
				case 15:
					UpdatePaintJob();
					break;
				case 16:
					ImportExportCust(0);
					break;
				case 17:
					ImportExportCust(1);
					break;
				case 18:  //do nothing and break out of loop.
					break;
				default:
					System.out.println("Invalid Option.\nSelect from Option below.");
			}
			
		}
		while(option != 18);

		//Ending program.
		System.out.println("Exiting...\nHave a good day!");
		System.exit(0);
	}
	//option 16 and 17 
	public static void ImportExportCust(int flag) 
	{
		String line = "";
		String splitBy = ","; //for csv
		String userFile = "";
		Scanner sc = new Scanner(System.in);
		try 
		{
			System.out.print("Input file name (Include .csv): ");
			//sc.nextLine();
			//File file = new File(userFile);
			BufferedReader br = new BufferedReader(new FileReader("cust.csv"));
			while((line = br.readLine()) != null)//goes to EOF
			{
				String[] customers = line.split(splitBy);
				System.out.println("Customer: Name = " + customers[0] + " Address: " +
				customers[1] + " Category: " + customers[2]);  //debug.
			}
			br.close();
		}
		catch(Exception ex) 
		{
			System.out.println("Error in ImportExportCust. Error: " + ex.toString());
		}
	}
	
	//Option 15 Update color on Paint Job.
	public static void UpdatePaintJob() {}
	
	//Option 14 Delete Cut jobs based on date range.
	public static void DeleteCutJobs() {}
	
	//Option 13 Retrieve Customers based on category
	public static void RetrieveCustomers() {}
	
	//Option 12 Get jobs based on dept and date completed
	public static void RetrieveJobsCompleted(){}
	
	//Option 11 get processes from given assembly id.
	public static void RetrieveProcesses() {}
	
	//Option 10 Retrieve labor time based on department and it's by jobs completed.
	public static void RetrieveDepLaborTime()
	{}
	
	//Option 9 Retrieve Assembly costs by id.
	public static void RetrieveAssemblyCost()
	{}
	
	//Option 8 Insert Transaction Information 
	public static void InsertTransactionInfo()
	{
		
	}
	
	//Option 7 Update job with completion date and type info.
	public static void ConfirmJobCompletion()
	{
		
	}
	
	//Option 6 insert job information
	public static void InsertJobInfo() 
	{
	}
	
	//Option 5 Create account and associate it to appropriate thing.
	public static void CreateAccount()
	{
		try 
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Account Number: ");
			int accNum = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter type of account: 1)Process 2)Assembly 3)Department. Enter a number");
			int type = sc.nextInt();
			sc.nextLine();
			switch(type)
			{
			//Process Account
			case 1:
				System.out.print("Enter process id: ");
				int procId = sc.nextInt();
				break;
			//Assembly Account
			case 2:
				System.out.print("Enter Assembly id: ");
				int assId = sc.nextInt();
				break;
			//Department Account	
			case 3:
				System.out.print("Enter Department number: ");
				int deptNum = sc.nextInt();
				break;
			default:
				System.out.println("Incorrect type for process.");
			}
		}
		catch (Exception ex){}
	}
	
	//Option 4 enter process information followed by department information for supervision.
	public static void InsertProcessInfo()
	{
		try 
		{
			String procType = "";
			String method = "";
			String machineType = "";
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Process Id: ");
			int procId = sc.nextInt();
			sc.nextLine(); //buffer out
			System.out.println("Enter the type: 1)Paint 2)Fit 3)Cut. Type number");
			int type = sc.nextInt();
			sc.nextLine();
			switch(type)
			{
			//Paint type
			case 1:
				System.out.print("Enter paint type: ");
				procType = sc.nextLine();
				System.out.print("Enter method: ");
				method = sc.nextLine();
				break;
			//Fit type
			case 2:
				System.out.print("Enter fit type: ");
				procType = sc.nextLine();
				break;
			//Cut type	
			case 3:
				System.out.print("Enter the cut type: ");
				procType = sc.nextLine();
				System.out.print("Enter the machine type: ");
				machineType = sc.nextLine();
				break;
			default:
				System.out.println("Incorrect type for process.");
			}
		}
		
		catch(Exception ex) 
		{
			System.out.println("Error in InsertProcessInfo(). Error: " + ex.toString());
		}
	}
	
	//Option 3 Enter Assembly Info.
	public static void InsertAssemblyInfo()
	{
		try
		{
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Assembly Id: ");
			int assId = sc.nextInt();
			sc.nextLine();
			System.out.print("Enter Date Ordered: ");
			String dateOrdered = sc.nextLine();
			System.out.print("Enter Assembly Details: ");
			String details = sc.nextLine();
			System.out.print("Enter Customer Name: ");
			String custName = sc.nextLine();
			
			System.out.println("Inputting following information...");
			System.out.println("Assembly Id: " + assId + "\nDate Ordered:" + dateOrdered
					+ "\nAssembly Details: " + details + "\nCustomer Name: " + custName);
		}
		catch(Exception ex)
		{
			System.out.println("Error in the InsertAssemblyInfo. Error: " + ex.toString());
		}
	}
	
	//Option 2 Enter Department Information.
	public static void InsertDepartment(int flag)
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Department: ");
			int deptNum = sc.nextInt();
			sc.nextLine(); //buffer out.
			if(deptNum != -1)
			{
				System.out.print("Enter Department data: ");
				String deptData = sc.nextLine();
				
				/*
				//debug
				System.out.println("Inputting following information...");
				System.out.println("Department Number: " + deptNum + "\nDepartment Data: "
						+ deptData);
				*/
				
				String sql = "INSERT INTO Department VALUES (" + deptNum + ",'" 
						+ deptData + "')";
				//System.out.println(sql); //debug
						
				Statement statement = connection.createStatement();
				statement.execute(sql); //execute insert query.
				System.out.println("Insertion Successful");
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error in InsertDepartment. Error: " + ex.toString());
			InsertDepartment(1);
		}
	}
	
	//Option 1 Enter Customer information
	public static void InsertCustomer(int flag)
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			
			if(flag > 0)
				System.out.println("Type exit to go back to options");
			
			Scanner sc = new Scanner(System.in);
			System.out.print("\nPlease input Customer name: ");
			String custName = sc.nextLine();
			
			if(!custName.toLowerCase().equals("exit"))
			{
				System.out.print("\nPlease input Customer address: ");
				String address = sc.nextLine();
				System.out.print("\nPlease input Customer category (1-10): ");
				int category = sc.nextInt();
				sc.nextLine(); //buffer out.
				
				/*
				//debug
				System.out.println("Inputing following information...");
				System.out.println("Customer Name: " + custName + "\nAddress: " + address
						+ "\nCategory: " + category);
				*/
			
				String sql = "INSERT INTO Customers VALUES ('" + custName + "','" 
				+ address + "', " + category + ")";
				//System.out.println(sql); //debug
				
				Statement statement = connection.createStatement();
				statement.execute(sql); //execute insert query.
				System.out.println("Insertion Successful");
			}
			//sc.close();  //close stream.
		}
		catch(Exception ex)
		{
			System.out.println("Error in InsertCustomer. Error: " + ex.toString());
			InsertCustomer(1); //Going back to input.
		}
	}
	
	//Displays the list of options.
	public static void DisplayOptions() 
	{
		System.out.println("\n(1) Enter a new customer");
		System.out.println("(2) Enter a new department");
		System.out.println("(3) Enter new Assembly; REQUIRED: Customer_Name, "
				+ " Assembly_Details, Assembly_Id, Date_Ordered");
		System.out.println("(4) Enter new Process_Id and Department Information");
		System.out.println("(5) Create new Account and Setup");
		System.out.println("(6) Enter new Job; REQUIRED: Job_no, Assembly_id,"
				+ " Process_id, Date_of_Job_Started");
		System.out.println("(7) Enter Job Completion Date and Type of Job");
		System.out.println("(8) Enter Transaction Information");
		System.out.println("(9) Display Assembly Cost; REQUIRED: Assembly_Id");
		System.out.println("(10) Display total Labor time of"
				+ " Job; REQUIRED: Department_Number and Date_Completed");
		System.out.println("(11) Display Processes and their Department;"
				+ " REQUIRED: Process_Id");
		System.out.println("(12) Display Completed Jobs; REQUIRED: Date_Completed"
				+ " and Department_Number");
		System.out.println("(13) Display Customers based on category");
		System.out.println("(14) Delete Cut-Jobs; REQUIRED: Job_no and Date_Range");
		System.out.println("(15) Change color of a Paint-Job");
		System.out.println("(16) Import: Enter new customers from a data file "
				+ " until the file is empty");
		System.out.println("(17) Export: Retrieve customers whose category is in a given"
				+ " range");
		System.out.println("(18) Quit");
	}

}