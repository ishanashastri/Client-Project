package JavaFXGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import backend.Student;
import backend.StudentList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.*;

/**
 * The Starting tab of the program. 
 * @author Kevin and Ishana
 */
@SuppressWarnings("restriction")
public class StartTab extends Tab {
	private ObservableList<String> subentries = FXCollections.observableArrayList();
	private MenuTabPane parent;
	private HashMap<String, StudentList> data;
	private AnimatedAlertBox alert;
	private BorderPane content;
	private AtomicBoolean busMode;
	private ListView list;
	private ObservableList<String> nameEntries; 
	private TextField searchTextField;
	/**
	 * Initializes two buttons in the middle and a third View Records button in the bottom
	 * left
	 * @param p The Parent of this node.
	 * @param title The title of this tab.
	 * @param data The data of the program.
	 */
	public StartTab(MenuTabPane p, String title, HashMap<String, StudentList> data, AtomicBoolean bMode){
		busMode = bMode;

		this.data = data;
		parent = p;
		setText(title);
		content = new BorderPane();

		HBox viewButtonHBox = new HBox();
		viewButtonHBox.setPadding(new Insets(15, 12, 15, 12));
		viewButtonHBox.setSpacing(10);

		MenuBar menuBar = new MenuBar();
		Menu settings = new Menu("Settings");
		settings.setGraphic(viewButtonHBox);
		
		
		MenuItem viewButton = new MenuItem("View Records");
		viewButton.setOnAction(e -> parent.parent.showOptionsPage());
		settings.getItems().add(viewButton);
		menuBar.getMenus().add(settings);
		content.setBottom(menuBar);
		alert = new AnimatedAlertBox("Submission Sucessful!", false);
		content.setTop(alert);
		menuBar.getStyleClass().add("background");
		settings.getStyleClass().add("settings");
		setContent(content);
		switchMode();
	
	}
	/**
	 * Goes to the next page by creating a EnterStudentTab.
	 * @param signIn
	 */
	private void moveOn(boolean signIn){
		EnterStudentTab tab2 = null;
		if (signIn){
			tab2 = new EnterStudentTab(parent, this, "Sign In",  data, signIn);
		}
		else{
			tab2 = new EnterStudentTab(parent, this, "Sign Out",  data, signIn);
		}
		setDisable(true);
		parent.getTabs().add(tab2);
		parent.getSelectionModel().select(tab2);

	}
	/**
	 * Changes to Late Bus mode if the option is checked, otherwise it constructs the standard 
	 * initial page.
	 */
	public void switchMode(){

		if (busMode.get()){
			list = new ListView<String>();


			Tooltip toolList = new Tooltip("Double click to select student, \n"
					+ "or select student and then click submit");
			list.setTooltip(toolList);

			VBox imageHBox = new VBox();
			imageHBox.setAlignment(Pos.CENTER);
			imageHBox.setPadding(new Insets(15, 12, 15, 12));
			imageHBox.setSpacing(10);

			Image photoID  = new Image("img//image.png");
			ImageView photoIDView = new ImageView();
			photoIDView.setImage(photoID);
			photoIDView.setFitWidth(300);
			photoIDView.setPreserveRatio(true);
			photoIDView.setSmooth(true);
			photoIDView.setCache(true);

			HBox labelHBox = new HBox();
			imageHBox.setPadding(new Insets(15, 12, 15, 12));
			imageHBox.setSpacing(10);

			Label studentIDLabel = new Label("Enter student name for late bus: ");
			studentIDLabel.getStyleClass().add("studentIDLabel");
			Button submitButton = new Button("Submit");
			submitButton.setDefaultButton(true);
			submitButton.setPrefSize(100, 20);
			submitButton.setOnAction(e -> submitButton());
			submitButton.getStyleClass().add("submitButton");
			labelHBox.getChildren().addAll(studentIDLabel);
			labelHBox.setAlignment(Pos.CENTER);

			nameEntries = FXCollections.observableArrayList(data.get("database").getInfoList());
			FXCollections.sort(nameEntries, new StudentComparator());
			searchTextField = new TextField();
			searchTextField.setPromptText("Search");
			searchTextField.textProperty().addListener(
					new ChangeListener<Object>() {
						public void changed(ObservableValue<?> observable, 
								Object oldVal, Object newVal) {
							searchStudent(nameEntries, (String)oldVal, (String)newVal);
						}
					});
			Platform.runLater(new Runnable() {
				public void run() {
					searchTextField.requestFocus();
				}
			});

			Tooltip toolTextField = new Tooltip(" Enter in keywords "
					+ "\n separated by a space. ");
			searchTextField.setTooltip(toolTextField);

			list.setMaxHeight(400);

			list.setItems(nameEntries);
			list.getStyleClass().add("searchTextField");

			VBox searchVBox = new VBox();
			searchVBox.setPadding(new Insets(15, 12, 15, 12));
			searchVBox.setSpacing(10);
			searchVBox.setMaxSize(500, 300);
			searchVBox.getChildren().addAll(searchTextField, list);
			list.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent click) {
					if (click.getClickCount() == 2) {
						String currentItemSelected = (String) list.getSelectionModel().getSelectedItem();
						list.getSelectionModel().select(-1);
						submit(currentItemSelected, false);
					}
				}
			});

			imageHBox.getChildren().addAll(photoIDView, labelHBox, searchVBox, submitButton);

			content.setCenter(imageHBox);

		}
		else{
			VBox contentHBox = new VBox();
			contentHBox.setAlignment(Pos.CENTER);
			contentHBox.setPadding(new Insets(15, 12, 15, 12));
			contentHBox.setSpacing(10);
			contentHBox.getStyleClass().add("background");

			Button buttonSignIn = new Button("STUDENT SIGN IN");
			buttonSignIn.setPrefSize(300, 60);
			buttonSignIn.setOnAction(e -> moveOn(true));
			buttonSignIn.getStyleClass().add("signButton");

			Button buttonSignOut = new Button("STUDENT SIGN OUT");
			buttonSignOut.setPrefSize(300, 60);
			buttonSignOut.setOnAction(e -> moveOn(false));
			buttonSignOut.getStyleClass().add("signButton");

			Image photoID  = new Image("img//image.png");
			ImageView photoIDView = new ImageView();
			photoIDView.setImage(photoID);
			photoIDView.setFitWidth(400);
			photoIDView.setPreserveRatio(true);
			photoIDView.setSmooth(true);
			photoIDView.setCache(true);
			VBox v = new VBox();
			v.getChildren().add(photoIDView);
			

			contentHBox.getChildren().addAll(buttonSignIn, buttonSignOut);
			content.setCenter(contentHBox);

		}

	}
	/**
	 * Displays a Success message when an entry is successfully submitted
	 */
	public void displaySuccess(){
		alert.setWarning(false);
		alert.play();
	}
	/**
	 * Searches for a student within an ObservableList. Every time a key is pressed
	 * in a textField, this method is called. Updates the ListView list accordingly. 
	 * The search functions uses every space-separated word as a keyword, and an 
	 * element that is deemed part of the search must have EVERY keyword.
	 * @param entries The ObservableList of Entries
	 * @param oldVal The previous value of the search
	 * @param newVal The new value of the Search.
	 */
	public void searchStudent(ObservableList<String> entries, String oldVal, String newVal) {

		if ( oldVal != null && (newVal.length() < oldVal.length()) ) {
			list.setItems( entries );
		}

		String[] parts = newVal.toUpperCase().split(" ");

		subentries = FXCollections.observableArrayList();
		for ( Object entry: list.getItems() ) {
			boolean match = true;
			String entryText = (String)entry;
			for ( String part: parts ) {
				if ( ! entryText.toUpperCase().contains(part) ) {
					match = false;
					break;
				}
			}

			if ( match ) {
				subentries.add(entryText);
			}
		}
		list.setItems(subentries);
	}
	
	/**
	 * Handles submit button for searching for the student. If there are multiple students with the 
	 * name typed, or no students at all with the name, an alert will play. Otherwise, 
	 * the submit button will be triggered. 
	 */
	public void submitButton(){
		searchTextField.requestFocus();
		String selected = (String) list.getSelectionModel().getSelectedItem();
		String submittedText = "";
		if (selected == null){
			if (subentries.size() !=0){
				if (subentries.size() ==1){
					submittedText = subentries.get(0);
					ArrayList<String> toStringList = data.get("database").getInfoList();
					if (toStringList.contains(submittedText)){
						submit(submittedText, false);
					}
				}
				else{
					alert.setWarning(true);
					alert.play("Please continue entering information until there is only one possible student.");
				}
			}
			else{
				submittedText = searchTextField.getText();
				if(data.get("database").getIDList().contains(submittedText)){
					submit(submittedText,true);
				}
				else if (searchTextField.getText().isEmpty()){
					alert.setWarning(true);
					alert.play("Please submit your name.");
				}
				else{
					alert.setWarning(true);
					alert.play("The student \"" + searchTextField.getText() + "\" was not found.");
				}
			}
		}
		else{
			submit(selected, false);
		}
	}
	/**
	 * Handles late bus entries. The student's reason is automatically set as "Late Bus," and their time
	 * of arrival is recorded in the log. 
	 * @param txt Keyboard input of student (name or ID)
	 * @param id true if the student is entering their ID, false if student is entering name
	 */
	public void submit(String txt, boolean id){
		searchTextField.clear();
		LocalDate todayDate = LocalDate.now();
		String date = todayDate.toString();		
		
		Student newStudent;
		if(id){
			newStudent = data.get("database").getStudentByID(txt);
		}else{
			newStudent = data.get("database").getStudentByToString(txt);
		}
		newStudent.setReason("Late Bus");
		data.get("in").add(newStudent);
		
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
		
		alert.setWarning(false);
		alert.play("Student " + newStudent.getName() + " successfully recorded.");
		
	}
}
