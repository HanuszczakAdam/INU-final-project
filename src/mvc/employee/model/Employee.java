package mvc.employee.model;

import java.time.LocalDate;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {

	private IntegerProperty employeeId;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty email;
	private StringProperty phone;
	private ObjectProperty<LocalDate> hireDate;

	private StringProperty jobId;
	private IntegerProperty salary;
	private IntegerProperty managerId;
	private IntegerProperty departmentId;

	private StringProperty managerName;
	private StringProperty departmentName;
	private StringProperty jobTitle;

	public Employee() {
		employeeId = new SimpleIntegerProperty(0);
		firstName = new SimpleStringProperty("");
		lastName = new SimpleStringProperty("");
		email = new SimpleStringProperty("");
		phone = new SimpleStringProperty("");
		employeeId = new SimpleIntegerProperty(0);
		jobId = new SimpleStringProperty("");
		salary = new SimpleIntegerProperty(0);
		managerId = new SimpleIntegerProperty(0);
		departmentId = new SimpleIntegerProperty(0);
		hireDate = new SimpleObjectProperty<LocalDate>(LocalDate.now());
		managerName = new SimpleStringProperty("");
		departmentName = new SimpleStringProperty("");
		jobTitle = new SimpleStringProperty("");
	}

	public Employee(int employeeId) {
		this();
		this.employeeId.set(employeeId);
	}

	public int getEmployeeId() {
		return this.employeeId.get();
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId.set(employeeId);
	}

	public IntegerProperty employeeIdProperty() {
		return employeeId;
	}

	public String getLastName() {
		return lastName.get();
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public String getFirstName() {
		return firstName.get();
	}

	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public StringProperty emailProperty() {
		return email;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public StringProperty phoneProperty() {
		return phone;
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(String phone) {
		this.phone.set(phone);
	}

	public String getJobId() {
		return this.jobId.get();
	}

	public void setJobId(String jobId) {
		this.jobId.set(jobId);
	}

	public StringProperty jobIdProperty() {
		return jobId;
	}

	public int getSalary() {
		return this.salary.get();
	}

	public void setSalary(int salary) {
		this.salary.set(salary);
	}

	public IntegerProperty salaryProperty() {
		return salary;
	}

	public int getManagerId() {
		return this.managerId.get();
	}

	public void setManagerId(int managerId) {
		this.managerId.set(managerId);
	}

	public IntegerProperty managerIdProperty() {
		return managerId;
	}

	public int getDepartmentId() {
		return this.departmentId.get();
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId.set(departmentId);
	}

	public IntegerProperty departmentIdProperty() {
		return departmentId;
	}

	public LocalDate getHireDate() {
		return this.hireDate.get();
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate.set(hireDate);
	}

	public ObjectProperty hireDateProperty() {
		return hireDate;
	}

	public StringProperty managerNameProperty() {
		return managerName;
	}

	public String getManagerName() {
		return managerName.get();
	}

	public void setManagerName(String managerName) {
		this.managerName.set(managerName);
	}

	public StringProperty departmentNameProperty() {
		return departmentName;
	}

	public String getDepartmentName() {
		return departmentName.get();
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName.set(departmentName);
	}

	public StringProperty jobTitleProperty() {
		return jobTitle;
	}

	public String getJobTitle() {
		return jobTitle.get();
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle.set(jobTitle);
	}

	@Override
	public String toString() {
		return getFirstName() + " " + getLastName();
	}

	public void copyFrom(Employee emp) {
		this.setEmployeeId(emp.getEmployeeId());
		this.setDepartmentId(emp.getDepartmentId());
		this.setManagerId(emp.getManagerId());
		this.setJobId(emp.getJobId());		
		
		this.setFirstName(emp.getFirstName());
		this.setLastName(emp.getLastName());
		this.setEmail(emp.getEmail());
		this.setPhone(emp.getPhone());
		this.setSalary(emp.getSalary());
		this.setHireDate(emp.getHireDate());
		this.setManagerName(emp.getManagerName());
		this.setJobTitle(emp.getJobTitle());
		this.setDepartmentName(emp.getDepartmentName());

	}
	
	@Override
	public boolean equals(Object ob) {
		if (ob==null) {
			return false;
		} else {
			Employee emp = (Employee)ob;
			return emp.getEmployeeId() == employeeId.get();
		}
	}

}
