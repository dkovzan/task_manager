package com.kovzan.task_manager.validator;

import com.kovzan.task_manager.entity.Task;

import java.time.LocalDate;

public class TaskValidator {

	public static final String CHECK_TASK_NAME_REGEX = "[\\D\\d]{1,255}";
	public static final String CHECK_TASK_ESTIMATE_REGEX = "\\d{1,8}";

	public static boolean validateTaskName(String name) {
		return name.matches(CHECK_TASK_NAME_REGEX);
	}

	public static boolean validateTaskEstimate(Integer estimate) {
		return String.valueOf(estimate).matches(CHECK_TASK_ESTIMATE_REGEX);
	}

	public static boolean validateTaskDates(LocalDate earlierDate, LocalDate laterDate) {
		return (!laterDate.isBefore(earlierDate));
	}

	public static boolean validateTask(Task task) {
		return (validateTaskDates(task.getBeginDate(), task.getEndDate())
				&& validateTaskName(task.getName()) && validateTaskEstimate(task.getWork()));
	}

	public static Task getTaskWithValidFields(Task task) {
		Task taskWithValidFields = task;
		if (!validateTaskDates(task.getBeginDate(), task.getEndDate())) {
			taskWithValidFields.setBeginDate(null);
			taskWithValidFields.setEndDate(null);
		}
		if (!validateTaskName(task.getName())) {
			taskWithValidFields.setName(null);
		}
		if (!validateTaskEstimate(task.getWork())) {
			taskWithValidFields.setWork(null);
		}
		return taskWithValidFields;
	}
}
