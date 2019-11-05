CREATE TABLE Assembly
(
	assembly_id int PRIMARY KEY,
	date_ordered date,
	details varchar(128)
);

CREATE TABLE Customers
(
	cust_name varchar(32) PRIMARY KEY,
	address varchar(40),
	category int,
	CHECK (category>0),
	CHECK (category<11)
);

CREATE TABLE Department
(
	dept_num int PRIMARY KEY,
	dept_data varchar(128)
);

CREATE TABLE Processes
(
	process_id int PRIMARY KEY,
	process_data varchar(128)
);

CREATE TABLE Process_Paint
(
	process_id int PRIMARY KEY,
	process_data varchar(128),
	paint_type varchar(32),
	method varchar(32)
);

CREATE TABLE Process_Fit
(
	process_id int PRIMARY KEY,
	process_data varchar(128),
	fit_type varchar(32)
);

CREATE TABLE Process_Cut
(
	process_id int PRIMARY KEY,
	process_data varchar(128),
	cut_type varchar(32),
	machine_type varchar(32)
);

CREATE TABLE Job
(
	job_no int PRIMARY KEY,
	date_started date,
	date_completed date,
	CHECK (date_completed >= date_started)
);

CREATE TABLE Job_Cut
(
	job_no int PRIMARY KEY,
	date_started date,
	date_completed date,
	mcahine_type varchar(32),
	time_used int,
	material_used varchar(32),
	labor_time int,
	CHECK (date_completed >= date_started),
	CHECK (labor_time > 0),
	CHECK (time_used > 0)
);

CREATE TABLE Job_Paint
(
	job_no int PRIMARY KEY,
	date_started date,
	date_completed date,
	color varchar(32),
	volume int,
	labor_time int,
	CHECK (date_completed >= date_started),
	CHECK (volume >= 0),
	CHECK (labor_time >= 0)
);

CREATE TABLE Job_Fit
(
	job_no int PRIMARY KEY,
	date_started date,
	date_completed date,
	labor_time int,
	CHECK (date_completed >= date_started),
	CHECK (labor_time >= 0)
);

CREATE TABLE [Transaction]
(
	trans_no int PRIMARY KEY,
	sup_cost float,
	CHECK (sup_cost >= 0)
);

CREATE TABLE Account
(
	acc_num int PRIMARY KEY,
	date_created date DEFAULT GETDATE()
);

CREATE TABLE Account_Assembly
(
	acc_num int PRIMARY KEY,
	date_created date DEFAULT GETDATE(),
	cost float DEFAULT 0, 
	CHECK (cost >= 0),
);

CREATE TABLE Account_Department
(
	acc_num int PRIMARY KEY,
	date_created date DEFAULT GETDATE(),
	cost float DEFAULT 0, 
	CHECK (cost >= 0),
);

CREATE TABLE Account_Process
(
	acc_num int PRIMARY KEY,
	date_created date DEFAULT GETDATE(),
	cost float DEFAULT 0, 
	CHECK (cost >= 0),
);

CREATE TABLE Orders
(
	cust_name varchar(32) PRIMARY KEY,
	assembly_id int FOREIGN KEY REFERENCES Assembly(assembly_id)
);

CREATE TABLE Manufactures
(
	assembly_id int PRIMARY KEY,
	process_id int FOREIGN KEY REFERENCES Processes(process_id),
	job_no int
);

CREATE TABLE Assigned 
(
	process_id int PRIMARY KEY,
	job_no int FOREIGN KEY REFERENCES Job(job_no)
);

CREATE TABLE Supervises
(
	dept_num int PRIMARY KEY,
	process_id int FOREIGN KEY REFERENCES Processes(process_id),
	job_no int 
);

CREATE TABLE Records 
(
	job_no int PRIMARY KEY,
	trans_no int FOREIGN KEY REFERENCES [Transaction](trans_no)
);

CREATE TABLE Updates
(
	trans_no int PRIMARY KEY,
	acc_num int FOREIGN KEY REFERENCES Account(acc_num)
);

CREATE TABLE Has1
(
	assembly_id int PRIMARY KEY,
	acc_num int FOREIGN KEY REFERENCES Account(acc_num)
);

CREATE TABLE Has2
(
	process_id int PRIMARY KEY,
	acc_num int FOREIGN KEY REFERENCES Account(acc_num)
);

CREATE TABLE Has3
(
	dept_num int PRIMARY KEY,
	acc_num int FOREIGN KEY REFERENCES Account(acc_num)
);