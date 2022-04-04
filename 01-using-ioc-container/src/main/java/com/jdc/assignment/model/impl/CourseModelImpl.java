package com.jdc.assignment.model.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.jdc.assignment.domain.Course;
import com.jdc.assignment.model.CourseModel;

public class CourseModelImpl implements CourseModel{

	private static final String SELECT_ALL = "select * from course";
	private static final String INSERT = "insert into course(name,duration,fees,description) values (?,?,?,?)";
	private static final String SELECT_ONE = "select * from course where id = ?";
	private DataSource dataSource;
	
	public CourseModelImpl(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}

	@Override
	public List<Course> getAll() {
		
		var list = new ArrayList<Course>();
		
		try(var con = dataSource.getConnection();
				var stmt = con.prepareStatement(SELECT_ALL)){
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				var course = new Course();
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setDuration(rs.getInt("duration"));
				course.setFees(rs.getInt("fees"));
				course.setDescription(rs.getString("description"));
				
				list.add(course);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void save(Course course) {
		
		try(var conn = dataSource.getConnection();
				var stmt = conn.prepareStatement(INSERT)){
			
			stmt.setString(1, course.getName());
			stmt.setInt(2, course.getDuration());
			stmt.setInt(3, course.getFees());
			stmt.setString(4, course.getDescription());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Course findById(int id) {
		
		try(var conn = dataSource.getConnection(); 
				var stmt = conn.prepareStatement(SELECT_ONE) ){
			
			
			stmt.setInt(1, id);
			var rs = stmt.executeQuery();
			while(rs.next()) {
				var course = new Course();
				
				course.setId(rs.getInt("id"));
				course.setName(rs.getString("name"));
				course.setDuration(rs.getInt("duration"));
				course.setFees(rs.getInt("fees"));
				course.setDescription(rs.getString("description"));
				return course;
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
