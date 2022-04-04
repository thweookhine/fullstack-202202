<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Using IoC Container</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="container mt-4">
		<h1>Using IoC Container</h1>
		<h3>Add New Registration</h3>

		<div class="row">
			<div class="col-4">
				<c:url var="save" value="/registrations">
					<c:param name="classId" value="${openClass.id}"></c:param>
				</c:url>
				<form action="${save}" method="post">
					<div class="mb-3">
						<label for="" class="form-label">Student Name</label> <input
							class="form-control" required="required" type="text"
							placeholder="Enter Student Name" name="student" />

					</div>
					<div class="mb-3">
						<label for="" class="form-label mb-0">Phone</label> <input
							class="form-control" type="number"
							placeholder="Enter Phone" required="required"
							name="phone" />
					</div>

					<div class="mb-3">
						<label for="" class="form-label">Email</label> <input type="email"
							required="required" placeholder="Enter Student Email"
							class="form-control" name="email" />
					</div>
					
					<div>
						<input type="submit" class="btn btn-primary" value="Save Course" />
					</div>
				</form>
			</div>
		</div>

	</div>
</body>
</html>