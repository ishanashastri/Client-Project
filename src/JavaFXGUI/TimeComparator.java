package JavaFXGUI;

import java.util.Comparator;

import backend.Student;

/**
 * Comparator class for a student toString() that sorts by time the student signs in/out.
 * @author Ishana
 */
public class TimeComparator implements Comparator<Student>{

	@Override
	
	public int compare(Student o1, Student o2) {
		return o2.getTime().compareTo(o1.getTime());
	}

}
