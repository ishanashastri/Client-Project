package JavaFXGUI;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * A handler that handles the selection of elements in the OptionSelect Class.  
 * @author Ishana
 */
public class ChoiceHandler implements EventHandler<ActionEvent>{
	
	private String val ;
	private OptionSelect parent;
	/**
	 * Constructor  
	 * @param opt The value of the button.
	 * @param b A Playable node.
	 * @param p The Parent OptionSelect class
	 */
	public ChoiceHandler(String opt, OptionSelect p) {
		this.val = opt ;
		parent = p;
	}

	public void handle(ActionEvent event) {
		parent.addInfo(val);
	}
}
