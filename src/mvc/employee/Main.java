package mvc.employee;

import java.util.Optional;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mvc.employee.model.Employee;
import mvc.employee.model.dal.DepartmentsDAL;
import mvc.employee.model.dal.EmployeesDAL;
import mvc.employee.model.dal.JobsDAL;
import mvc.employee.model.dal.OraConn;
import mvc.employee.model.view.AlertBox;
import mvc.employee.model.view.EmployeeController;
import mvc.employee.model.view.EmployeeEditController;
import mvc.employee.model.view.MainController;

public class Main extends Application implements IEmployeesOperations  {
	
	private EmployeesDAL employeesDAL;
	private DepartmentsDAL departmentsDAL;
	private JobsDAL jobsDAL;
	private EmployeeController empControler;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		if (OraDbConnect() > 0) {
			return;
		}
		try {
			
			employeesDAL = new EmployeesDAL();
			departmentsDAL = new DepartmentsDAL();
			jobsDAL = new JobsDAL();

			ViewLoader<BorderPane, Object> viewLoader = new ViewLoader<BorderPane, Object>("model/view/Main.fxml");
			BorderPane borderPane = viewLoader.getLayout();
			Scene scene = new Scene(borderPane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Pracownicy");
			
			ViewLoader<AnchorPane, EmployeeController> viewLoaderEmp = new ViewLoader<AnchorPane, EmployeeController>("model/view/EmployeeData.fxml");
			AnchorPane anchorPaneEmp = viewLoaderEmp.getLayout();
			borderPane.setCenter(anchorPaneEmp);
			((MainController) viewLoader.getController()).setStage(primaryStage);
			empControler = viewLoaderEmp.getController();
			((MainController) viewLoader.getController()).setStage(primaryStage);
			((MainController) viewLoader.getController()).setEmployeeFXML(viewLoaderEmp);
			
			
			empControler.setDdEmployeeOperations(this);			
			refreshEmployees(); 

			primaryStage.setOnHiding(e -> primaryStage_Hiding(e));
			primaryStage.setOnCloseRequest(e -> primaryStage_CloseRequest(e));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	int OraDbConnect() {
		int ret = OraConn.open("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:" + "1521:ora3inf", "xtemp15", "xtemp15");

		if (ret > 0) {
			AlertBox.showAndWait(AlertType.ERROR, "Nawi¹zanie po³¹czenia z baz¹ danych",
					"Nieprawid³owy u¿ytkownik lub has³o.\n");
		}
		return ret;
	}

	void primaryStage_Hiding(WindowEvent e) {
		try {
			OraConn.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	void primaryStage_CloseRequest(WindowEvent e) {
		Optional<ButtonType> result = AlertBox.showAndWait(AlertType.CONFIRMATION, "Koñczenie pracy",
				"Czy chcesz	zamkn¹æ	aplikacjê?");
		if (result.orElse(ButtonType.CANCEL) != ButtonType.OK)
			e.consume();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void refreshEmployees() {
		ObservableList<Employee> emps = employeesDAL.getEmployees();
		empControler.setEmployees(emps);	
	}
	
	@Override
	public void updateEmployee(Employee emp) {
		employeesDAL.updateEmployee(emp);		
	}

	@Override
	public void deleteEmployee(int empId) {
		employeesDAL.deleteByEmployeeId(empId);
	}

	@Override
	public void addEmployee(Employee emp) {
		employeesDAL.insertEmployee(emp);	
		refreshEmployees();
		empControler.setEmployeeView(emp);
	}
	
	@Override
	public boolean showEmployeeEditor(Employee emp) {	
		try {
			ViewLoader<AnchorPane, EmployeeEditController> viewLoaderEmpEdit = new ViewLoader<AnchorPane, EmployeeEditController>("model/view/EmployeeEdit.fxml");
			AnchorPane pane = viewLoaderEmpEdit.getLayout();

	        Stage dialogEmployee = new Stage();
	        dialogEmployee.setTitle("Pracownik");
	        dialogEmployee.initModality(Modality.WINDOW_MODAL);
	        dialogEmployee.initOwner(primaryStage);
	        Scene scene = new Scene(pane);
	        dialogEmployee.setScene(scene);

	        EmployeeEditController empControler = viewLoaderEmpEdit.getController();
	        empControler.setStage(dialogEmployee);        
	        
	        empControler.setDepartments(departmentsDAL.getDepartments());
	        empControler.setManagers(employeesDAL.getEmployees());
	        empControler.setJobs(jobsDAL.getJobs());
	        
	        empControler.setEmployee(emp);

	        dialogEmployee.showAndWait();

	        return empControler.isSaved();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}
