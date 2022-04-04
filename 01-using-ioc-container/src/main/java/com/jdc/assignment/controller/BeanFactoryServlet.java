package com.jdc.assignment.controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;

public interface BeanFactoryServlet{

	<T> T getBean(String name, Class<T> requiredType) throws BeansException;
}
