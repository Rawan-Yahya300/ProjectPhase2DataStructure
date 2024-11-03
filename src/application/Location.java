package application;


import java.util.Date;

public class Location implements Comparable<Location> {

	private String location; // name of location
	private Tree<DateOfMartyrs> dates; //tree of dates

	public Location(String location) { // constructor
		this.location = location;
		dates = new Tree<>();
	}

	@Override
	public int compareTo(Location o) { // CopmpareTo according to name of location
		return location.compareToIgnoreCase(o.getLocation());
	}

	public String toString() { // toString
		return location;
	}
	public int numOfMartyrs() { // calculate number of martyrs
		 
		return numOfMartyrs(dates.getRoot());
	}
	private int numOfMartyrs(TNode<DateOfMartyrs> node) {
		if(node == null)
			return 0;
		return node.getData().getMartyrs().length() + numOfMartyrs(node.getLeft()) + numOfMartyrs(node.getRight());
		
	}


	public int numOfMaleMartyrs() { // calculate number of martyrs
		 
		return numOfMaleMartyrs(dates.getRoot());
	}
	private int numOfMaleMartyrs(TNode<DateOfMartyrs> node) {
		if(node == null)
			return 0;
		return node.getData().numOfMaleMartyrs() + numOfMaleMartyrs(node.getLeft()) + numOfMaleMartyrs(node.getRight());
		
	}
	
	public int numOfFemaleMartyrs() { // calculate number of martyrs
		 
		return numOfFemaleMartyrs(dates.getRoot());
	}
	private int numOfFemaleMartyrs(TNode<DateOfMartyrs> node) {
		if(node == null)
			return 0;
		return node.getData().numOfFemaleMartyrs() + numOfFemaleMartyrs(node.getLeft()) + numOfFemaleMartyrs(node.getRight());
		
	}
	public Date earliestDate() {
		TNode<DateOfMartyrs> date = dates.smallest();
		if(date != null)
		return dates.smallest().getData().getDate();
		else
			return null;
	}
	public Date latestDate() {
		TNode<DateOfMartyrs> date = dates.largest();
		if(date != null)
		return dates.largest().getData().getDate();
		else
			return null;
	}


	// getters and setters
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Tree<DateOfMartyrs> getDates() {
		return dates;
	}

	 public Tree<Date_numOfMartyr> dates(){
	    	Tree<Date_numOfMartyr> tree = new Tree<>();
	    	 return dates(tree, dates.getRoot());
	     }
	     private Tree<Date_numOfMartyr> dates(Tree<Date_numOfMartyr> tree, TNode<DateOfMartyrs> node){
	    	 if(node != null) {
	    		 tree.insert(new Date_numOfMartyr(node.getData().getDate(), node.getData().getMartyrs().length()));
	    		 dates(tree,node.getLeft());
	    		 dates(tree,node.getRight());
	    	 }
	    		 
	    	 return tree;
	     }

	     public Date dateHasMaxNumOfMartyrs() { // calculate the date that has the maximum number of martyrs

	 		Tree<Date_numOfMartyr> listDates = dates(); // make a list from all dates in this district
	 		TNode<Date_numOfMartyr> larg = listDates.largest();
	 		Date date = null;//listDates.largest().getData().getDateOFDeath();
	 		if(larg != null) {
	 			Date_numOfMartyr obj = larg.getData();
	 			if(obj != null)
	 				date = obj.getDateOFDeath();
	 		}
	 		return date;

	 	}
}
