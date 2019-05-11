package com.kovzan.task_manager.command.impl.project;

public class ProjectValidator {
	
	private static final String CHECK_PROJECT_NAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_PROJECT_SHORTNAME_REGEX = "[\\D\\d]{1,255}";
	private static final String CHECK_PROJECT_DESCRIPTION_REGEX = "[\\D\\d]{0,4000}";
	
	public static boolean isProjectNameValid(String name) {
		return name.matches(CHECK_PROJECT_NAME_REGEX);
	}
	
	public static boolean isProjectShortNameValid(String shortName) {
		return shortName.matches(CHECK_PROJECT_SHORTNAME_REGEX);
	}
	
	public static boolean isProjectDescriptionValid(String description) {
		return description.matches(CHECK_PROJECT_DESCRIPTION_REGEX);
	}

}
