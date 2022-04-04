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
		<h1>Class For ${course.name}</h1>

		<div class="mb-4 mt-4">
			<c:url var="addNew" value="/class-edit">
				<c:param name="courseId" value="${course.id}"></c:param>
			</c:url>
			<a href="${addNew}" class="btn btn-primary">Add New Class</a>
		</div>

		<c:choose>
			<c:when test="${empty classes}">
				<div class="alert alert-warning">There is no class for
					${course.name}. Please create new class.</div>
			</c:when>
			<c:otherwise>
				<table class="table table-striped">
					<thead>
						<tr>
							<th>ID</th>
							<th>Course</th>
							<th>Teacher</th>
							<th>Start Date</th>
							<th>Fees</th>
							<th>Duration</th>
							<th>Description</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${classes}">
							<tr>
								<td>${c.id}</td>
								<td>${c.course.name}</td>
								<td>${c.teacher}</td>
								<td>${c.date}</td>
								<td>${c.course.fees}</td>
								<td>${c.course.duration} Months</td>
								<td>${c.course.description}</td>
								<td>
									<c:url var="registrations" value="/registrations">
										<c:param name="classId" value="${c.id}"></c:param>
									</c:url>
									<a href="${registrations}">Registrations</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>


</body>
</html>