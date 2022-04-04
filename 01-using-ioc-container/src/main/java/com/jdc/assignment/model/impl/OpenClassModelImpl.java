package com.jdc.assignment.model.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.domain.OpenClass;
import com.jdc.assignment.model.OpenClassModel;

public class OpenClassModelImpl implements OpenClassModel{
	
	private static final String SELECT_ALL = """
			select oc.id id, oc.start_date, oc.teacher, 
			c.id courseId, c.name, c.duration, c.fees, c.description
			from open_class oc join course c
			on oc.course_id = c.id
			where course_id = ?
			""";
	private static final String CREATE_SQL = """
			insert into open_class (course_id,start_date,teacher) values (?,?,?)
			""";
	private static final String SELECT_ONE = """
			select oc.id id, oc.start_date, oc.teacher, 
			c.id courseId, c.name, c.duration, c.fees, c.description
			from open_class oc join course c
			on c.id = oc.course_id
			where oc.id = ?
			""";
	private DataSource dataSource;
	
	public OpenClassModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<OpenClass> findByCourse(int courseId) {
		
		var list = new ArrayList<OpenClass>();
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(SELECT_ALL)){
			stmt.setInt(1, courseId);
			var rs = stmt.executeQuery();
			while(rs.next()) {
				var c = new Course();
				c.setId(rs.getInt("courseId"));
				c.setName(rs.getString("name"));
				c.setDuration(rs.getInt("duration"));
				c.setFees(rs.getInt("fees"));
				c.setDescription(rs.getString("description"));
				
				var openClass = new OpenClass();
				openClass.setId(rs.getInt("id"));
				openClass.setCourse(c);
				openClass.setDate(rs.getDate("start_date").toLocalDate());
				openClass.setTeacher(rs.getString("teacher"));
				
				list.add(openClass);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void create(OpenClass openClass) {
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(CREATE_SQL)){
			stmt.setInt(1, openClass.getCourse().getId());
			stmt.setDate(2, Date.valueOf(openClass.getDate()));
			stmt.setString(3, openClass.getTeacher());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public OpenClass findById(int id) {
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(SELECT_ONE)){
			
			stmt.setInt(1, id);
			var rs = stmt.executeQuery();
			while(rs.next()) {
				
				var course = new Course();
				course.setId(rs.getInt("courseId"));
				course.setName(rs.getString("name"));
				course.setDuration(rs.getInt("duration"));
				course.setDescription(rs.getString("description"));
				
				var openClass = new OpenClass();
				openClass.setId(rs.getInt("id"));
				openClass.setCourse(course);
				openClass.setDate(rs.getDate("start_date").toLocalDate());
				openClass.setTeacher(rs.getString("teacher"));
				
				return openClass;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
