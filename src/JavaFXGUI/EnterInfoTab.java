package JavaFXGUI;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import backend.Student;
import backend.StudentList;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * The tab where the student enters specific information about why he/she is late.
 * @author Kevin and Ishana
 *
 */
@SuppressWarnings("restriction")
public class EnterInfoTab extends Tab{

	private boolean goingIn;
	private MenuTabPane parent;
	private EnterStudentTab previous;
	private Student student;
	private HashMap<String, StudentList> data;
	private AnimatedAlertBox alert;
	private OptionSelect infoOptionSelect;
	/**
	 * Initializes the Tab. The Tab reads from a file "src/data/options.sip" for a list 
	 * of the options of the OptionSelect carousel. 
	 * @param par The parent (tabPane)
	 * @param prev The previous tab
	 * @param title The Title of the tab.
	 * @param d The Data of which students Signed in, out and the student Database
	 * @param gIn Whether or not the student is signing in.
	 * @param st The Student that is signing in. 
	 */
	public EnterInfoTab(MenuTabPane par, EnterStudentTab prev, String title,
			HashMap<String, StudentList> d, boolean gIn, Student st){
		setText(title);
		goingIn = gIn;
		previous = prev;
		parent = par;
		student = st;

		data = d;

		BorderPane content = new BorderPane();

		alert = new AnimatedAlertBox("Please select all options.", true);

		infoOptionSelect = new OptionSelect(700, 550, this, student);

		int version = 0;
		if (goingIn){
			version = 0;
			for (int i = 0; i < data.get("outin").getStudentList().size(); i++){
				if (student.equals(data.get("outin").getStudentList().get(i))){
					version = 1;
				}	
			}
		}
		else{
			version = 2;
		}
		Scanner file = null;
		try {
			file = new Scanner(new File("src/data/options.sip"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		int v = -1;
		int page = -1; 
		String stringData = "";
		while(file.hasNext()){
			if(goingIn){
				stringData = file.nextLine();
				if (stringData.equals("+++")){
					v++;
				}
				else if (stringData.equals("++") && version == v){
					page++;
					stringData = file.nextLine();
					if (stringData.contains("Submit Button")){
						String[] stringDatas = stringData.split(", ");
						infoOptionSelect.addPage(stringDatas[1]);
						infoOptionSelect.addSubmitButton(page);
					}	
					else{
						infoOptionSelect.addPage(stringData);
					}
				}
				else if (version == v){
					infoOptionSelect.addButton(page, stringData, stringData);
				}
				else if(stringData.equals("++++")){
					break;
				}
			}else{
				stringData=file.nextLine();
				if (stringData.equals("++++")){
					stringData=file.nextLine();
					if (stringData.contains("Submit Button")){
						String[] stringDatas = stringData.split(", ");
						infoOptionSelect.addSubmitButton(page);
						infoOptionSelect.addPage(stringDatas[1]);
						
						ArrayList<String> data = new ArrayList<String>();
						stringData = file.nextLine();
						while(!stringData.equals("Title 2")){
							data.add(stringData);
							stringData = file.nextLine();
						}
						stringData = file.nextLine();
						ArrayList<String> data2 = new ArrayList<String>();
						while(!stringData.equals("End")){
							data2.add(stringData);
							stringData=file.nextLine();
						}																																																																																			
						infoOptionSelect.splitScreen(page+1, data);
					}	
				}
			}
		}	
		file.close();


		Button backButton = new Button("Back to Start");
		backButton.setOnAction(e -> goBackToHome());
		backButton.setPrefSize(175, 20);

		HBox navHBox = new HBox();
		navHBox.setPadding(new Insets(15, 12, 15, 12));
		navHBox.setSpacing(10);

		navHBox.getChildren().add(backButton);

		VBox contentVBox = new VBox();

		contentVBox.setSpacing(40);

		VBox centerVBox = new VBox();
		centerVBox.getChildren().add(infoOptionSelect);
		centerVBox.setAlignment(Pos.CENTER);

		contentVBox.getChildren().addAll(alert, centerVBox);
		content.setCenter(contentVBox);
		content.setBottom(navHBox);

		setContent(content);
	}

	/**
	 * Transfers focus to the previous tab.
	 */
	public void goBack(){
		previous.setDisable(false);
		parent.getSelectionModel().select(previous);
		die();
	}
	/**
	 * Kills the current tab
	 */
	public void die(){
		parent.getTabs().remove(this);
	}
	/**
	 * Adds data to the program, and writes the data out to a backup file in case the program shuts down.
	 * It then closes the previous two tabs.
	 * @param option An ArrayList of Strings obtained from the OptionSelect that contains the data from that OptionSelect
	 */
	public void addData(ArrayList<String> option){
		if(goingIn){
			if (option.get(0).isEmpty()){
				alert.play();
				infoOptionSelect.submitFocus();
			}else{
				student.setReason(option.get(0));
				data.get("in").add(student);
				
				LocalDate todayDate = LocalDate.now();
				String date = todayDate.toString();
				File f = new File("src/backup/" + date+"-IN.csv");
				try {
					f.createNewFile();
				} catch (IOException e1) {
				// 	TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					PrintWriter printWriter = new PrintWriter (f);
					printWriter.println("DATE,ID,NAME,GR,TIME,REASON");
					TimeComparator c = new TimeComparator();
					data.get("in").getStudentList().sort(c);
					for(Student st : data.get("in").getStudentList()){
						printWriter.print("\"" + st.getDate() + "\",");
						printWriter.print("\"" + st.getStudentID() + "\",");
						printWriter.print("\"" + st.getName() + "\",");
						printWriter.print("\"" + st.getGrade() + "\",");
						printWriter.print("\"" + st.getTime() + "\",");
						printWriter.print("\"" + st.getReason() + "\",");
						printWriter.println();
					}
					printWriter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				f = new File("src/backup/" + date+"-OUT.csv");
				try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				try {
					PrintWriter printWriter = new PrintWriter (f);
					printWriter.println("DATE,ID,NAME,GR,REASON,EXCUSED,TIME,ARRTIME");
					TimeComparator c = new TimeComparator();
					data.get("outin").getStudentList().sort(c);
					for(Student st : data.get("outin").getStudentList()){
						printWriter.print("\"" + st.getDate() + "\",");
						printWriter.print("\"" + st.getStudentID() + "\",");
						printWriter.print("\"" + st.getName() + "\",");
						printWriter.print("\"" + st.getGrade() + "\",");
						printWriter.print("\"" + st.getReason() + "\",");
						printWriter.print("\"" + st.getExcused() + "\",");
						printWriter.print("\"" + st.getTime() + "\",");
						printWriter.print("\"" + st.getArrTime() + "\",");
						printWriter.println();
					}
					printWriter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goBack();
				previous.goBack(true);
			}
		}else{
			if(option.get(0).isEmpty() || option.get(1).isEmpty()){
				alert.play();
				infoOptionSelect.submitFocus();
			}else{
				student.setReason(option.get(0));
				student.setExcused(option.get(1));
				data.get("outin").add(student);

				LocalDate todayDate = LocalDate.now();
				String date = todayDate.toString();
				File f = new File("src/backup/" + date+"-IN.csv");
				try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				try {
					PrintWriter printWriter = new PrintWriter (f);
					printWriter.println("DATE,ID,NAME,GR,TIME,REASON");
					TimeComparator c = new TimeComparator();
					data.get("in").getStudentList().sort(c);
					for(Student st : data.get("in").getStudentList()){
						printWriter.print("\"" + st.getDate() + "\",");
						printWriter.print("\"" + st.getStudentID() + "\",");
						printWriter.print("\"" + st.getName() + "\",");
						printWriter.print("\"" + st.getGrade() + "\",");
						printWriter.print("\"" + st.getTime() + "\",");
						printWriter.print("\"" + st.getReason() + "\",");
						printWriter.println();
					}
					printWriter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				f = new File("src/backup/" + date+"-OUT.csv");
				try {
					f.createNewFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
				try {
					PrintWriter printWriter = new PrintWriter (f);
					printWriter.println("DATE,ID,NAME,GR,REASON,EXCUSED,TIME,ARRTIME");
					TimeComparator c = new TimeComparator();
					data.get("outin").getStudentList().sort(c);
					for(Student st : data.get("outin").getStudentList()){
						printWriter.print("\"" + st.getDate() + "\",");
						printWriter.print("\"" + st.getStudentID() + "\",");
						printWriter.print("\"" + st.getName() + "\",");
						printWriter.print("\"" + st.getGrade() + "\",");
						printWriter.print("\"" + st.getReason() + "\",");
						printWriter.print("\"" + st.getExcused() + "\",");
						printWriter.print("\"" + st.getTime() + "\",");
						printWriter.print("\"" + st.getArrTime() + "\",");
						printWriter.println();
					}
					printWriter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				goBack();
				previous.goBack(true);
			}
		}
	}

	public void goBackToHome(){
		goBack();
		previous.goBack(false);
	}

}
