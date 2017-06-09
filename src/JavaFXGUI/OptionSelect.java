package JavaFXGUI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;

import org.omg.CORBA.SystemException;

import backend.Student;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.Component;
import java.awt.event.KeyListener;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
/**
 * Creates a carousel of options that the student uses to select his/her reasons for signing in/out.
 * @author Kevin and Ishana
 */
@SuppressWarnings("restriction")
public class OptionSelect extends VBox{
	private ArrayList<ArrayList<OptionButton>> buttonList;
	private ArrayList<String> title = new ArrayList<String>();
	private VBox buttonVBox;
	private ArrayList<String> option = new ArrayList<String>();
	private int page;
	private Label titleLabel;
	private int height, width;
	private HBox bottomHBox;
	private Label pageNumberLabel;
	private Button submitButton;
	private EnterInfoTab tabToBeClosed;
	private Student student; 
	private FadeTransition ftIn;
	private FadeTransition ftOut;
	private ArrayList<OptionHBox> optionHBoxArray = new ArrayList<OptionHBox>();
	private HBox hb;
	private ToggleGroup tg;
	/**
	 * Constructor. The data of the optionSelect is represented by an ArrayList of Strings that indicates what options where selected.
	 * @param w Width of the optionSelect
	 * @param h Height of the optionSelect
	 * @param close The Tab to be closed when the options are submitted.
	 * @param st The Student selected
	 */
	public OptionSelect(int w, int h, EnterInfoTab close, Student st){
		buttonList = new ArrayList<ArrayList<OptionButton>>();
		height = h;
		width = w;
		student = st;
		tabToBeClosed = close;

		init();


	}
	/**
	 * Initializes contents of the optionSelect.
	 */
	public void init(){

		
	    ftIn = new FadeTransition(Duration.millis(250), buttonVBox);
		ftIn.setFromValue(0);
		ftIn.setToValue(1.0);
		ftIn.setCycleCount(1);

		
		ftOut = new FadeTransition(Duration.millis(250), buttonVBox);
		ftOut.setFromValue(1.0);
		ftOut.setToValue(0);
		ftOut.setCycleCount(1);
		

		setMaxHeight(height);
		setMaxWidth(width);

		submitButton = new Button("Submit");
		submitButton.setAlignment(Pos.BOTTOM_CENTER);
		submitButton.getStyleClass().add("submitButton");
		submitButton.setDefaultButton(true);
		submitButton.setOnAction(e -> handle());
	
		titleLabel= new Label();
		titleLabel.getStyleClass().add("optionTitle");

		getChildren().add(titleLabel);
		getStyleClass().add("optionSelect");
		HBox contentHBox = new HBox();

		buttonVBox = new VBox();
		buttonVBox.getStyleClass().add("buttonVBox");

		contentHBox.getChildren().addAll(buttonVBox);
		getChildren().add(contentHBox);

		bottomHBox = new HBox();
		bottomHBox.getStyleClass().add("bottomHBox");
		getChildren().add(bottomHBox);		

	}
	/**
	 * Adds a button.
	 * @param page The page of the carousel of the button
	 * @param name The text displayed in the button.
	 * @param mes The value of the Button
	 */
	public void addButton(int page, String name, String mes){
		buttonList.get(page).add(new OptionButton(name, mes, page));
		updateState(0);
	}

	/**
	 * Adds a page in the carousel.
	 * @param t The title of the page (usually the question).
	 */
	public void addPage(String t){	
		buttonList.add(new ArrayList<OptionButton>());
		title.add(t);
		option.add("");
		OptionHBox textFieldOtherHBox  = new OptionHBox(width, this, buttonList.size()-1);
		textFieldOtherHBox.getStyleClass().add("optionTextFieldOther");
		optionHBoxArray.add(textFieldOtherHBox);
		updateState(0);
	}
	
	
	/**
	 * Updates the state of the carousel. This is called every time the arrow buttons are clicked,
	 * or when a button/page is added. This method scales every child of the OptionSelect
	 * to fit the OptionSelect.
	 * @param pg The page to be displayed
	 */
	private void updateState(int pg){
		titleLabel.setText(title.get(pg));
		page = pg;

		buttonVBox.getChildren().clear();
		double buttonHeight = (double)(height-100)/ (buttonList.get(page).size()+1);
		double buttonWidth = (double)(width);

		if (buttonList.get(page).size() >=3){
			buttonList.get(page).get(0).setPosStyle("top");
			buttonList.get(page).get(0).setOnAction(new ButtonHandler(buttonList.get(page).get(0).getValue(), buttonList.get(page).get(0),this));
			buttonList.get(page).get(0).setPrefHeight(buttonHeight);
			buttonList.get(page).get(0).setPrefWidth(buttonWidth);
			buttonVBox.getChildren().add(buttonList.get(page).get(0));
			for (int i = 1; i < buttonList.get(page).size()-1; i++){
				buttonList.get(page).get(i).setPosStyle("mid");
				buttonVBox.getChildren().add(buttonList.get(page).get(i));

				buttonList.get(page).get(i).setPrefHeight(buttonHeight);
				buttonList.get(page).get(i).setPrefWidth(buttonWidth);

				buttonList.get(page).get(i).setOnAction(new ButtonHandler(buttonList.get(page).get(i).getValue(), buttonList.get(page).get(i),this));
			}
			buttonList.get(page).get(buttonList.get(page).size()-1).setPosStyle("mid");
			buttonList.get(page).get(buttonList.get(page).size()-1).setOnAction(new ButtonHandler(buttonList.get(page).get(buttonList.get(page).size()-1).getValue(),buttonList.get(page).get(buttonList.get(page).size()-1), this));
			buttonList.get(page).get(buttonList.get(page).size()-1).setPrefHeight(buttonHeight);
			buttonList.get(page).get(buttonList.get(page).size()-1).setPrefWidth(buttonWidth);
			buttonVBox.getChildren().add(buttonList.get(page).get(buttonList.get(page).size()-1));
		}
		else if (buttonList.get(page).size() == 2){
			buttonList.get(page).get(0).setPosStyle("top");
			buttonList.get(page).get(0).setOnAction(new ButtonHandler(buttonList.get(page).get(0).getValue(), buttonList.get(page).get(0),this));
			buttonList.get(page).get(0).setPrefHeight(buttonHeight);
			buttonList.get(page).get(0).setPrefWidth(buttonWidth);
			buttonVBox.getChildren().add(buttonList.get(page).get(0));

			buttonList.get(page).get(buttonList.get(page).size()-1).setPosStyle("mid");
			buttonList.get(page).get(buttonList.get(page).size()-1).setOnAction(new ButtonHandler(buttonList.get(page).get(buttonList.get(page).size()-1).getValue(), buttonList.get(page).get(buttonList.get(page).size()-1), this));
			buttonList.get(page).get(buttonList.get(page).size()-1).setPrefHeight(buttonHeight);
			buttonList.get(page).get(buttonList.get(page).size()-1).setPrefWidth(buttonWidth);
			buttonVBox.getChildren().add(buttonList.get(page).get(buttonList.get(page).size()-1));

		}
		else if (buttonList.get(page).size() == 1){
			buttonList.get(page).get(0).setPosStyle("mid");
			buttonList.get(page).get(0).setPrefHeight(buttonHeight);
			buttonList.get(page).get(0).setPrefWidth(buttonWidth);
			buttonList.get(page).get(0).setOnAction(new ButtonHandler(buttonList.get(page).get(0).getValue(), buttonList.get(page).get(0),this));
			buttonVBox.getChildren().add(buttonList.get(page).get(0));
		}
		


		optionHBoxArray.get(page).setPrefHeight(buttonHeight);
		optionHBoxArray.get(page).setPrefWidth(buttonWidth);

		buttonVBox.getChildren().add(optionHBoxArray.get(page));

	}
	/**
	 * Creates new page with two questions: the first question's options are OptionButtons, while
	 * the second question's buttons are RadioButtons
	 * @param page page to be implemented on
	 * @param data list of options
	 */
	public void splitScreen(int page, ArrayList<String> data){
		ArrayList<OptionButton> left = new ArrayList<OptionButton>();
		for(int i =0;i<data.size()-1;i++){
			OptionButton opt = new OptionButton(data.get(i), data.get(i), page);
			left.add(opt);
		}
		buttonList.get(page).addAll(left) ;	
		option.add("");
		
		updateState(0);	
		addRadButton(false);
		buttonVBox.getChildren().remove(optionHBoxArray.get(page));
		System.out.println("split screen");
	}
	/**
	 * Adds RadioButtons with the question and the options.
	 * @param rem deletes RadioButton if rem is true.
	 */
	public void addRadButton(boolean rem){
		RadioButton h = new RadioButton("Health Room");
		h.getStyleClass().add("RadioButton");
		RadioButton p = new RadioButton("Parent");
		p.getStyleClass().add("RadioButton");
		
		Label l = new Label("Enter who you were excused by: ");
		l.getStyleClass().add("RadioButton");
		
		tg = new ToggleGroup();
		h.setToggleGroup(tg); p.setToggleGroup(tg);
		tg.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) -> {
			for(String opt: option){
				System.out.println(opt);
			}
			String[] pts = tg.getSelectedToggle().toString().split("'");
			option.add(1,pts[1]);
			submitButton.requestFocus();
		});
		
		HBox hb = new HBox(60);
		hb.getChildren().addAll(l,h,p); 
		hb.setAlignment(Pos.CENTER);
		
		buttonVBox.getChildren().add(hb);
		System.out.println("add choices");
//		System.out.println(buttonVBox.getChildren().contains(hb));
		submitButton.requestFocus();
		if(rem){
			System.out.println(buttonVBox.getChildren().contains(hb));
			buttonVBox.getChildren().remove(hb);
			System.out.println("removed " + buttonVBox.getChildren().contains(hb));
		}
	}
	/**
	 * Removes all RadioButtons from page
	 */
	public void removeRadButton(){
		System.out.println("before: " + buttonVBox.getChildren().contains(hb));
		if(buttonVBox.getChildren().contains(hb)){
			buttonVBox.getChildren().remove(hb);
		}
		System.out.println("remove choices");
		System.out.println("removed: " + buttonVBox.getChildren().contains(hb));
	}
	/**
	 * This is called by the ButtonHandler to add information to the Data.
	 * @param mes The value of the button clicked to be added.
	 */
	public void addInfo(String mes){
		option.set(page, mes);
	}
	
	/**
	 * Adds a submit button to the bottom of the specified page if a submit button does not already exist. 
	 * @param page
	 */
	public void addSubmitButton(int page){
		if(!bottomHBox.getChildren().contains(submitButton)){
			 bottomHBox.getChildren().add(submitButton);
		}
	}
	/**
	 * Called when the arrow buttons are clicked.
	 * @param next true if pageButtonRight is clicked
	 */
	private void setPage(boolean next){
		
		if (next == true && (page + 1 < buttonList.size()-1)){
			transitionPage(page+1);
		}
		else if ((next != true) && (page - 1 >= 0)){
			transitionPage(page-1);
		}
		else if ((next == true) && (page + 1 == buttonList.size()-1)){
			transitionPage(page+1);
		}
	}

	/**
	 * This is called when the arrow buttons are clicked. If the current page is equal to the amount of pages.
	 * the User is brought to a custom Page with a submit button.
	 * @param pg
	 */
	private void transitionPage(int pg){
		if (pg == buttonList.size()-1){
			ftOut.play();
			page = page + 1;
			ftIn.play();
//			pageButtonRight.setDisable(true);
//			pageButtonLeft.setDisable(false);
		}
		else{
			for(ArrayList<OptionButton> e: buttonList){
				for(OptionButton d: e){
					System.out.println(d);
				}
			}
			ftOut.play();
			updateState(pg);
			ftIn.play();
		}

	}
	
	/**
	 * The class that handles going to the next page.
	 * @author Kevin
	 *
	 */
	private class NextHandler implements EventHandler<ActionEvent> {
		private boolean next ;
		public NextHandler(boolean n) {
			this.next = n;
		}
		@Override
		public void handle(ActionEvent event) {
			setPage(next);
		}

	}
	
	/**
	 * The method that handles submitting the data. It places focus on the submit button so it listens to key presses 
	 * automatically.
	 * @author Ishana
	 *
	 */
	public void handle() {
		submitButton.defaultButtonProperty().bind(submitButton.focusedProperty());
			tabToBeClosed.addData(option);
	}

	
	public ArrayList<String> getOption(){
		ArrayList<String> returnArr = new ArrayList<String>();
		for (String o : option){
			returnArr.add(o);
		}
		return returnArr;
	}
	public ArrayList<String> getPageTitles(){
		ArrayList<String> titleArray = new ArrayList<String>();

		for (String t : title){
			titleArray.add(t);
		}
		return titleArray;
	}
	
	public int getPage(){
		return page;
	}
	
}