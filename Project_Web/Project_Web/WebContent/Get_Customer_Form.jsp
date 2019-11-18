<!DOCTYPE html>
	<html>
	<head>
		<meta charset="UTF-8">
		<title>Search Customers</title>
	</head>
	<body>
	<h2>Search Customers by Category</h2>
	<!--
	Form for collecting user input for the new movie_night record.
	Upon form submission, add_movie.jsp file will be invoked.
	-->
		<form action="GetCustomers.jsp">
		<!-- The form organized in an HTML table for better clarity. -->
	<table border=1>
	<tr>
		<th colspan="2">Enter the Customer Categories:</th>
	</tr>
	<tr>
		<td>Low Category:</td>
		<td><div style="text-align: center;">
		<input type=text name=low_category>
		</div></td>
	</tr>
	<tr>
		<td>High Category:</td>
		<td><div style="text-align: center;">
		<input type=text name=high_category>
		</div></td>
	</tr>
	<tr>
		<td><div style="text-align: center;">
		<input type=reset value=Clear>
	</div></td>
		<td><div style="text-align: center;">
		<input type=submit value=Search>
		</div></td>
	</tr>
	</table>
</form>
</body>
</html>