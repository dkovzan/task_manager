package com.kovzan.task_manager.model;

import com.kovzan.task_manager.entities.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Model {

    private static Model instance = new Model();

    private List<Employee> model;

    public static Model getInstance() {
        return instance;
    }

    private Model() {
        model = new ArrayList<Employee>();
    }

    public void add(Employee employee) {
        model.add(employee);
    }

    public List<String> list() {
        return model.stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
    }


}
