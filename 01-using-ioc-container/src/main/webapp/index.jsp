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
		<h1>Using IoC Container</h1>

		<h3>Courses</h3>

		<div>
			<a class="btn btn-primary" href="course-edit">Add New Course</a>
		</div>

		<div class="mt-4">
			<c:choose>
				<c:when test="${empty courses}">
					<div class="alert alert-warning">There is no course.Please
						Create New Course</div>
				</c:when>
				<c:otherwise>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Name</th>
								<th>Duration</th>
								<th>Fees</th>
								<th>Description</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="course" items="${courses }">
								<tr>
									<td>${course.id}</td>
									<td>${course.name }</td>
									<td>${course.duration}Months</td>
									<td>${course.fees }</td>
									<td>${course.description }</td>
									<td>
										<c:url var="classes" value="/classes">
											<c:param name="courseId" value="${course.id }"></c:param>
										</c:url>
										<a href="${classes}">Open Classes</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:otherwise>
			</c:choose>
		</div>



	</div>
</body>
</html>