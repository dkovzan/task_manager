package com.kovzan.task_manager.command.impl.task;

import com.kovzan.task_manager.entity.Task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TaskValidator {

	public static final String CHECK_TASK_NAME_REGEX = "[\\D\\d]{1,255}";
	public static final String CHECK_TASK_WORK_REGEX = "\\d{1,8}";

	public static boolean isTaskNameValid(String name) {
		return name.matches(CHECK_TASK_NAME_REGEX);
	}

	public static boolean isTaskWorkValid(String work) {
		return work.matches(CHECK_TASK_WORK_REGEX);
	}
	
	public static boolean isTaskDateValid(String date) {
		try {
			LocalDate.parse(date);
		} catch (DateTimeParseException  e) {
			return false;
		}
		return true;
	}

	public static boolean areTaskDatesValid(LocalDate earlierDate, LocalDate laterDate) {
		return (!laterDate.isBefore(earlierDate));
	}

	public static boolean isTaskValid(Task task) {
		return (areTaskDatesValid(task.getBeginDate(), task.getEndDate())
				&& isTaskNameValid(task.getName()) && isTaskWorkValid(String.valueOf(task.getWork())));
	}

	public static Task getTaskWithValidFields(Task task) {
		Task taskWithValidFields = task;
		if (!areTaskDatesValid(task.getBeginDate(), task.getEndDate())) {
			taskWithValidFields.setBeginDate(null);
			taskWithValidFields.setEndDate(null);
		}
		if (!isTaskNameValid(task.getName())) {
			taskWithValidFields.setName(null);
		}
		if (!isTaskWorkValid(String.valueOf(task.getWork()))) {
			taskWithValidFields.setWork(null);
		}
		return taskWithValidFields;
	}
}
