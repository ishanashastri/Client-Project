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
	private SimpleStringProperty time;
	private ArrayList<SimpleStringProperty> depTime = new ArrayList<SimpleStringProperty>();
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
		time = new SimpleStringProperty(st.getTime());
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

	public String getReason(int i) {
		return reason.get(i).get();
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
	
	public String getExcused(int i) {
		return excused.get(i).get();
	}

	public void setExcused(String excused) {
		SimpleStringProperty e = new SimpleStringProperty(excused);
		this.excused.add(e);
	}
	public String getDepTime(int i){
//		String dt = "";
//		for(int i = 0;i<depTime.size();i++){
//			dt+=depTime.get(i)+"\n";
//		}return dt;
		return depTime.get(0).get();
	}
	public void setDepTime(String depTime){
		SimpleStringProperty d = new SimpleStringProperty(depTime);
		this.depTime.add(d);
	}
	public String getArrTime(int i) {
		return arrTime.get(i).get();
	}

	public void setArrTime(String arrTime) {
		SimpleStringProperty a = new SimpleStringProperty(arrTime);
		this.arrTime.add(a);
	}
	public String getTime() {
		return time.get();
	}

	public void setTime(String time) {
		this.time.set(time);
	}
	
	
}