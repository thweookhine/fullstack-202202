package com.jdc.project.model.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jdc.project.model.dto.Project;
import com.jdc.project.model.service.utils.ProjectHelper;

@Service
public class ProjectService {
	
	@Autowired
	private SimpleJdbcInsert projectInsert;
	@Autowired
	private NamedParameterJdbcTemplate template;
	@Autowired
	private ProjectHelper projectHelper;
	private RowMapper<Project> rowMapper;
	
	public ProjectService() {
		rowMapper = new BeanPropertyRowMapper<>(Project.class);
	}
	
	public int create(Project project) {
		projectHelper.validate(project);
		return projectInsert.executeAndReturnKey(projectHelper.insertParams(project)).intValue();
	}

	public Project findById(int id) {
		String sql = """
				select p.id, p.name, p.description, p.manager managerId, p.start startDate, p.months,
				m.name managerName
				from project p inner join member m 
				on p.manager = m.id
				where p.id = :id
				""";
		
		return template.queryForObject(sql, Map.of("id",id), rowMapper);
	}

	public List<Project> search(String project, String manager, LocalDate dateFrom, LocalDate dateTo) {
		
		var sb = new StringBuffer("""
				select p.id, p.name, p.description, p.manager managerId, p.start startDate, p.months,
				m.name managerName  
				from project p inner join member m
				on p.manager = m.id
				where 1 = 1
				""");
		Map<String, Object> params = new HashMap<>();
		
		if(StringUtils.hasLength(project)) {
			sb.append(" and lower(p.name) like :project");
			params.put("project", project.toLowerCase().concat("%"));
		}
		if(StringUtils.hasLength(manager)) {
			sb.append(" and lower(m.name) like lower(:manager)");
			params.put("manager", manager.concat("%"));
		}
		if(null != dateFrom) {
			sb.append(" and start >= :dateFrom");
			params.put("dateFrom", Date.valueOf(dateFrom));
		}
		if(null != dateTo) {
			sb.append(" and start <= :dateTo");
			params.put("dateTo", Date.valueOf(dateTo));
		}
		return template.query(sb.toString(), params, rowMapper);
	}

	public int update(int id, String name, String description, LocalDate startDate, int month) {
		
		var sql = """
				update project set name = :name, description = :description,
				start = :startDate , months = :month where id = :id
				""";
		var params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("name", name);
		params.put("description", description);
		params.put("startDate", startDate);
		params.put("month",month);
		return template.update(sql, params);
	}

	public int deleteById(int id) {
		var sql = "delete from project where id = :id";
		return template.update(sql, Map.of("id",id));
	}

}
