package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class NameOfMartyrTable {
	private ObservableList<NameOfMartyr> martyrs;
	private BorderPane root = new BorderPane();
	private LinkedList<NameOfMartyr> list;
	
	public NameOfMartyrTable(LinkedList<NameOfMartyr> list){
		this.list = list;
		 TableView<NameOfMartyr> table = new TableView<>();

         TableColumn<NameOfMartyr, String> nameCol = new TableColumn<>("Name");
         nameCol.setMinWidth(200);
         nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

         TableColumn<NameOfMartyr, Integer> ageCol = new TableColumn<>("Age");
         ageCol.setMinWidth(100);
         ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

         TableColumn<NameOfMartyr, Character> genderCol = new TableColumn<>("Gender");
         genderCol.setMinWidth(50);
         genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));

         table.setItems(getMartyrs());
         table.getColumns().addAll(nameCol, ageCol, genderCol);
         root.setCenter(table);
	}
	 private ObservableList<NameOfMartyr> getMartyrs() {
	        martyrs = FXCollections.observableArrayList();
	        Node<NameOfMartyr> current = list.getHead();
	        while (current != null) {
	            martyrs.add(current.getData());
	            current = current.getNext();
	        }

	        return martyrs;
	    }
	public BorderPane getRoot() {
		return root;
	}
	public LinkedList<NameOfMartyr> getList() {
		return list;
	}
	 
}
