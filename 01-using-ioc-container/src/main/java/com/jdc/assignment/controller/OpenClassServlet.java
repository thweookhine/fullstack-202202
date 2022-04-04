package com.jdc.assignment.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.model.CourseModel;
import com.jdc.assignment.model.OpenClassModel;

@WebServlet(urlPatterns = {
		"/classes",
		"/class-edit",
})
public class OpenClassServlet extends AbstractBeanFactoryServlet{

	private static final long serialVersionUID = 1L;
	private OpenClassModel model;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var courseId =req.getParameter("courseId");
		
		//Find Course
		var course = getBean("courseModel", CourseModel.class).findById(Integer.parseInt(courseId));
		req.setAttribute("course", course);
		
		var page = switch(req.getServletPath()) {
		case "/classes" -> {
			model = getBean("openClassModel", OpenClassModel.class);
			req.setAttribute("classes", model.findByCourse(Integer.parseInt(courseId)));
			yield "classes";
		}
		default -> "class-edit";
		};
		
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//Get Parameters from request
		var courseId = Integer.parseInt(req.getParameter("courseId"));
		var startDate = req.getParameter("startDate");
		var teacher = req.getParameter("teacher");
		
		//Get OpenClassModel Bean
		model = getBean("openClassModel", OpenClassModel.class);
		
		//Create OpenClass Object
		var openClass= new OpenClass();
		openClass.setCourse(getBean("courseModel", CourseModel.class).findById(courseId));
		openClass.setDate(LocalDate.parse(startDate));
		openClass.setTeacher(teacher);
		
		//add openclass object to model
		model.create(openClass);
		
		//Redirect
		resp.sendRedirect("/classes?courseId=%d".formatted(courseId));
		
	}

}
