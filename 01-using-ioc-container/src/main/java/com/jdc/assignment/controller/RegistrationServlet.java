package com.jdc.assignment.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.OpenClassModel;
import com.jdc.assignment.model.RegistrationModel;

@WebServlet(urlPatterns = {
		"/registrations",
		"/registration-edit"
})
public class RegistrationServlet extends AbstractBeanFactoryServlet{

	private static final long serialVersionUID = 1L;
	
	private RegistrationModel model;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var classId = req.getParameter("classId");
		
		//Find Class 
		var openClass = getBean("openClassModel", OpenClassModel.class).findById(Integer.parseInt(classId));
		req.setAttribute("openClass", openClass);
		
		var page = switch(req.getServletPath()) {
		case "/registrations" -> {
			//Load Registrations of class
			model = getBean("registrationModel", RegistrationModel.class);
			req.setAttribute("registrations", model.findByClass(Integer.parseInt(classId)));
			
			yield "registrations";
		}
		default -> "registration-edit";
		};
		
		getServletContext().getRequestDispatcher("/%s.jsp".formatted(page)).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		var classId = Integer.parseInt(req.getParameter("classId"));
		
		//Create Registration Object
		var registration = new Registration();
		registration.setStudent(req.getParameter("student"));
		registration.setPhone(req.getParameter("phone"));
		registration.setEmail(req.getParameter("email"));
		registration.setOpenClass(getBean("openClassModel", OpenClassModel.class).findById(classId));
		
		//Save to Registration Model
		getBean("registrationModel", RegistrationModel.class).save(registration);
		
		//Redirect
		resp.sendRedirect("/registrations?classId=%d".formatted(classId));
	}

}
