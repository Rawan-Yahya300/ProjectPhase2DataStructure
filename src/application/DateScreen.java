package application;

import java.time.LocalDate;
import java.util.Date;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DateScreen {

	private GridPane grid;
	private Location location;
	private Button insertBtn;
	private Label insertLbl;
	private TextField insertTxt;
	private DatePicker datePicker;
	private Label insertResult;

	private Button next;
	private Button prev;
	private Label info;
	private Button martyrsByname;
	private Label martyrsBynameLbl;

	private Button updateBtn;
	private Label updateLbl;
	private TextField updatedTxt;
	private TextField newMartyrTxt;
	private Label updateResult;

	private Button deleteBtn;
	private Label deleteLbl;
	private TextField deleteTxt;
	private Label deleteResult;

	private Button searchBtn;
	private Label searchLbl;
	private TextField searchTxt;
	private Label martyrs;
	

	private Button back;
	
	LinkedList<NameOfMartyr> newLink = new LinkedList<>();
	
	public DateScreen(Location location) {
		this.location = location;
		grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		insertBtn = new Button("insert Martyr");
		insertLbl = new Label("Enter name,age,gender\nin the following TextField\nand the date in the DatePicker");
		insertTxt = new TextField();
		datePicker = new DatePicker();
		datePicker.setEditable(false);
		insertResult = new Label();

		next = new Button("next");
		prev = new Button("previous");
		info = new Label();
		martyrsByname = new Button("Show martyrs in TableView");
		martyrsBynameLbl = new Label();

		updateBtn = new Button("update martyr");
		updateLbl = new Label("Enter the name of martyr\nand the new information\n(name,age,gender)");
		updatedTxt = new TextField();
		newMartyrTxt = new TextField();
		updateResult = new Label();

		deleteBtn = new Button("delete martyr");
		deleteLbl = new Label("Enter the name of martyr");
		deleteTxt = new TextField();
		deleteResult = new Label();

		searchBtn = new Button("search about martyrs");
		searchLbl = new Label("Enter part of name:");
		searchTxt = new TextField();
		martyrs = new Label();
		
		back = new Button("back");
		

		grid.add(insertBtn, 0, 0);
		grid.add(insertLbl, 1, 0);
		grid.add(insertTxt, 2, 0);
		grid.add(datePicker, 3, 0);
		grid.add(insertResult, 4, 0);

		grid.add(next, 3, 2);
		grid.add(prev, 1, 2);
		grid.add(info, 2, 3);
		grid.add(martyrsByname, 2, 4);
		grid.add(martyrsBynameLbl, 3, 4);

		grid.add(updateBtn, 0, 5);
		grid.add(updateLbl, 1, 5);
		grid.add(updatedTxt, 2, 5);
		grid.add(newMartyrTxt, 3, 5);
		grid.add(updateResult, 4, 5);

		grid.add(deleteBtn, 0, 6);
		grid.add(deleteLbl, 1, 6);
		grid.add(deleteTxt, 2, 6);
		grid.add(deleteResult, 3, 6);

		grid.add(searchBtn, 0, 7);
		grid.add(searchLbl, 1, 7);
		grid.add(searchTxt, 2, 7);
		grid.add(martyrs, 3, 7);
		
		grid.add(back, 2, 8);
	}

	public void insertMartyrAction() {
		location.getDates().traverseInOrder();
		LocalDate localDate = datePicker.getValue();
		if (!insertTxt.getText().isEmpty() && localDate != null) {
			String[] information = insertTxt.getText().split(",");
			if (information.length == 3) {
				Date date = new Date(localDate.getYear() - 1900, localDate.getMonth().getValue() - 1,
						localDate.getDayOfMonth());
				DateOfMartyrs dateOfMartyrs = new DateOfMartyrs(date);
				if (location.getDates().find(dateOfMartyrs) == null) {
					location.getDates().insert(dateOfMartyrs);
				}
				try {
					int age = Integer.parseInt(information[1]);
					if (age > 0 && age < 150) {
						char gender = information[2].charAt(0);
						if (Character.toUpperCase(gender) == 'F' || Character.toUpperCase(gender) == 'M') {
							Martyr martyr = new Martyr(information[0], age, gender);
							location.getDates().find(dateOfMartyrs).getData().getMartyrs().insert(martyr);
							insertResult.setText("process done");
						} else {
							insertResult.setText("The gender should be F/M");
						}
					} else {
						insertResult.setText("logical error in the age");
					}
				} catch (Exception ex) {
					insertResult.setText("Error! the age should be integer");
				}
			} else {
				insertResult.setText("Error! information not completed");
			}
		} else {
			insertResult.setText("Error! you don`t enter the information or the date");
		}
		location.getDates().traverseInOrder();
	}

	public void deleteMartyrAction(DateOfMartyrs dateOfMartyrs) {
		System.out.println(dateOfMartyrs.toString());
		if (!deleteTxt.getText().isEmpty()) {
			LinkedList<NameOfMartyr> newLink = new LinkedList<>();
			Node<Martyr> curr = dateOfMartyrs.getMartyrs().getHead();
			while (curr != null) {
				NameOfMartyr nameOfMartyr = new NameOfMartyr(curr.getData().getName(), curr.getData().getAge(),
						curr.getData().getGender());
				newLink.insert(nameOfMartyr);
				curr = curr.getNext();
			}
			String name = deleteTxt.getText();
			NameOfMartyr nameOfMartyr = new NameOfMartyr(name);
			Node<NameOfMartyr> searchedNameOfMArtyr = newLink.delete(nameOfMartyr);
			if (searchedNameOfMArtyr != null) {
				deleteResult.setText("Process done");
			} else {
				deleteResult.setText("The martyr does not exist");
			}
			Node<NameOfMartyr> currNameOfMartyr = newLink.getHead();
			LinkedList<Martyr> newMartyrs = new LinkedList<>();
			while (currNameOfMartyr != null) {
				Martyr martyr = new Martyr(currNameOfMartyr.getData().getName(), currNameOfMartyr.getData().getAge(),
						currNameOfMartyr.getData().getGender());
				newMartyrs.insert(martyr);
				currNameOfMartyr = currNameOfMartyr.getNext();
			}
			dateOfMartyrs.setMartyrs(newMartyrs);
			System.out.println(dateOfMartyrs.toString());
		} else {
			deleteResult.setText("Enter the name of martyr");
		}
	}

	public void updateMartyrAction(DateOfMartyrs dateOfMartyrs) {
		System.out.println(dateOfMartyrs.toString());
		if (!updatedTxt.getText().isEmpty() && !newMartyrTxt.getText().isEmpty()) {
			String[] newInfo = newMartyrTxt.getText().split(",");
			if (newInfo.length == 3) {
				try {
					int age = Integer.parseInt(newInfo[1]);
					if (age > 0 && age < 150) {
						char gender = newInfo[2].charAt(0);
						if (Character.toUpperCase(gender) == 'F' || Character.toUpperCase(gender) == 'M') {
							NameOfMartyr newNameOfMartyr = new NameOfMartyr(newInfo[0], age, gender);
							LinkedList<NameOfMartyr> newLink = new LinkedList<>();
							Node<Martyr> curr = dateOfMartyrs.getMartyrs().getHead();
							while (curr != null) {
								NameOfMartyr nameOfMartyr = new NameOfMartyr(curr.getData().getName(),
										curr.getData().getAge(), curr.getData().getGender());
								newLink.insert(nameOfMartyr);
								curr = curr.getNext();
							}
							String name = updatedTxt.getText();
							NameOfMartyr nameOfMartyr = new NameOfMartyr(name);
							Node<NameOfMartyr> searchedNameOfMArtyr = newLink.delete(nameOfMartyr);
							if (searchedNameOfMArtyr != null) {
								newLink.insert(newNameOfMartyr);
								updateResult.setText("Process done");
							} else {
								updateResult.setText("The martyr does not exist");
							}
							Node<NameOfMartyr> currNameOfMartyr = newLink.getHead();
							LinkedList<Martyr> newMartyrs = new LinkedList<>();
							while (currNameOfMartyr != null) {
								Martyr martyr = new Martyr(currNameOfMartyr.getData().getName(),
										currNameOfMartyr.getData().getAge(), currNameOfMartyr.getData().getGender());
								newMartyrs.insert(martyr);
								currNameOfMartyr = currNameOfMartyr.getNext();
							}
							dateOfMartyrs.setMartyrs(newMartyrs);
							System.out.println(dateOfMartyrs.toString());
						} else {
							updateResult.setText("The gender should be M/F");
						}
					} else {
						updateResult.setText("logical error in age");
					}
				} catch (Exception ex) {
					updateResult.setText("Error! the age should be integer");
				}
			}else {
				updateResult.setText("You don`t enter all information");
			}
		} else {
			updateResult.setText("Error! you don`t Enter the name of martyr\nor the new information");
		}
	}

	public void informationCurrDateAction(DateOfMartyrs dateOfMartyrs) {
		 newLink = new LinkedList<>();
		Node<Martyr> curr = dateOfMartyrs.getMartyrs().getHead();
		while (curr != null) {
			NameOfMartyr nameOfMartyr = new NameOfMartyr(curr.getData().getName(), curr.getData().getAge(),
					curr.getData().getGender());
			newLink.insert(nameOfMartyr);
			curr = curr.getNext();
		}
		info.setText(dateOfMartyrs.printDate() + "\n" + "Average martyrs ages: " + dateOfMartyrs.averageMartyrsAge()
				+ "\nThe youngest martyr:\n " + dateOfMartyrs.youngestMartyr().toString() + "\nThe oldest martyr:\n "
				+ dateOfMartyrs.oldestMartyr().toString() + "\nmartyrs" + ":\n " + newLink.toString());
	}

	public void searchAboutMartyrsByName(DateOfMartyrs dateOfMartyrs) {
		if (!searchTxt.getText().isEmpty()) {
			LinkedList<Martyr> martyrsList = dateOfMartyrs.findMartyrUsingPartofName(searchTxt.getText());
			if (martyrsList.getHead() != null) {
				martyrs.setText(martyrsList.toString());
				MartyrTable martyrTable = new MartyrTable(martyrsList);
				Scene sceneTable = new Scene(martyrTable.getRoot());
				Stage stage = new Stage();
				stage.setScene(sceneTable);
				stage.show();
			}
			else
				martyrs.setText("There is no martyr for this part");
		} else {
			martyrs.setText("Enter part Of name");
		}

	}
       public void showMartyrsinTableAction() {
    	   NameOfMartyrTable table = new NameOfMartyrTable(newLink);
    	   Scene scene = new Scene(table.getRoot(),500,500);
    	   Stage stage = new Stage();
    	   stage.setScene(scene);
    	   stage.show();
       }
	public void clear() {
		insertTxt.setText("");;
		//datePicker.setT;
		insertResult.setText("");
		info.setText("");
		updatedTxt.setText("");
		newMartyrTxt.setText("");
		updateResult.setText("");
		deleteTxt.setText("");
		deleteResult.setText("");
		searchTxt.setText("");
		martyrs.setText("");
	}

	public GridPane getGrid() {
		return grid;
	}

	public Button getInsertBtn() {
		return insertBtn;
	}

	public Label getInsertLbl() {
		return insertLbl;
	}

	public TextField getInsertTxt() {
		return insertTxt;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public Label getInsertResult() {
		return insertResult;
	}

	public Button getNext() {
		return next;
	}

	public Button getPrev() {
		return prev;
	}

	public Label getInfo() {
		return info;
	}

	public Button getUpdateBtn() {
		return updateBtn;
	}

	public Label getUpdateLbl() {
		return updateLbl;
	}

	public TextField getUpdatedTxt() {
		return updatedTxt;
	}

	public TextField getNewMartyrTxt() {
		return newMartyrTxt;
	}

	public Label getUpdateResult() {
		return updateResult;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}

	public Label getDeleteLbl() {
		return deleteLbl;
	}

	public TextField getDeleteTxt() {
		return deleteTxt;
	}

	public Label getDeleteResult() {
		return deleteResult;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Button getSearchBtn() {
		return searchBtn;
	}

	public Label getSearchLbl() {
		return searchLbl;
	}

	public TextField getSearchTxt() {
		return searchTxt;
	}

	public Label getMartyrs() {
		return martyrs;
	}

	public Button getBack() {
		return back;
	}

	public Button getMartyrsByname() {
		return martyrsByname;
	}

	public LinkedList<NameOfMartyr> getNewLink() {
		return newLink;
	}

	public Label getMartyrsBynameLbl() {
		return martyrsBynameLbl;
	}

}
