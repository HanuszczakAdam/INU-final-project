package mvc.employee.model.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.employee.model.Employee;

public class EmployeesDAL {
	
	private SQLException ex;
	public SQLException getSQLException() {
		return ex;
	}

	public EmployeesDAL() { }
	
	public ObservableList<Employee> getEmployees() {
		
		ObservableList<Employee> employees = FXCollections.observableArrayList();
		try (Statement statement = OraConn.getConnection().createStatement();) {
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT E.EMPLOYEE_ID");
			query.append(",E.FIRST_NAME");
			query.append(",E.LAST_NAME");
			query.append(",E.EMAIL");
			query.append(",E.PHONE_NUMBER");
			query.append(",E.HIRE_DATE");
			query.append(",E.JOB_ID");
			query.append(",E.SALARY");
			query.append(",E.COMMISSION_PCT");
			query.append(",E.MANAGER_ID");
			query.append(",E.DEPARTMENT_ID");
			query.append(",D.DEPARTMENT_NAME");
			query.append(",M.FIRST_NAME||' '||M.LAST_NAME");			
			query.append(",J.JOB_TITLE");
			query.append(" FROM EMPLOYEES E");
			query.append(" LEFT OUTER JOIN DEPARTMENTS D ON (D.DEPARTMENT_ID = E.DEPARTMENT_ID) ");
			query.append(" LEFT OUTER JOIN EMPLOYEES M ON (M.EMPLOYEE_ID = E.MANAGER_ID) ");
			query.append(" LEFT OUTER JOIN JOBS J ON (J.JOB_ID = E.JOB_ID)");
			
			String sQ =query.toString();
	        ResultSet resultSet = statement.executeQuery(sQ);
	        
	        while (resultSet.next()) {	        	
	        	employees.add(rs2Employee(resultSet));
	        }
		}
		catch (SQLException ex ) {
			System.out.println(ex);
		} 
		return employees;
	}
	
	public ObservableList<Employee>  getEmployeesByEmployeeId(int EmployeeId) {
		
		ObservableList<Employee> employees = FXCollections.observableArrayList();
		try (Statement statement = OraConn.getConnection().createStatement();) 
		{
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT E.EMPLOYEE_ID");
			query.append(",E.FIRST_NAME");
			query.append(",E.LAST_NAME");
			query.append(",E.EMAIL");
			query.append(",E.PHONE_NUMBER");
			query.append(",E.HIRE_DATE");
			query.append(",E.JOB_ID");
			query.append(",E.SALARY");
			query.append(",E.COMMISSION_PCT");
			query.append(",E.MANAGER_ID");
			query.append(",E.DEPARTMENT_ID");
			query.append(",D.DEPARTMENT_NAME");
			query.append(",M.FIRST_NAME||' '||M.LAST_NAME");			
			query.append(",J.JOB_TITLE");
			query.append(" FROM EMPLOYEES E");
			query.append(" LEFT OUTER JOIN DEPARTMENTS D ON (D.DEPARTMENT_ID = E.DEPARTMENT_ID) ");
			query.append(" LEFT OUTER JOIN EMPLOYEES M ON (M.EMPLOYEE_ID = E.MANAGER_ID) ");
			query.append(" LEFT OUTER JOIN JOBS J ON (J.JOB_ID = E.JOB_ID)");
			query.append(" WHERE E.EMPLOYEE_ID =" + EmployeeId);
			
			String sQ =query.toString();
	        ResultSet resultSet = statement.executeQuery(sQ);
	        
	        while (resultSet.next()) {
	        	employees.add(rs2Employee(resultSet));
	        }
		}
		catch (SQLException ex ) {
			System.out.println(ex);
		} 
		return employees;
	}
	
	public int deleteByEmployeeId(int EmployeeId) {

		try (Statement statement = OraConn.getConnection().createStatement();) {
		    
			String query = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID =" + EmployeeId;
			int affectedRows = statement.executeUpdate(query);
	        return affectedRows;
		}
		catch (SQLException ex ) {
			this.ex = ex;
			return 0;
		} 
	}

	public int updateEmployee(Employee emp) {
		try (Statement statement = OraConn.getConnection().createStatement();) {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			String hireDate = dtf.format(emp.getHireDate());
			
			String query = "UPDATE EMPLOYEES SET "
					+ "LAST_NAME     = '"  + emp.getLastName() 	   + "', "
					+ "FIRST_NAME    = '"  + emp.getFirstName()    + "', "
					+ "EMAIL         = '"  + emp.getEmail() 	   + "', "
					+ "JOB_ID        = '"  + emp.getJobId() 	   + "', "
					+ "PHONE_NUMBER  = '"  + emp.getPhone() 	   + "', "
					+ "HIRE_DATE     =  to_date('"  + hireDate + "', 'yyyyMMdd') , "	
					+ "DEPARTMENT_ID =  "  + emp.getDepartmentId() + " , "
					+ "MANAGER_ID    =  "  + emp.getManagerId()    + " , "
					+ "SALARY        =  "  + emp.getSalary() 	   + "   "
					+ "WHERE " 
					+ "EMPLOYEE_ID   =  " + emp.getEmployeeId();
	        int affectedRows = statement.executeUpdate(query);
	        OraConn.getConnection().commit();
	        return affectedRows;
		}
		catch (SQLException ex ) {
			this.ex = ex;
			return 0;
		} 
	}
	
	public void updateFromLatest(Employee emp) {	

			try (Statement statement = OraConn.getConnection().createStatement();) {
			    
				String query = "SELECT MAX(EMPLOYEE_ID) FROM EMPLOYEES";
		        ResultSet resultSet = statement.executeQuery(query);
		        
		        if (resultSet.next()) {		        	
		        	int id=resultSet.getInt(1);
		        	emp.setEmployeeId(id);
		        }
			}
			catch (SQLException ex ) {
				System.out.println(ex);
			} 
	}
	
	public int insertEmployee(Employee emp) {
		try (Statement statement = OraConn.getConnection().createStatement();) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
			String hireDate = dtf.format(emp.getHireDate());
					
			String query = "INSERT INTO EMPLOYEES VALUES("
					+ "(SELECT MAX(EMPLOYEE_ID) + 1 FROM EMPLOYEES) , '" 
					+ emp.getFirstName()    + "','"
					+ emp.getLastName()     + "','"
					+ emp.getEmail() 	    + "','"
					+ emp.getPhone() 	    + "', "
					+ "to_date('" + hireDate + "','yy/MM/dd'), '"	
					+ emp.getJobId() 	    + "', "
					+ emp.getSalary() 	    + " , "
					+ "null"				+ " , "
					+ emp.getManagerId() 	+ " , "
					+ emp.getDepartmentId() + " ) " ;			
			if (statement.executeUpdate(query)>0) {
			  updateFromLatest(emp);
			  return 1;	
			} else
			  return 0;
		}
		catch (SQLException ex ) {
			this.ex = ex;
			return 0;
		} 
	}
	
	private Employee rs2Employee(ResultSet resultSet){
		Employee emp = null;
		try {
			int col = 1;
			emp = new Employee(resultSet.getInt(col++));
	    	emp.setFirstName(resultSet.getNString(col++));
	    	emp.setLastName(resultSet.getNString(col++));
	    	emp.setEmail(resultSet.getNString(col++));
	    	emp.setPhone(resultSet.getNString(col++));
	    	emp.setHireDate(resultSet.getDate(col++).toLocalDate());
	    	emp.setJobId(resultSet.getNString(col++));
	    	emp.setSalary(resultSet.getInt(col++));
	    	col++;
	    	emp.setManagerId(resultSet.getInt(col++));
	    	emp.setDepartmentId(resultSet.getInt(col++));
	    	emp.setDepartmentName( resultSet.getNString(col++));
	    	emp.setManagerName(resultSet.getNString(col++));
	    	emp.setJobTitle(resultSet.getNString(col++));	    	
		}
		catch (SQLException ex ) {
			this.ex = ex;
			ex.printStackTrace();
		}
		return emp;
	}

}
