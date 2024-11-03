package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	static Tree<District> districts = new Tree<>(); // the list of districts
	static Node<District> currDistrict = null;
	static Node<Location> currLocation = null;
	static LinkedStack<District> stack = new LinkedStack<>();
	static LinkedStack<District> temp = new LinkedStack<>();
	static LinkedStack<Location> stackLoc = new LinkedStack<>();
	static LinkedStack<Location> tempLoc = new LinkedStack<>();
	static LinkedStack<DateOfMartyrs> stackDates = new LinkedStack<>();
	static LinkedStack<DateOfMartyrs> tempDates = new LinkedStack<>();
	static Node<DateOfMartyrs> currDate = null;
	static DistrictScreen disSc = new DistrictScreen();
	static LocationScreen locSc = new LocationScreen(null);
	static DateScreen dateSc = new DateScreen(null);
	static Scene disScene = new Scene(disSc.getGrid(), 700, 700);
	static Scene locScene = new Scene(locSc.getGrid(), 700, 700);
	static Scene sceneDate = new Scene(dateSc.getGrid(), 800, 800);

	@Override
	public void start(Stage primaryStage) {
		try {

			Button chooseFile = new Button("choos file and upload data"); // this button is to choose file
			Button loadDisSc = new Button("Load district screen");
			Button save = new Button("save");
			GridPane grid = new GridPane();
			grid.add(chooseFile, 0, 0);
			grid.add(loadDisSc, 0, 1);
			grid.add(save, 0, 2);

			Scene scene = new Scene(grid, 700, 700);
			grid.setAlignment(Pos.CENTER);
			grid.setVgap(10);
			grid.setHgap(10);

			primaryStage.setScene(scene);
			primaryStage.setTitle("choose file");
			primaryStage.show();

			// read from file
			chooseFile.setOnAction(e -> { // this action to Let the user to choose the file
				FileChooser filechooser = new FileChooser(); // create a file chooser
				filechooser.setTitle("Choose file"); // title of file chooser
				filechooser.setInitialDirectory(new File("C:\\")); // the initial directory when the file chooser opened
				// the type of files appears on file chooser
				filechooser.getExtensionFilters().addAll(new ExtensionFilter("csv files", "*.csv"));
				File selectedFile = filechooser.showOpenDialog(primaryStage);
				if (selectedFile != null) { // if the selected file not null
					try {
						Scanner sc = new Scanner(selectedFile); // read the information of martyrs from the file
						while (sc.hasNext()) {
							String[] line = sc.nextLine().split(",");
							if (line.length == 6) { // if the lines contains all information read to store
								if (Character.toUpperCase(line[5].charAt(0)) == 'F'
										|| Character.toUpperCase(line[5].charAt(0)) == 'M') { // if the gender F or M
																								// store the martyr
									String[] date = line[1].split("/");
									if (date.length == 3) { // check the date
										try {
											int age = Integer.parseInt(line[2]);
											if (age > 0 && age < 150) {
												// create a date from the information
												Date dateOfDeath = new Date(Integer.parseInt(date[2]) - 1900,
														Integer.parseInt(date[0]) - 1, Integer.parseInt(date[1]));
												Martyr martyr = new Martyr(line[0], age, line[5].charAt(0)); // create a
																												// martyr
																												// from
																												// the
																												// information
												// check if the district does not exist , insert it
												District newDistrict = new District(line[4]);
												if (districts.find(newDistrict) == null) {
													districts.insert(newDistrict);
												}
												newDistrict = districts.find(newDistrict).getData();
												// check if the location does not exist , insert it
												Tree<Location> locations = newDistrict.getLocations();
												Location newLocation = new Location(line[3]);
												if (locations.find(newLocation) == null) {
													locations.insert(newLocation);
												}
												newLocation = locations.find(newLocation).getData();
												Tree<DateOfMartyrs> dateOfMArtyrs = newLocation.getDates();
												DateOfMartyrs newDateOfMartyr = new DateOfMartyrs(dateOfDeath);
												if (newLocation.getDates().find(newDateOfMartyr) == null) {
													newLocation.getDates().insert(newDateOfMartyr);
												}
												newDateOfMartyr = newLocation.getDates().find(newDateOfMartyr)
														.getData();
												newDateOfMartyr.getMartyrs().insert(martyr);

											}
										} catch (Exception ex) {

										}
									}
								}
							}
						}
						traverseInOrder();
						loadToStack(stack);

						
					} catch (FileNotFoundException e1) {

					}
				}

			});
			loadDisSc.setOnAction(e -> {
				loadDistrictsToCombo();
				primaryStage.setScene(disScene);
				primaryStage.setTitle("District Screen");
			});
			disSc.getInsertBtn().setOnAction(e -> {
				disSc.insertAction();
				disSc.getCombo().getItems().clear();
				loadDistrictsToCombo();
				reLoadToStack();
			});
			disSc.getDeleteBtn().setOnAction(e -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Are you sure you want to delete this ditrict?");
				alert.setContentText("You will lose all the locations and data");
				alert.showAndWait();
				if (alert.getResult().getText().equalsIgnoreCase("OK")) {
					District deleted = disSc.deleteAction();
					disSc.getCombo().getItems().clear();
					if (currDistrict != null) {
						if (currDistrict.getData().equals(deleted))
							currDistrict = temp.pop();
					}
					loadDistrictsToCombo();
					
					reLoadToStack();
				}
			});
			disSc.getUpdateBtn().setOnAction(e -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Are you sure you want to update this ditrict?");
				alert.setContentText("You cannot undo this action");
				alert.showAndWait();
				if (alert.getResult().getText().equalsIgnoreCase("OK")) {
					disSc.updateAction();
					disSc.getCombo().getItems().clear();
					loadDistrictsToCombo();
					//
					reLoadToStack();
					disSc.getCombo().setPromptText("Districts");
				}
			});
			disSc.getNext().setOnAction(e -> {
				if (currDistrict != null)
					temp.push(currDistrict.getData());
				if (!stack.isEmpty()) {

					currDistrict = stack.pop();
					disSc.getNameOfDistrict().setText(currDistrict.getData().getDistrict());
					disSc.getNumOfMArtyrsLbl()
							.setText("Total number of martyr: " + currDistrict.getData().numOfMartyrs());
					// temp.push(currDistrict.getData());
				} else {
					currDistrict = null;
					disSc.getNameOfDistrict().setText("");
					disSc.getNumOfMArtyrsLbl().setText("no more ");
				}
			});
			disSc.getPrev().setOnAction(e -> {
				if (!temp.isEmpty()) {
					if (currDistrict != null)
						stack.push(currDistrict.getData());
					currDistrict = temp.pop();
					disSc.getNameOfDistrict().setText(currDistrict.getData().getDistrict());
					disSc.getNumOfMArtyrsLbl()
							.setText("Total number of martyr: " + currDistrict.getData().numOfMartyrs());

				}

			});

			disSc.getLocationScreenBtn().setOnAction(e -> {
				if (currDistrict != null) {
					locSc.setDistrict(currDistrict.getData());
					loadLocationsToCombo();
					primaryStage.setScene(locScene);
					primaryStage.setTitle("Location Screen");
					loadLocationsToStack(stackLoc, currDistrict);
					stackLoc.print();

				} else {
					disSc.getLocationScreenLbl().setText("There is no district");
				}
			});
			disSc.getBack().setOnAction(e -> {
				primaryStage.setScene(scene);
				primaryStage.setTitle("choose file");
				stack.clear();
				temp.clear();
				currDistrict = null;
				loadToStack(stack);
				disSc.clear();
				disSc.getCombo().getItems().clear();
			});
			locSc.getInsertBtn().setOnAction(e -> {
				locSc.insertAction();
				locSc.getCombo().getItems().clear();
				loadLocationsToCombo();
				reLoadLocationsToStack();

			});
			locSc.getDeleteBtn().setOnAction(e -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Are you sure you want to delete this location?");
				alert.setContentText("You will lose all the martyrs information");
				alert.showAndWait();
				if (alert.getResult().getText().equalsIgnoreCase("OK")) {
					Location deleted = locSc.deleteAction();
					locSc.getCombo().getItems().clear();
					if (currLocation != null) {
						if (currLocation.getData().equals(deleted))
							currLocation = tempLoc.pop();
					}
					loadLocationsToCombo();
					reLoadLocationsToStack();
				}

			});
			locSc.getUpdateBtn().setOnAction(e -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Are you sure you want to update this location?");
				alert.setContentText("You cannot undo this action");
				alert.showAndWait();
				if (alert.getResult().getText().equalsIgnoreCase("OK")) {
					locSc.updateAction();
					locSc.getCombo().getItems().clear();
					loadLocationsToCombo();
					reLoadLocationsToStack();
				}

			});

			locSc.getNext().setOnAction(e -> {
			    if (currLocation != null) {
			        tempLoc.push(currLocation.getData());
			    }
			    if (!stackLoc.isEmpty()) {
			        currLocation = stackLoc.pop();
			        locSc.informationCurrLocAction(currLocation.getData());
			    } else {
			        currLocation = null;
			        locSc.getInfo().setText("no more locations");
			    }
			});

			locSc.getPrev().setOnAction(e -> {
			    if (!tempLoc.isEmpty()) {
			        if (currLocation != null) {
			            stackLoc.push(currLocation.getData());
			        }
			        currLocation = tempLoc.pop();
			        locSc.informationCurrLocAction(currLocation.getData());
			    }
			});
			locSc.getMartyrScreenBtn().setOnAction(e -> {
				if (currLocation != null) {
					dateSc.setLocation(currLocation.getData());
					loadDatesToStack(stackDates, currLocation);
					primaryStage.setScene(sceneDate);
					primaryStage.setTitle("Martyr Screen");
				}
			});
			locSc.getBack().setOnAction(e -> {
				primaryStage.setScene(disScene);
				primaryStage.setTitle("District Screen");
				stackLoc.clear();
				tempLoc.clear();
				currLocation = null;
				// loadLocationsToStack(stackLoc,currDistrict);
				locSc.clear();
				locSc.getCombo().getItems().clear();
			});
			dateSc.getInsertBtn().setOnAction(e -> {
				dateSc.insertMartyrAction();
				reLoadDatesToStack();

			});
			dateSc.getDeleteBtn().setOnAction(e -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Are you sure you want to delete this martyr?");
				alert.setContentText("You will lose all  data");
				alert.showAndWait();
				if (alert.getResult().getText().equalsIgnoreCase("OK")) {
					if(currDate != null) {
					System.out.println(currDate.getData().toString());
					dateSc.deleteMartyrAction(currDate.getData());
					System.out.println(currDate.getData().toString());
					}else {
						dateSc.getDeleteResult().setText("There is no date");
					}
				}
				
			});
			dateSc.getUpdateBtn().setOnAction(e -> {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Confirmation Dialog");
				alert.setHeaderText("Are you sure you want to delete this martyr?");
				alert.setContentText("You will lose all  data");
				alert.showAndWait();
				if (alert.getResult().getText().equalsIgnoreCase("OK")) {
					if(currDate != null) {
					System.out.println(currDate.getData().toString());
					dateSc.updateMartyrAction(currDate.getData());
					System.out.println(currDate.getData().toString());
					}else {
						dateSc.getUpdateResult().setText("There is no date");
					}
				}
				
			});
			dateSc.getNext().setOnAction(e -> {

				if (currDate != null)
					tempDates.push(currDate.getData());
				if (!stackDates.isEmpty()) {
					currDate = stackDates.pop();
					dateSc.informationCurrDateAction(currDate.getData());
				} else {
					currDate = null;
					dateSc.getInfo().setText("no more");
				}
			});
			dateSc.getPrev().setOnAction(e -> {
				if (!tempDates.isEmpty()) {
					if (currDate != null)
						stackDates.push(currDate.getData());
					currDate = tempDates.pop();
					dateSc.informationCurrDateAction(currDate.getData());
					
				}
			});
			dateSc.getSearchBtn().setOnAction(e -> {

				dateSc.searchAboutMartyrsByName(currDate.getData());
			});
			dateSc.getBack().setOnAction(e -> {
				primaryStage.setScene(locScene);
				primaryStage.setTitle("Location Screen");
				stackDates.clear();
				tempDates.clear();
				currDate = null;
				// loadLocationsToStack(stackLoc,currDistrict);
				dateSc.clear();
			});
			dateSc.getMartyrsByname().setOnAction(e -> {
				if(currDate != null) {
					dateSc.showMartyrsinTableAction();
				}else {
					dateSc.getMartyrsBynameLbl().setText("There is no date");
				}
			});
			save.setOnAction(e -> {
				saveAction();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void traverseInOrder() {
		traverseInOrder(districts.getRoot());
		System.out.println();
	}

	private static void traverseInOrder(TNode<District> node) {
		if (node != null) {
			if (node.getLeft() != null)
				traverseInOrder(node.getLeft());
			System.out.println(node + "  numOfMartyrs:" + node.getData().numOfMartyrs());
			if (node.getRight() != null)
				traverseInOrder(node.getRight());
		}

	}

	public static String printDate(Date date) {
		if (date != null)
			return (date.getMonth() + 1) + "/" + (date.getDate()) + "/" + (date.getYear() + 1900);
		else
			return "null";
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loadDistrictsToCombo() {
		loadDistrictsToCombo(districts.getRoot());
	}

	private void loadDistrictsToCombo(TNode<District> node) {
		if (node != null) {
			if (node.getLeft() != null)
				loadDistrictsToCombo(node.getLeft());
			disSc.getCombo().getItems().add(node.getData().getDistrict());
			if (node.getRight() != null)
				loadDistrictsToCombo(node.getRight());
		}

	}

	public void loadLocationsToCombo() {
		loadLocationsToCombo(currDistrict.getData().getLocations().getRoot());
	}

	private void loadLocationsToCombo(TNode<Location> node) {
		if (node != null) {
			if (node.getLeft() != null)
				loadLocationsToCombo(node.getLeft());
			locSc.getCombo().getItems().add(node.getData().getLocation());
			if (node.getRight() != null)
				loadLocationsToCombo(node.getRight());
		}

	}

	public static void loadToStack(LinkedStack<District> newStack) {
		LinkedStack<District> tempStack = new LinkedStack<>();
		loadToStack(tempStack, districts.getRoot());
		loadToStack2(tempStack, newStack);

	}

	private static void loadToStack(LinkedStack<District> tempStack, TNode<District> node) {
		if (node != null) {
			if (node.getLeft() != null)
				loadToStack(tempStack, node.getLeft());
			tempStack.push(node.getData());
			if (node.getRight() != null)
				loadToStack(tempStack, node.getRight());
		}

	}

	public static void loadToStack2(LinkedStack<District> tempStack, LinkedStack<District> newStack) {
		while (!tempStack.isEmpty()) {
			newStack.push(tempStack.pop().getData());
		}
	}

	public static void reLoadToStack() {
		
		stack.clear();
		temp.clear();
		loadToStack(stack);
		stack.print();
		
		if (currDistrict == null) {
			while (!stack.isEmpty()) {
				temp.push(stack.pop().getData());
			}
		} else {
			while (!stack.isEmpty() && !stack.peek().getData().equals(currDistrict.getData())) {
				temp.push(stack.pop().getData());
			}
		}
		currDistrict = stack.pop();
		
	}

	public static void reLoadLocationsToStack() {
		stackLoc.clear();
		tempLoc.clear();
		loadLocationsToStack(stackLoc, currDistrict);
		stackLoc.print();
		
		if (currLocation == null) {
			while (!stackLoc.isEmpty()) {
				tempLoc.push(stackLoc.pop().getData());
			}
		} else {
			while (!stackLoc.isEmpty() && !stackLoc.peek().getData().equals(currLocation.getData())) {
				tempLoc.push(stackLoc.pop().getData());
			}
		}
		currLocation = stackLoc.pop();
		// System.out.println(stack.peek());
	}


	public static void reLoadDatesToStack() {
		stackDates.clear();
		tempDates.clear();
		loadDatesToStack(stackDates, currLocation);
		stackDates.print();
		
		if (currDate == null) {
			while (!stackDates.isEmpty()) {
				tempDates.push(stackDates.pop().getData());
			}
		} else {
			while (!stackDates.isEmpty() && !stackDates.peek().getData().equals(currDate.getData())) {
				tempDates.push(stackDates.pop().getData());
			}
		}
		currDate = stackDates.pop();
		
	}

	public static void loadLocationsToStack(LinkedStack<Location> stackLocation, Node<District> districtNode) {
		loadLocationsToStack(stackLocation, districtNode, districtNode.getData().getLocations().getRoot());
		stackLocation.print();
		districtNode.getData().getLocations().traverseInOrder();
	}

	public static void loadLocationsToStack(LinkedStack<Location> stackLocation, Node<District> districtNode,
			TNode<Location> node) {
		Tree<Location> treeLocations = districtNode.getData().getLocations();
		LinkedStack<Location> tempLoc = new LinkedStack<>();
		if (districtNode != null) {
			int numOfLevels = treeLocations.height();
			for (int i = 1; i <= numOfLevels; i++) {
				searchAndAdd(tempLoc, treeLocations, treeLocations.getRoot(), i);
			}
		}
		while (!tempLoc.isEmpty()) {
			stackLocation.push(tempLoc.pop().getData());
		}
	}

	public static void searchAndAdd(LinkedStack<Location> stack, Tree<Location> treeLocations, TNode<Location> node,
			int level) {
		if (node != null) {
			if (node.getLeft() != null)
				searchAndAdd(stack, treeLocations, node.getLeft(), level);
			if (treeLocations.height(node.getData()) == level)
				stack.push(node.getData());
			if (node.getRight() != null)
				searchAndAdd(stack, treeLocations, node.getRight(), level);
		}
	}

	public static void loadDatesToStack(LinkedStack<DateOfMartyrs> stackOfDates, Node<Location> locationNode) {
		loadDatesToStack(stackOfDates, locationNode.getData().getDates().getRoot());
		loadDatesToStack2(stackOfDates);

	}

	private static void loadDatesToStack(LinkedStack<DateOfMartyrs> stackOfDates, TNode<DateOfMartyrs> node) {
		if (node != null) {
			if (node.getLeft() != null)
				loadDatesToStack(stackOfDates, node.getLeft());
			tempDates.push(node.getData());
			if (node.getRight() != null)
				loadDatesToStack(stackOfDates, node.getRight());
		}

	}

	public static void loadDatesToStack2(LinkedStack<DateOfMartyrs> stackOfDates) {
		while (!tempDates.isEmpty()) {
			stackOfDates.push(tempDates.pop().getData());
		}
	}

	public void saveAction() {
		try {
			PrintWriter pw = new PrintWriter("savedFile.csv");
			pw.print("Name");
			pw.print(",");
			pw.print("Event");
			pw.print(",");
			pw.print("Age");
			pw.print(",");
			pw.print("Location");
			pw.print(",");
			pw.print("District");
			pw.print(",");
			pw.print("Gender");
			pw.print("\n");

			LinkedStack<District> stackDistricts = new LinkedStack<>();
			loadToStack(stackDistricts);
			stackDistricts.print();

			while (!stackDistricts.isEmpty()) {
				LinkedStack<Location> stackLocations = new LinkedStack<>();
				Node<District> nodeDistrict = stackDistricts.pop();
				loadLocationsToStack(stackLocations, nodeDistrict);
				while (!stackLocations.isEmpty()) {
					LinkedStack<DateOfMartyrs> stackOfDates = new LinkedStack<>();
					Node<Location> nodeLocation = stackLocations.pop();
					loadDatesToStack(stackOfDates, nodeLocation);
					while (!stackOfDates.isEmpty()) {
						DateOfMartyrs date = stackOfDates.pop().getData();
						Node<Martyr> curr = date.getMartyrs().getHead();
						while (curr != null) {
							pw.print(curr.getData().getName());
							pw.print(",");
							pw.print(date.printDate());
							pw.print(",");
							pw.print(curr.getData().getAge());
							pw.print(",");
							pw.print(nodeLocation.getData().getLocation());
							pw.print(",");
							pw.print(nodeDistrict.getData().getDistrict());
							pw.print(",");
							pw.print(curr.getData().getGender());
							pw.print("\n");
							curr = curr.getNext();
						}
					}
				}

			}
			pw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

	}
}
