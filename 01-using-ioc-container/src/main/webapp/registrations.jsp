<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<h3>Registrations</h3>

		<div class="mb-4 mt-4">
			<c:url var="addNewRegistration" value="/registration-edit">
				<c:param name="classId" value="${openClass.id}"></c:param>
			</c:url>
			<a href="${addNewRegistration}" class="btn btn-primary">Add New
				Registration</a>
		</div>

		<c:choose>
			<c:when test="${empty registrations }">
				<div class="alert alert-warning">There is no registration for
					this class.</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Id</th>
							<th>Student</th>
							<th>Phone</th>
							<th>Email</th>
							<th>Course</th>
							<th>Start Date</th>
							<th>Teacher</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="r" items="${registrations}">
							<tr>
								<td>${r.id}</td>
								<td>${r.student}</td>
								<td>${r.phone}</td>
								<td>${r.email}</td>
								<td>${r.openClass.course.name}</td>
								<td>${r.openClass.date}</td>
								<td>${r.openClass.teacher}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>


	</div>

</body>
</html>