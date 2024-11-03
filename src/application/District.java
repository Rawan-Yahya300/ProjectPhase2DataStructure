package application;



import java.util.Date;

public class District implements Comparable<District> {

	

	private String district; // name of district
	private Tree<Location> locations; // tree of locations

	public District(String district) { // constructor

		this.district = district;
		locations = new Tree<>();
	}
 
	// getters and setters
	
	public String getDistrict() { 
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Tree<Location> getLocations() {
		return locations;
	}

	public void setLocations(Tree<Location> location) {
		this.locations = location;
	}

	@Override
	public int compareTo(District o) { // compare to according to the name of district

		return district.compareToIgnoreCase(o.getDistrict());
	}

	public String toString() {
		return district;
	}



	public int numOfMartyrs() { // calculate number of martyrs
		 
		return numOfMartyrs(locations.getRoot());
	}
	private int numOfMartyrs(TNode<Location> node) {
		if(node == null)
			return 0;
		return node.getData().numOfMartyrs() + numOfMartyrs(node.getLeft()) + numOfMartyrs(node.getRight());
		
	}

}
