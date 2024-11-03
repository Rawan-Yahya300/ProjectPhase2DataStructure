package application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DistrictScreen {
	 private GridPane grid ;
	
	private Button insertBtn ;
	private Label insertLabel;
	private TextField insertTxt;
	private Label insertResult;
	
	private ComboBox<String> combo;
	
	private Button deleteBtn ;
	private Label deleteLabel;
	private Label deleteResult;
	
	private Button updateBtn ;
	private Label updateLabel;
	private TextField updateTxt;
	private Label updateResult;
	
	private Button prev;
	private Button next;
	private Label numOfMArtyrsLbl;
	private Button locationScreenBtn;
	private Label locationScreenLbl;
	
	private Label nameOfDistrict;
	private Button back;
	
	public DistrictScreen() {
		
		 grid = new GridPane();
		 grid.setVgap(20);
		 grid.setHgap(20);
		 grid.setAlignment(Pos.CENTER);
		 
		 insertBtn = new Button("insert distrct") ;
		 insertLabel = new Label("enter the name of district:");
		 insertTxt = new TextField();
		 insertResult = new Label();
		
		 combo = new ComboBox<>();
		 combo.setPrefWidth(100);
         combo.setPromptText("Districts");
		
		
		 deleteBtn = new Button("delete district") ;
		 deleteLabel = new Label("Choose the district from the\n following comboBox");
		 deleteResult = new Label();
		
		 updateBtn = new Button("update district") ;
		 updateLabel = new Label("choose the district from the\n above comboBox and enter\nthe new name");
		 updateTxt = new TextField();
		 updateResult = new Label();
		
		 prev = new Button("previous");
		 next = new Button("next");
		 numOfMArtyrsLbl = new Label();
		 locationScreenBtn = new Button("Load location screen");
		 locationScreenLbl = new Label();
		 
		 nameOfDistrict = new Label();
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
		 grid.add(nameOfDistrict, 1, 4);
		 grid.add(next, 2, 4);
		 grid.add(numOfMArtyrsLbl, 1, 5);
		 grid.add(locationScreenBtn, 1, 6);
		 grid.add(locationScreenLbl, 1, 7);
		 grid.add(back, 1, 8);
		 
	}

    public void insertAction() {
    	if(!insertTxt.getText().isEmpty()) {
    		District newDistrict = new District(insertTxt.getText());
    		if(Main.districts.find(newDistrict) == null) {
    			Main.districts.insert(newDistrict);
    			insertResult.setText("proccess is done");
    		}else {
    			insertResult.setText("The district is already exist");
    		}
    	}else {
    		insertResult.setText("Enter the name of district");
    	}
    }
    
    public District deleteAction() {
    	if(combo.getValue() != null) {
    		deleteResult.setText("proccess Done");
    		  return Main.districts.delete(new District(combo.getValue()));
    		  
    	}else {
    		deleteResult.setText("choose the district");
    		return null;
    	}
    }
    
    public void updateAction() {
    	if(!updateTxt.getText().isEmpty() && combo.getValue() != null) {
    		District district = new District(updateTxt.getText());
    		if(Main.districts.find(district) == null) {
    			District original = Main.districts.find(new District(combo.getValue())).getData();
    			Main.districts.delete(original);
    			original.setDistrict(district.getDistrict());
    			Main.districts.insert(original);
    			updateResult.setText("proccess done");
    		}else {
    			updateResult.setText("The new name is a name\n for another district");
    		}
    	}else {
    		updateResult.setText("Error! you don`t choose the district\nor you don`t enter the new name");
    	}
    }
    public void clear() {
    	
    	insertTxt.setText("");;
    	insertResult.setText("");;
    	deleteResult.setText("");;
    	updateTxt.setText("");;
    	updateResult.setText("");;
    	numOfMArtyrsLbl.setText("");;
    	locationScreenLbl.setText(null);;
    	nameOfDistrict.setText(null);;
    	
    }
	public GridPane getGrid() {
		return grid;
	}


	public void setGrid(GridPane grid) {
		this.grid = grid;
	}


	public Button getInsertBtn() {
		return insertBtn;
	}


	public void setInsertBtn(Button insertBtn) {
		this.insertBtn = insertBtn;
	}


	public Label getInsertLabel() {
		return insertLabel;
	}


	public void setInsertLabel(Label insertLabel) {
		this.insertLabel = insertLabel;
	}


	public TextField getInsertTxt() {
		return insertTxt;
	}


	public void setInsertTxt(TextField insertTxt) {
		this.insertTxt = insertTxt;
	}


	public Label getInsertResult() {
		return insertResult;
	}


	public void setInsertResult(Label insertResult) {
		this.insertResult = insertResult;
	}


	public ComboBox<String> getCombo() {
		return combo;
	}


	public void setCombo(ComboBox<String> combo) {
		this.combo = combo;
	}


	public Button getDeleteBtn() {
		return deleteBtn;
	}


	public void setDeleteBtn(Button deleteBtn) {
		this.deleteBtn = deleteBtn;
	}


	public Label getDeleteLabel() {
		return deleteLabel;
	}


	public void setDeleteLabel(Label deleteLabel) {
		this.deleteLabel = deleteLabel;
	}


	public Label getDeleteResult() {
		return deleteResult;
	}


	public void setDeleteResult(Label deleteResult) {
		this.deleteResult = deleteResult;
	}


	public Button getUpdateBtn() {
		return updateBtn;
	}


	public void setUpdateBtn(Button updateBtn) {
		this.updateBtn = updateBtn;
	}


	public Label getUpdateLabel() {
		return updateLabel;
	}


	public void setUpdateLabel(Label updateLabel) {
		this.updateLabel = updateLabel;
	}


	public Label getUpdateResult() {
		return updateResult;
	}


	public void setUpdateResult(Label updateResult) {
		this.updateResult = updateResult;
	}


	public Button getPrev() {
		return prev;
	}


	public void setPrev(Button prev) {
		this.prev = prev;
	}


	public Button getNext() {
		return next;
	}


	public void setNext(Button next) {
		this.next = next;
	}


	public Label getNumOfMArtyrsLbl() {
		return numOfMArtyrsLbl;
	}


	public void setNumOfMArtyrsLbl(Label numOfMArtyrsLbl) {
		this.numOfMArtyrsLbl = numOfMArtyrsLbl;
	}


	public Button getLocationScreenBtn() {
		return locationScreenBtn;
	}


	public void setLocationScreenBtn(Button locationScreenBtn) {
		this.locationScreenBtn = locationScreenBtn;
	}

	public Label getNameOfDistrict() {
		return nameOfDistrict;
	}

	public Label getLocationScreenLbl() {
		return locationScreenLbl;
	}

	public Tree<District> getDistricts() {
		return Main.districts;
	}

	public TextField getUpdateTxt() {
		return updateTxt;
	}

	public Button getBack() {
		return back;
	}
	
}
