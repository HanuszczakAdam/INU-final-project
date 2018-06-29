package mvc.employee.model.view;

import java.time.LocalDate;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mvc.employee.IEmployeesOperations;
import mvc.employee.model.Employee;

public class EmployeeController {

	private IEmployeesOperations empOperations = null;

	@FXML
	private TableView<Employee> employeeTable;
	@FXML
	private TableColumn<Employee, Integer> employeeIdColumn;
	@FXML
	private TableColumn<Employee, String> firstNameColumn;
	@FXML
	private TableColumn<Employee, String> lastNameColumn;
	@FXML
	private TableColumn<Employee, String> emailColumn;
	@FXML
	private TableColumn<Employee, String> phoneColumn;
	@FXML
	private TableColumn<Employee, LocalDate> hireDateColumn;
	@FXML
	private TableColumn<Employee, String> hireDateAsStrColumn;
	@FXML
	private TableColumn<Employee, Integer> salaryColumn;

	@FXML
	private TableColumn<Employee, String> departmentNameColumn;
	@FXML
	private TableColumn<Employee, String> managerNameColumn;
	@FXML
	private TableColumn<Employee, String> jobTitleColumn;

	@FXML
	private Label employeeIdLabel;
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label hireDateLabel;
	@FXML
	private Label jobIdLabel;
	@FXML
	private Label salaryLabel;
	@FXML
	private Label managerIdLabel;
	@FXML
	private Label departmentIdLabel;

	@FXML
	private void initialize() {
		employeeTable.setTableMenuButtonVisible(true);
		employeeIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
		emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
		phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
		hireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());
		salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());

		departmentNameColumn.setCellValueFactory(cellData -> cellData.getValue().departmentNameProperty());
		managerNameColumn.setCellValueFactory(cellData -> cellData.getValue().managerNameProperty());
		jobTitleColumn.setCellValueFactory(cellData -> cellData.getValue().jobTitleProperty());


		refreshEmployee(null);

		employeeTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> refreshEmployee(newValue));
	}

	public void setEmployees(ObservableList<Employee> olEmployees) {
		employeeTable.getItems().clear();
		employeeTable.setItems(olEmployees);

		if (!employeeTable.getItems().isEmpty())
			employeeTable.getSelectionModel().select(0);
	}

	private void refreshEmployee(Employee emp) {
		if (emp != null) {
			employeeIdLabel.setText(Integer.toString(emp.getEmployeeId()));
			firstNameLabel.setText(emp.getFirstName());
			lastNameLabel.setText(emp.getLastName());
			emailLabel.setText(emp.getEmail());
			phoneLabel.setText(emp.getPhone());
			hireDateLabel.setText(emp.getHireDate().toString());
			jobIdLabel.setText(emp.getJobTitle());
			salaryLabel.setText(Integer.toString(emp.getSalary()));
			managerIdLabel.setText(emp.getManagerName());
			departmentIdLabel.setText(emp.getDepartmentName());
		} else {
			employeeIdLabel.setText("");
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			emailLabel.setText("");
			phoneLabel.setText("");
			hireDateLabel.setText("");
			jobIdLabel.setText("");
			salaryLabel.setText("");
			managerIdLabel.setText("");
			departmentIdLabel.setText("");
		}
	}

	@FXML
	private void deleteEmployee() {
		int selIdx = employeeTable.getSelectionModel().getSelectedIndex();
		if (selIdx >= 0) {
			if (empOperations != null) {
				empOperations.deleteEmployee(employeeTable.getItems().get(selIdx).getEmployeeId());
			}
			employeeTable.getItems().remove(selIdx);
		}
	}

	@FXML
	public void editEmployee() {
		if (empOperations == null) return;
		int selIdx = employeeTable.getSelectionModel().getSelectedIndex();
		if (selIdx >= 0) {
			Employee emp = employeeTable.getItems().get(selIdx);
			if (empOperations.showEmployeeEditor(emp)) {
				empOperations.updateEmployee(emp);				
				refreshEmployee(emp);
			}
		}
	}

	@FXML
	public void addEmployee() {
		if (empOperations == null) return;
		Employee emp = new Employee();
		if (empOperations.showEmployeeEditor(emp)) {
			empOperations.addEmployee(emp);				
			refreshEmployee(emp);
		}		
	
	}

	public void setDdEmployeeOperations(IEmployeesOperations empOperations) {
		this.empOperations = empOperations;
	}

	public void setEmployeeView(Employee emp) {
		int idx = employeeTable.getItems().lastIndexOf(emp);
		employeeTable.getSelectionModel().select(idx);
		
	}

}
