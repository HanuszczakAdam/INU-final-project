package mvc.employee.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Job {
	private StringProperty jobId;
	private StringProperty jobTitle;
	private IntegerProperty minSalary;
	private IntegerProperty maxSalary;

	public Job() {
		minSalary = new SimpleIntegerProperty(0);
		maxSalary = new SimpleIntegerProperty(0);
		jobId = new SimpleStringProperty("");
		jobTitle = new SimpleStringProperty("");	
	}

	public Job(String jobId) {
		this();
		this.jobId.set(jobId);
	}

	public String getJobId() {
		return jobId.get();
	}

	public void setJobId(String jobId) {
		this.jobId.set(jobId);
	}
	
	public StringProperty jobIdProperty() {
		return jobId;
	}
	

	public String getJobTitle() {
		return jobTitle.get();
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle.set(jobTitle);
	}
	
	public StringProperty jobTitleProperty() {
		return jobTitle;
	}

	public int getMinSalary() {
		return minSalary.get();
	}

	public void setMinSalary(int minSalary) {
		this.minSalary.set(minSalary);
	}
	
	public IntegerProperty minSalaryProperty() {
		return minSalary;
	}

	public int getMaxSalary() {
		return maxSalary.get();
	}

	public void setMaxSalary(int maxSalary) {
		this.maxSalary.set(maxSalary);
	}
	
	public IntegerProperty maxSalaryProperty() {
		return maxSalary;
	}

    @Override
    public String toString() {
    	return  getJobTitle();
    }

}
