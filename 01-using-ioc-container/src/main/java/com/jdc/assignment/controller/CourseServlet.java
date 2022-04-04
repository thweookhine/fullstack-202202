package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.listener.SpringContainerManager;
import com.jdc.assignment.model.CourseModel;

@WebServlet(urlPatterns = {
		"/",
		"/courses",
		"/course-edit",
		"/course-save"
})
public class CourseServlet extends AbstractBeanFactoryServlet{
 
	private static final long serialVersionUID = 1L;
	private CourseModel model;
	
	@Override
	public void init() throws ServletException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		var page = switch(req.getServletPath()) {
		case "/course-edit" -> "/course-edit.jsp";
		default -> {
			//Load course and set result to request scope
			var model = getBean("courseModel", CourseModel.class);
			req.setAttribute("courses", model.getAll());
			yield "/index.jsp";
		}
		};
		getServletContext().getRequestDispatcher(page).forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//Creat Course Object
		var course = new Course();
		course.setName(req.getParameter("name"));
		course.setDuration(Integer.parseInt(req.getParameter("duration")));
		course.setFees(Integer.parseInt(req.getParameter("fees")));
		course.setDescription(req.getParameter("description"));
		
		//Save To Database
		getBean("courseModel", CourseModel.class).save(course);
		
		//redirect to Top Page
		resp.sendRedirect("/");
	}

}
