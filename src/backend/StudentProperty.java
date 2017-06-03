package backend;
import java.util.ArrayList;

import javafx.beans.property.*;

/**
 * Wrapper Class of student for implementation in a TableView.
 * @author Kevin
 */
@SuppressWarnings("restriction")
public class StudentProperty {
	private SimpleStringProperty name;
	private SimpleIntegerProperty grade;
	private SimpleStringProperty studentID;
	private ArrayList<SimpleStringProperty> reason = new ArrayList<SimpleStringProperty>();
	private SimpleStringProperty date;
	private ArrayList<SimpleStringProperty> time = new ArrayList<SimpleStringProperty>();
	private ArrayList<SimpleStringProperty> excused = new ArrayList<SimpleStringProperty>();
	private ArrayList<SimpleStringProperty> arrTime = new ArrayList<SimpleStringProperty>();
	
	/**
	 * Constructs StudentProperty from a Student and copies all the data.
	 * @param st the Student to be copied
	 */
	public StudentProperty(Student st){
		name = new SimpleStringProperty(st.getName());
		grade = new SimpleIntegerProperty(st.getGrade());
		studentID = new SimpleStringProperty(st.getStudentID());
		for(int i = 0; i<st.getReason().size();i++){
			reason.add(new SimpleStringProperty(st.getReason().get(i)));
		}
		date = new SimpleStringProperty(st.getDate());
		for(int i = 0;i<st.getTime().size();i++){
			time.add(new SimpleStringProperty(st.getTime().get(i)));
		}
		for(int i = 0;i<st.getExcused().size();i++){
			excused.add(new SimpleStringProperty(st.getExcused().get(i)));
		}
		for(int i = 0;i<st.getArrTime().size();i++){
			arrTime.add(new SimpleStringProperty(st.getArrTime().get(i)));
		}
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public Integer getGrade() {
		return grade.get();
	}

	public void setGrade(int grade) {
		this.grade.set(grade);
	}

	public String getStudentID() {
		return studentID.get();
	}

	public void setStudentID(String studentID) {
		this.studentID.set(studentID);
	}

	public ArrayList<SimpleStringProperty> getReason() {
		return reason;
	}

	public void setReason(String reason) {
		SimpleStringProperty r = new SimpleStringProperty(reason);
		this.reason.add(r);
	}
	
	public String getDate() {
		return date.get();
	}

	public void setDate(String date) {
		this.date.set(date);
	}
	
	public ArrayList<SimpleStringProperty> getExcused() {
		return excused;
	}

	public void setExcused(String excused) {
		SimpleStringProperty e = new SimpleStringProperty(excused);
		this.excused.add(e);
	}
	
	public ArrayList<SimpleStringProperty> getArrTime() {
		return arrTime;
	}

	public void setArrTime(String arrTime) {
		SimpleStringProperty a = new SimpleStringProperty(arrTime);
		this.arrTime.add(a);
	}
	public ArrayList<SimpleStringProperty> getTime() {
		return time;
	}

	public void setTime(String time) {
		SimpleStringProperty t = new SimpleStringProperty(time);
		this.time.add(t);
	}
	
	
}