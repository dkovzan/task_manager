package com.kovzan.task_manager.command.impl.task;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TaskValidator {

	private static final String CHECK_TASK_NAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_TASK_WORK_REGEX = "\\d{1,8}";
	private static final LocalDate MIN_DATE = LocalDate.of(1900, 1, 1);
	private static final LocalDate MAX_DATE = LocalDate.of(9999, 12,31);
	

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
	
	public static boolean isTaskDateInRange(String date) {
		if (isTaskDateValid(date)) {
			LocalDate validDate = LocalDate.parse(date);
			return (!validDate.isBefore(MIN_DATE) && !validDate.isAfter(MAX_DATE));
		} else {
			return false;
		}
	}

	public static boolean areTaskDatesValid(LocalDate earlierDate, LocalDate laterDate) {
		return (!laterDate.isBefore(earlierDate));
	}
}
