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
import java.util.Date;

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
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Enter Department Number: ");
			int deptNum = sc.nextInt();
			sc.nextLine(); //clear buffer.
			
			String sql1 = "SELECT job_no FROM Supervises WHERE dept_num = " + deptNum;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql1); //execute select query.
			List<Integer> jobNos = new ArrayList<Integer>();
			int laborTime = 0; //initialize variable.
			while(resultSet.next())
			{
				jobNos.add(resultSet.getInt("job_no")); //populate the list.
			}
			//Create String from jobNos list. This will be the WHERE clause.
			String whereClause = "WHERE job_no in (";
			Iterator<Integer> iterator = jobNos.iterator(); 
			while(iterator.hasNext())
			{
				Integer i = iterator.next();
				whereClause += i.toString();
				whereClause += ", ";
			}
			whereClause += ")";
			
			String sql2 = "SELECT labor_time FROM Job_Cut " + whereClause;
			String sql3 = "SELECT labor_time FROM Job_Fit " + whereClause;
			String sql4 = "SELECT labor_time FROM Job_Paint " + whereClause;

			//debug.
			System.out.println(sql2);
			System.out.println(sql3);
			System.out.println(sql4);

		}
		catch(Exception ex)
		{
			System.out.println("Error in RetrieveDepLaborTime(). Error: " + ex.toString());
		}
	}
	
	//Option 9 Retrieve Assembly costs by id.
	public static void RetrieveAssemblyCost()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			Scanner sc = new Scanner(System.in);
			
			System.out.print("Enter Assembly Id: ");
			int assId = sc.nextInt();
			
			String sql = "SELECT acc_num FROM Has1 WHERE assembly_id = " + assId;
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql); //execute select query.
			int accNum = 0;
			while(resultSet.next())
			{
				accNum = resultSet.getInt("acc_num");
			}
			
			sql = "SELECT cost FROM Account_Assembly WHERE acc_num = " + accNum;
			resultSet = statement.executeQuery(sql); //execute select query.
			float cost = 0;
			while(resultSet.next())
			{
				cost = resultSet.getFloat("cost");
			}
			
			System.out.println("Assembly Id: " + assId + "; Costs: " + cost);
		}
		catch(Exception ex)
		{
			System.out.println("Error in RetrieveAssemblyCost(). Error: " + ex.toString());
		}
	}
	
	//Option 8 Insert Transaction Information 
	public static void InsertTransactionInfo()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Transaction No: ");
			int transNo = sc.nextInt();
			sc.nextLine(); //clear buffer.
			System.out.print("Enter sup-cost: ");
			double cost = sc.nextDouble();
			sc.nextLine(); //clear buffer.
			
			System.out.print("Enter job no for transaction: ");
			int jobno = sc.nextInt();
			sc.nextLine();
			
			System.out.print("Enter account id: ");
			int accId = sc.nextInt();
			sc.nextLine(); //clear buffer.
			
			String sql1 = "INSERT INTO [Transaction] VALUES (" + transNo +  ", " + cost + ")";
			String sql2 = "INSERT INTO Records VALUES (" + jobno +  ", " + transNo + ")";
			String sql3 = "INSERT INTO Updates VALUES (" + transNo +  ", " + accId + ")";
			//Added all 3 though only one will update since the primary keys are unique
			String sql4 = "UPDATE Account_Assembly SET cost = " + cost + " WHERE acc_num = " + accId;
			String sql5 = "UPDATE Account_Assembly SET cost = " + cost + " WHERE acc_num = " + accId;
			String sql6 = "UPDATE Account_Assembly SET cost = " + cost + " WHERE acc_num = " + accId;
			
			Statement statement = connection.createStatement();
			statement.execute(sql1); //execute insert query.
			statement.execute(sql2); //execute insert query.
			statement.execute(sql3); //execute insert query.
			statement.execute(sql4); //execute insert query.
			statement.execute(sql5); //execute insert query.
			statement.execute(sql6); //execute insert query.
			System.out.println("Insertions/Updates Successful");
		}
		catch(Exception ex)
		{
			System.out.println("Error in InsertTransactionInfo(). Error: "+ ex.toString());
		}
	}
	
	//Option 7 Update job with completion date and type info.
	public static void ConfirmJobCompletion()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			String sql1 = "";
			String sql2 = "";
			String sql3 = "";
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter JobNo: ");
			int jobNo = sc.nextInt();
			
			//select statement to se if job exists.
			sql1 = "select job_no from Job WHERE job_no = " + jobNo;
			
			Statement statement = connection.createStatement();
			Statement statement2 = connection.createStatement();
			Statement statement3 = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql1); //execute select query.
			ResultSet resultSet2 = null;
			ResultSet resultSet3 = null;
			//See if in job table if so then loop through other 3 to find job table.
			int counter = 1; //1 for paint
			int result = 0;
			while(resultSet.next())
			{
				result = resultSet.getInt("job_no");	
			}
			if(result == jobNo)
			{
				int r2 = 0;
				System.out.println("Job Found...");
				sql1 = "SELECT job_no from Job_Paint WHERE job_no = " + jobNo; //new query.
				resultSet2 = statement2.executeQuery(sql1);
				while(resultSet2.next())
				{
					r2 = resultSet2.getInt("job_no");
				}
				if(r2 != jobNo)
				{
					int r3 = 0;
					counter++; //2 for fit
					sql1 = "SELECT job_no from Job_Fit WHERE job_no = " + jobNo;
					resultSet3 = statement3.executeQuery(sql1);
					while(resultSet3.next())
					{
						r3 = resultSet3.getInt("job_no");
					}
					if(r3 != jobNo)
					{
						counter++; //3 for cut
					}
				}
			}
			else 
			{
				System.out.println("Job not found.");
				return; //exit function.
			}
			
			sc.nextLine(); //buffer cleared.
			System.out.print("Enter date completed: ");
			String dateComp = sc.nextLine();
			
			sql2 = "UPDATE Job set date_completed = '" + dateComp + "' where job_no = " + jobNo;
			if(counter == 1)
			{
				System.out.print("Enter Paint Color: ");
				String color = sc.nextLine();
				System.out.print("Enter Volume: ");
				int volume = sc.nextInt();
				sc.nextLine(); //clear buffer.
				System.out.print("Enter Labor Time: ");
				int laborTime = sc.nextInt();
				sc.nextLine(); //clear buffer.
				sql3 = "UPDATE Job_Paint set date_completed = '" + dateComp
						+ "', color = '" + color 
						+ "', volume = " + volume 
						+ ", labor_time = " + laborTime +" where job_no = " + jobNo;
			}
			else if(counter == 2)
			{
				System.out.print("Enter Labor Time: ");
				int laborTime = sc.nextInt();
				sc.nextLine(); //clear buffer.
				sql3 = "UPDATE Job_Fit set date_completed = '" + dateComp
						+ "', labor_time = " + laborTime + " where job_no = " + jobNo;
			}
			else if(counter == 3)
			{
				System.out.print("Enter Machine Type: ");
				String machType = sc.nextLine();
				System.out.print("Enter Time Used: ");
				int timeUsed = sc.nextInt();
				sc.nextLine(); //clear buffer.
				System.out.print("Enter Material Used: ");
				String material = sc.nextLine();
				System.out.print("Enter Labor Time: ");
				int laborTime = sc.nextInt();
				sc.nextLine(); //clear buffer.
				sql3 = "UPDATE Job_Cut set date_completed = '" + dateComp 
						+ "', machine_type = '" + machType 
						+ "', time_used = " + timeUsed 
						+ ", material_used = '" + material 
						+ "', labor_time = " + laborTime + " where job_no = " + jobNo;	
			}
			
			//Debug.
			//System.out.println(sql1);
			//System.out.println(sql2);
			//System.out.println(sql3);

			statement.execute(sql2); //execute insert query.
			statement.execute(sql3); //execute insert query.
			System.out.println("Insertions Successful");
		}
		catch (Exception ex)
		{
			System.out.println("Error in ConfirmJobCompletion(). Error: " + ex.toString());
		}
	}
	
	//Option 6 insert job information
	public static void InsertJobInfo() 
	{
		try
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			Scanner sc = new Scanner(System.in);
			
			String sql1 = "";
			String sql2 = "";
			String sql3 = "";
			String sql4 = "";
			String tableName = "";
			System.out.print("Enter Job No: ");
			int jobNo = sc.nextInt();
			System.out.print("Enter Job Type: 1)Cut 2)Paint 3)Fit Type Number. ");
			int type = sc.nextInt();
			sc.nextLine(); //clear buffer.
			System.out.print("Enter Date for Job Started: ");
			String dateStarted = sc.nextLine();
			System.out.print("Enter Process Id for Job: ");
			int procId = sc.nextInt();
			sc.nextLine(); //clear buffer.
			System.out.print("Enter Assembly Id for Job: ");
			int assId = sc.nextInt();
			sc.nextLine();
			
			switch(type)
			{
			case 1:
				tableName = "Job_Cut";
				break;
			case 2:
				tableName = "Job_Paint";
				break;
			case 3:
				tableName = "Job_Fit";
				break;
			default:
				System.out.println("Incorrect Job Type.");
				return;  //break out of function.
			}
			
			sql1 = "INSERT INTO Job (job_no, date_started) VALUES (" + jobNo + ", '" + dateStarted + "')";
			sql2 = "INSERT INTO " + tableName  +" (job_no, date_started) VALUES (" + jobNo + ", '" + dateStarted + "')";
			sql3 = "INSERT INTO Assigned VALUES (" + procId + ", " + jobNo + ")";
			sql4 = "INSERT INTO Manufactures VALUES (" + assId + ", " + procId
					+ ", " + jobNo + ")";
			/*
			//debug.
			System.out.println(sql1);
			System.out.println(sql2);
			System.out.println(sql3);
			System.out.println(sql4);
			*/
			Statement statement = connection.createStatement();
			statement.execute(sql1); //execute insert query.
			statement.execute(sql2); //execute insert query.
			statement.execute(sql3); //execute insert query.
			statement.execute(sql4); //execute insert query.
			System.out.println("Insertions Successful");
			
		}
		catch(Exception ex)
		{
			System.out.println("Error in InsertJobInfo(). Error: " + ex.toString());
		}
	}
	
	//Option 5 Create account and associate it to appropriate thing.
	public static void CreateAccount()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			String sql1 = "";
			String sql2 = "";
			String sql3 = "";
			long millis=System.currentTimeMillis();  
			java.sql.Date date=new java.sql.Date(millis);
			Scanner sc = new Scanner(System.in);
			System.out.print("Enter Account Number: ");
			int accNum = sc.nextInt();
			sc.nextLine();
			System.out.println("Enter type of account: 1)Process 2)Assembly 3)Department. Enter a number");
			int type = sc.nextInt();
			sc.nextLine();
			int procId = 0;
			int assId = 0;
			int deptNum = 0;
			switch(type)
			{
			//Process Account
			case 1:
				System.out.print("Enter process id: ");
				procId = sc.nextInt();
				sql2 = "INSERT INTO Account_Process VALUES ("+ accNum + ", '" + date + "', 0)";
				sql3 = "INSERT INTO Has2 VALUES (" + procId + ", " + accNum + ")";
				break;
			//Assembly Account
			case 2:
				System.out.print("Enter Assembly id: ");
				assId = sc.nextInt();
				sql2 = "INSERT INTO Account_Assembly VALUES ("+ accNum + ", '" + date + "', 0)";
				sql3 = "INSERT INTO Has1 VALUES (" + assId + ", " + accNum + ")";
				break;
			//Department Account	
			case 3:
				System.out.print("Enter Department number: ");
				deptNum = sc.nextInt();
				sql2 = "INSERT INTO Account_Department VALUES ("+ accNum + ", '" + date + "', 0)";
				sql3 = "INSERT INTO Has3 VALUES (" + deptNum + ", " + accNum + ")";
				break;
			default:
				System.out.println("Incorrect type for process.");
			}
			
			sql1 = "INSERT INTO Account VALUES (" + accNum + ", '" + date + "')";
			
			//System.out.println(sql1); //debug
			//System.out.println(sql2); //debug
			//System.out.println(sql3); //debug
				
			Statement statement = connection.createStatement();
			statement.execute(sql1); //execute insert query.
			statement.execute(sql2); //execute insert query.
			statement.execute(sql3); //execute insert query.
			System.out.println("Insertions Successful");
		}
		catch (Exception ex)
		{
			System.out.println("Error in CreateAccount(). Error: " + ex.toString());
		}
	}
	
	//Option 4 enter process information followed by department information for supervision.
	public static void InsertProcessInfo()
	{
		try 
		{
			Connection connection = DriverManager.getConnection(url); //sql connection.
			String sql1 = "";
			String sql2 = "";
			
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
				sql2 = "INSERT INTO Process_Paint "
						+ "VALUES (" + procId + ", '', '" + procType + "', '" 
						+ method + "')";
				break;
			//Fit type
			case 2:
				System.out.print("Enter fit type: ");
				procType = sc.nextLine();
				sql2 = "INSERT INTO Process_Fit "
						+ "VALUES (" + procId + ", '', '" + procType + "')";
				break;
			//Cut type	
			case 3:
				System.out.print("Enter the cut type: ");
				procType = sc.nextLine();
				System.out.print("Enter the machine type: ");
				machineType = sc.nextLine();
				sql2 = "INSERT INTO Process_Cut "
						+ "VALUES (" + procId + ", '', '" + procType + "', '" 
						+ machineType + "')";
				break;
			default:
				System.out.println("Incorrect type for process.");
			}
			//Two sql insertion querys for the main table and tables related.
			sql1 = "INSERT INTO Processes VALUES (" + procId + ",'')";
			
			//Now input department info. Insert into department and supervises table.
			System.out.print("Enter Department: ");
			int deptNum = sc.nextInt();
			sc.nextLine(); //buffer out.
			System.out.print("Enter Department data: ");
			String deptData = sc.nextLine();
			
			String sql3 = "INSERT INTO Department VALUES (" + deptNum + ",'" 
					+ deptData + "')";
			
			String sql4 = "INSERT INTO Supervises VALUES (" + deptNum + ", " 
			+ procId + ", '')"; 
					
			/*
			System.out.println(sql1); //debug
			System.out.println(sql2); //debug
			System.out.println(sql3); //debug
			System.out.println(sql4); //debug
			*/	
			Statement statement = connection.createStatement();
			statement.execute(sql1); //execute insert query.
			statement.execute(sql2); //execute insert query.
			statement.execute(sql3); //execute insert query.
			statement.execute(sql4); //execute insert query.
			System.out.println("Insertions Successful");
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
			Connection connection = DriverManager.getConnection(url); //sql connection.

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
			
			/*
			//debug
			System.out.println("Inputting following information...");
			System.out.println("Assembly Id: " + assId + "\nDate Ordered:" + dateOrdered
					+ "\nAssembly Details: " + details + "\nCustomer Name: " + custName);
			*/
			
			//Two sql insertion querys for the main table and tables related.
			String sql1 = "INSERT INTO Assembly VALUES (" + assId + ",'" 
					+ dateOrdered + "', '" + details + "')";
			
			String sql2 = "INSERT INTO Orders VALUES ('" + custName + "', " + assId + ")";
			//System.out.println(sql1); //debug
			//System.out.println(sql2); //debug
					
			Statement statement = connection.createStatement();
			statement.execute(sql1); //execute insert query.
			statement.execute(sql2); //execute insert query.
			System.out.println("Insertions Successful");
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
