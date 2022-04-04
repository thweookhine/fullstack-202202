package com.jdc.assignment.model.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.spi.RegisterableService;
import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.domain.Registration;
import com.jdc.assignment.model.RegistrationModel;

public class RegistrationModelImpl implements RegistrationModel{

	private static final String SELECT_SQL = """
			select r.id id ,r.student, r.phone, r.email,
			oc.id open_class_id, oc.start_date, oc.teacher,
			c.id course_id, c.name, c.fees, c.duration, c.description
			from registration r join open_class oc 
			on r.open_class_id = oc.id
			join course c 
			on c.id = oc.course_id
			where open_class_id = ?
			""";
	private static final String INSERT_SQL = "insert into registration (open_class_id,student,phone,email) values (?,?,?,?)";
	private DataSource dataSource;
	
	public RegistrationModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Registration> findByClass(int id) {
		var list = new ArrayList<Registration>();
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(SELECT_SQL)){
			stmt.setInt(1, id);
			
			var rs = stmt.executeQuery();
			while(rs.next()) {
				
				//Course Object
				var course = new Course();
				course.setId(rs.getInt("course_id"));
				course.setName(rs.getString("name"));
				course.setFees(rs.getInt("fees"));
				course.setDuration(rs.getInt("duration"));
				course.setDescription(rs.getString("description"));
				
				//OpenClass Object
				var openClass = new OpenClass();
				openClass.setId(rs.getInt("open_class_id"));
				openClass.setCourse(course);
				openClass.setDate(rs.getDate("start_date").toLocalDate());
				openClass.setTeacher(rs.getString("teacher"));
				
				//Registration Object
				var registration = new Registration();
				registration.setId(rs.getInt("id"));
				registration.setOpenClass(openClass);
				registration.setStudent(rs.getString("student"));
				registration.setPhone(rs.getString("phone"));
				registration.setEmail(rs.getString("email"));
				
				list.add(registration);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void save(Registration registration) {
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(INSERT_SQL)){
			
			stmt.setInt(1, registration.getOpenClass().getId());
			stmt.setString(2, registration.getStudent());
			stmt.setString(3, registration.getPhone());
			stmt.setString(4, registration.getEmail());
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	
}
