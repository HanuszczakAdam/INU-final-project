package mvc.employee;

import mvc.employee.model.Employee;

public interface IEmployeesOperations {
	
	void refreshEmployees();
	
	void updateEmployee(Employee emp);
	
	void deleteEmployee(int empId);
	
	void addEmployee(Employee emp);
	
	boolean showEmployeeEditor(Employee emp);

}
