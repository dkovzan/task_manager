package com.kovzan.task_manager.validator;

import com.kovzan.task_manager.entities.Task;

import java.time.LocalDate;

public class TaskValidator {

	public static boolean validateDates(LocalDate earlierDate, LocalDate laterDate) {
		return !laterDate.isBefore(earlierDate);
	}

	public static boolean validateTask(Task task) {
		return validateDates(task.getCreatedOn(), task.getFinishedOn()) && task.getName() != null && task.getEstimate() != 0;
	}

	public static Task getTaskWithValidFields(Task task) {
		Task taskWithValidFields = task;
		if(!validateDates(task.getCreatedOn(), task.getFinishedOn())) {
			taskWithValidFields.setCreatedOn(null);
			taskWithValidFields.setFinishedOn(null);
		}
		return taskWithValidFields;
	}
}
