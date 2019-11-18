<%@ page language="java" contentType="text/html; charset=UTF8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Data Insert</title>
</head>
	<body>
		<%@page import="Project_Web.DataHandler"%>
		<%@page import="java.sql.ResultSet"%>
		<%@page import="java.sql.Array"%>
		<%
		// The handler is the one in charge of establishing the connection.
		DataHandler handler = new DataHandler();
		// Get the attribute values passed from the input form.
		String name = request.getParameter("cust_name");
		String address = request.getParameter("address");
		String category = request.getParameter("category");
		
		/*
		* If the user hasn't filled out all the time, movie name and duration. This is very simple
		checking.
		*/
		if (name.equals("") || address.equals("") || category.equals("")) 
		{
			response.sendRedirect("Add_Customer_Form.jsp");
		} 
		else 
		{
			// Now perform the query with the data from the form.
			boolean success = handler.addCustomer(name, address, category);
			if (!success) 
			{ // Something went wrong
				%>
				<h2>There was a problem inserting the customer</h2>
			<%
			} 
			else 
			{ // Confirm success to the user
				%>
				<h2>The Customer Info:</h2>
				<ul>
				<li>Customer Name: <%=name%></li>
				<li>Address: <%=address%></li>
				<li>Category: <%=category%></li>
				</ul>
				<h2>Was successfully inserted.</h2>
				<a href="Get_Customer_Form.jsp">See Customers.</a>
				<%
			}
		}
		
		%>
	</body>
</html>