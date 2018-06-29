package mvc.employee.model.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import mvc.employee.model.Department;
import mvc.employee.model.Employee;
import mvc.employee.model.Job;

public class EmployeeEditController {

	private boolean saved = false;
	private Stage stage;
	private Employee emp;

	@FXML
	private ComboBox<Department> cbDepartments;

	@FXML
	private ComboBox<Job> cbJobs;

	@FXML
	private ComboBox<Employee> cbManagers;

	@FXML
	private Label header;

	@FXML
	private Label employeeIdLabel;

	@FXML
	private Label employeeCaptionLabel;

	@FXML
	private TextField lastName;

	@FXML
	private TextField firstName;

	@FXML
	private TextField email;

	@FXML
	private TextField phone;

	@FXML
	private DatePicker hireDate;

	@FXML
	private TextField salary;

	@FXML
	private void initialize() {
		hireDate.setConverter(converter);
		hireDate.setPromptText("yyyy-MM-dd");
	}

	public void setDepartments(ObservableList<Department> olDepartment) {
		cbDepartments.getItems().clear();
		cbDepartments.setItems(olDepartment);
	}

	public void setJobs(ObservableList<Job> olJob) {
		cbJobs.getItems().clear();
		cbJobs.setItems(olJob);
	}

	public void setManagers(ObservableList<Employee> olManagers) {
		cbManagers.getItems().clear();
		cbManagers.setItems(olManagers);
	}

	@FXML
	public void save() {
		if (isValid()) {
			update(emp);
			saved = true;
			stage.close();
		} else {
			AlertBox.showAndWait(AlertType.ERROR, "B³¹d", "B³¹d w danych");
		}
	}

	@FXML
	public void close() {
		stage.close();
	}

	public void setEmployee(Employee emp) {
		this.emp = emp;
		if (emp.getEmployeeId() == 0) {
			employeeIdLabel.setText("");
			employeeCaptionLabel.setVisible(false);
			header.setText("Nowy pracownik");

		} else {
			employeeIdLabel.setText(String.valueOf(emp.getEmployeeId()));
			header.setText("Edycja pracownika");
		}
		refresh();
	}

	private void refresh() {
		if (emp.getEmployeeId() == 0) {
			clear(firstName, lastName, email, phone, salary);
			hireDate.setValue(LocalDate.now());
		} else {
			firstName.setText(emp.getFirstName());
			lastName.setText(emp.getLastName());
			phone.setText(emp.getPhone());
			email.setText(emp.getEmail());
			salary.setText(String.valueOf(emp.getSalary()));
			hireDate.setValue(emp.getHireDate());
			System.out.println("> jobid=" + emp.getJobId());

			try {
				ObservableList<Department> deps = cbDepartments.getItems()
						.filtered(x -> x.getDepartmentId() == emp.getDepartmentId());
				if (deps.size() > 0) {
					cbDepartments.setValue(deps.get(0));
				}

				ObservableList<Employee> mans = cbManagers.getItems()
						.filtered(x -> x.getManagerId() == emp.getManagerId());
				if (mans.size() > 0) {				
					cbManagers.setValue(mans.get(0));
				}
				
				ObservableList<Job> jobs = cbJobs.getItems()
						.filtered(x -> x.getJobId().equals(emp.getJobId()));
				if (jobs.size() > 0) {				
					cbJobs.setValue(jobs.get(0));
				}								

			} catch (Exception e) {

			}
		}
	}

	private void clear(TextInputControl... controls) {
		for (TextInputControl cnt : controls) {
			cnt.setText("");
		}
	}

	private boolean update(Employee emp) {
		try {
			emp.setFirstName(firstName.getText());
			emp.setLastName(lastName.getText());
			emp.setPhone(phone.getText());
			emp.setEmail(email.getText());
			emp.setSalary(Integer.parseInt(salary.getText()));
			emp.setHireDate(hireDate.getValue());
			Department dep = cbDepartments.getValue();
			if (dep != null) {
				emp.setDepartmentId(dep.getDepartmentId());
				emp.setDepartmentName(dep.getDepartmentName());
			}
			Employee man = cbManagers.getValue();
			if (man != null) {
				emp.setManagerId(man.getManagerId());
				emp.setManagerName(man.getManagerName());
			}
			Job job = cbJobs.getValue();
			if (job != null) {
				emp.setJobId(job.getJobId());
				emp.setJobTitle(job.getJobTitle());
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public boolean isSaved() {
		return saved;
	}

	private boolean isValid() {
		Employee empTmp = new Employee();
		return update(empTmp);
	}

	StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		@Override
		public String toString(LocalDate date) {
			if (date != null) {
				return dateFormatter.format(date);
			} else {
				return "";
			}
		}

		@Override
		public LocalDate fromString(String string) {
			if (string != null && !string.isEmpty()) {
				return LocalDate.parse(string, dateFormatter);
			} else {
				return null;
			}
		}
	};

}
