package mvc.employee.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Department {

	private IntegerProperty departmentId;
	private StringProperty departmentName;
	private IntegerProperty managerId;
	private IntegerProperty locationId;

	public Department() {
		departmentId = new SimpleIntegerProperty(0);
		departmentName = new SimpleStringProperty("");
		managerId = new SimpleIntegerProperty(0);
		locationId = new SimpleIntegerProperty(0);
	}

	public Department(int departmentId) {
		this();
		this.departmentId.set(departmentId);
	}

	@Override
	public String toString() {
		return departmentName.get();
	}

	public IntegerProperty departmentProperty() {
		return departmentId;
	}

	public int getDepartmentId() {
		return this.departmentId.get();
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId.set(departmentId);
	}

	public String getDepartmentName() {
		return departmentName.get();
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName.set(departmentName);
	}

	public StringProperty departmentNameProperty() {
		return this.departmentName;
	}

	public int getManagerId() {
		return managerId.get();
	}

	public void setManagerId(int managerId) {
		this.managerId.set(managerId);
	}

	public IntegerProperty managerProperty() {
		return managerId;
	}

	public int getLocationId() {
		return locationId.get();
	}

	public void setLocationId(int locationId) {
		this.locationId.set(locationId);
	}

	public IntegerProperty locationProperty() {
		return locationId;
	}


}
