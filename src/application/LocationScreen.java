package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LocationScreen {
	District district ;;
	private GridPane grid;

	private Button insertBtn;
	private Label insertLabel;
	private TextField insertTxt;
	private Label insertResult;

	private ComboBox<String> combo;

	private Button deleteBtn;
	private Label deleteLabel;
	private Label deleteResult;

	private Button updateBtn;
	private Label updateLabel;
	private TextField updateTxt;
	private Label updateResult;

	private Button prev;
	private Button next;
	private Label info;
	private Button martyrScreenBtn;

	private Label nameOfLocation;
	private Button back;

	public LocationScreen(District district) {
		if(Main.currDistrict != null)
			this.district = district;
		//district = Main.currDistrict.getData();
		grid = new GridPane();
		grid.setVgap(20);
		grid.setHgap(20);
		grid.setAlignment(Pos.CENTER);

		insertBtn = new Button("insert location");
		insertLabel = new Label("enter the name of location:");
		insertTxt = new TextField();
		insertResult = new Label();

		combo = new ComboBox<>();
		combo.setPrefWidth(100);
		combo.setPromptText("Locations");

		deleteBtn = new Button("delete location");
		deleteLabel = new Label("Choose the location from the\n following comboBox");
		deleteResult = new Label();

		updateBtn = new Button("update location");
		updateLabel = new Label("choose the location from the\n above comboBox and enter\nthe new name");
		updateTxt = new TextField();
		updateResult = new Label();

		prev = new Button("previous");
		next = new Button("next");
		info = new Label();
		martyrScreenBtn = new Button("Load martyr screen");

		nameOfLocation = new Label();
		back = new Button("back");

		grid.add(insertBtn, 0, 0);
		grid.add(insertLabel, 1, 0);
		grid.add(insertTxt, 2, 0);
		grid.add(insertResult, 3, 0);

		grid.add(deleteBtn, 0, 1);
		grid.add(deleteLabel, 1, 1);
		grid.add(deleteResult, 2, 1);

		grid.add(combo, 1, 2);

		grid.add(updateBtn, 0, 3);
		grid.add(updateLabel, 1, 3);
		grid.add(updateTxt, 2, 3);
		grid.add(updateResult, 3, 3);

		grid.add(prev, 0, 4);
		grid.add(nameOfLocation, 1, 4);
		grid.add(next, 2, 4);
		grid.add(info, 1, 5);
		grid.add(martyrScreenBtn, 1, 6);
		grid.add(back, 1, 7);

	}

	public void insertAction() {
		if (!insertTxt.getText().isEmpty()) {
			Location newLocation = new Location(insertTxt.getText());
			if (district.getLocations().find(newLocation) == null) {
				district.getLocations().insert(newLocation);
				insertResult.setText("proccess is done");
			} else {
				insertResult.setText("The location is already exist");
			}
		} else {
			insertResult.setText("Enter the name of location");
		}
	}

	public Location deleteAction() {
		if (combo.getValue() != null) {
			deleteResult.setText("proccess Done");
			return district.getLocations().delete(new Location(combo.getValue()));
			
		} else {
			deleteResult.setText("choose the district");
			return null;
		}
	}
	
	public void updateAction() {
    	if(!updateTxt.getText().isEmpty() && combo.getValue() != null) {
    		Location location = new Location(updateTxt.getText());
    		if(district.getLocations().find(location) == null) {
    			Location original = district.getLocations().find(new Location(combo.getValue())).getData();
    			district.getLocations().delete(original);
    			original.setLocation(location.getLocation()); 
    			district.getLocations().insert(original);
    			updateResult.setText("proccess done");
    		}else {
    			updateResult.setText("The new name is a name\n for another location");
    		}
    	}else {
    		updateResult.setText("Error! you don`t choose the location\nor you don`t enter the new name");
    	}
    }
	
	public void informationCurrLocAction(Location location) {
		 if(location != null) {
			 if(location.earliestDate() != null && location.latestDate() != null && location.dateHasMaxNumOfMartyrs() != null ) {
			 info.setText(location.getLocation()+"\nThe earliest date that has martyrs: "+Main.printDate(location.earliestDate())+""
			 		+ "\nThe latest dates that has martyrs: "+Main.printDate(location.latestDate())+""
			 				+ "\nThe date that has the maximum number of martyrs: "+Main.printDate(location.dateHasMaxNumOfMartyrs()));		
			 }else {
				 info.setText(location.getLocation()+"\nThe earliest date that has martyrs: null "+""
					 		+ "\nThe latest dates that has martyrs: null "+""
					 				+ "\nThe date that has the maximum number of martyrs: null ");	
			 }
		 }
	}
    public void clear() {
    	
    	insertTxt.setText("");;
    	insertResult.setText("");;
    	
    	deleteResult.setText("");
    	updateTxt.setText("");;
    	updateResult.setText("");
    	info.setText("");
    	nameOfLocation.setText("");;
    	
    }
	public GridPane getGrid() {
		return grid;
	}

	public Button getInsertBtn() {
		return insertBtn;
	}

	public Label getInsertLabel() {
		return insertLabel;
	}

	public TextField getInsertTxt() {
		return insertTxt;
	}

	public Label getInsertResult() {
		return insertResult;
	}

	public ComboBox<String> getCombo() {
		return combo;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public Label getDeleteLabel() {
		return deleteLabel;
	}

	public Label getDeleteResult() {
		return deleteResult;
	}

	public Button getUpdateBtn() {
		return updateBtn;
	}

	public Label getUpdateLabel() {
		return updateLabel;
	}

	public TextField getUpdateTxt() {
		return updateTxt;
	}

	public Label getUpdateResult() {
		return updateResult;
	}

	public Button getPrev() {
		return prev;
	}

	public Button getNext() {
		return next;
	}

	public Label getInfo() {
		return info;
	}

	public Button getMartyrScreenBtn() {
		return martyrScreenBtn;
	}

	public Label getNameOfLocation() {
		return nameOfLocation;
	}

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Button getBack() {
		return back;
	}
  
}
