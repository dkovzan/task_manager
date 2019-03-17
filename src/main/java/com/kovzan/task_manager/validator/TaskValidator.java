package com.kovzan.task_manager.validator;

import com.kovzan.task_manager.entity.Task;

import java.time.LocalDate;

public class TaskValidator {

	public static boolean validateTaskDates(LocalDate earlierDate, LocalDate laterDate) {
		return !laterDate.isBefore(earlierDate);
	}

	public static boolean validateTask(Task task) {
		return validateTaskDates(task.getCreatedOn(), task.getFinishedOn());
	}

	public static Task getTaskWithValidFields(Task task) {
		Task taskWithValidFields = task;
		if(!validateTaskDates(task.getCreatedOn(), task.getFinishedOn())) {
			taskWithValidFields.setCreatedOn(null);
			taskWithValidFields.setFinishedOn(null);
		}
		return taskWithValidFields;
	}
}
