package com.kovzan.task_manager.command.impl.task;

import static com.kovzan.task_manager.logger.Log.logger;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;

import com.kovzan.task_manager.command.ValidationException;
import com.kovzan.task_manager.command.impl.parameters.TaskParams;
import com.kovzan.task_manager.entity.Task;
import com.kovzan.task_manager.entity.TaskStatus;
import com.kovzan.task_manager.logger.LogConstant;

public class TaskUtils {
	
	private static final String INCORRECT_DATA_MESSAGE = "Incorrect data are entered.";
	private static final String DATES_ARE_NOT_IN_RANGE = "Begin date should be earlier then end date.";
	
	public static Task buildTask(HttpServletRequest request) throws ValidationException {
		
		Task task = new Task();
		HashMap<String, String> invalidFields = new HashMap<>();
		Integer id;
		if (request.getParameter(TaskParams.TASK_ID) != null) {
			id = Integer.parseInt(request.getParameter(TaskParams.TASK_ID));
			task.setId(id);
		}
		String name = request.getParameter(TaskParams.TASK_NAME);
		if (TaskValidator.isTaskNameValid(name)) {
			task.setName(name);
		} else {
			invalidFields.put(TaskParams.TASK_NAME, name);
		}
		String work = request.getParameter(TaskParams.TASK_WORK);
		if (TaskValidator.isTaskWorkValid(work)) {
			task.setWork(Integer.parseInt(work));
		} else {
			invalidFields.put(TaskParams.TASK_WORK, String.valueOf(work));
		}
		String beginDate = request.getParameter(TaskParams.TASK_BEGINDATE);
		boolean isBeginDateValid = true;
		if (TaskValidator.isTaskDateValid(beginDate) && TaskValidator.isTaskDateInRange(beginDate)) {
			task.setBeginDate(LocalDate.parse(beginDate));
		} else {
			invalidFields.put(TaskParams.TASK_BEGINDATE, beginDate);
			isBeginDateValid = false;
		}
		String endDate = request.getParameter(TaskParams.TASK_ENDDATE);
		boolean isEndDateValid = true;
		if (TaskValidator.isTaskDateValid(endDate) && TaskValidator.isTaskDateInRange(endDate)) {
			task.setEndDate(LocalDate.parse(endDate));
		} else {
			invalidFields.put(TaskParams.TASK_ENDDATE, endDate);
			isEndDateValid = false;
		}
		if (isBeginDateValid && isEndDateValid) {
			if (!TaskValidator.areTaskDatesValid(LocalDate.parse(beginDate), LocalDate.parse(endDate))) {
				task.setBeginDate(null);
				invalidFields.put(TaskParams.TASK_BEGINDATE, beginDate);
				invalidFields.put(TaskParams.TASK_INVALID_DATE_RANGE, beginDate);
			}
		}
		task.setProjectId(Integer.parseInt(request.getParameter(TaskParams.TASK_PROJECT_ID)));
		task.setEmployeeId(Integer.parseInt(request.getParameter(TaskParams.TASK_EMPLOYEE_ID)));
		task.setStatus(TaskStatus.valueOf(request.getParameter(TaskParams.TASK_STATUS)));
		if(!invalidFields.isEmpty()) {
			throw new ValidationException(INCORRECT_DATA_MESSAGE, task, invalidFields);
		}
		logger.log(Level.INFO, LogConstant.OBJECT_CREATED + task.toString());
		return task;
	}

}
