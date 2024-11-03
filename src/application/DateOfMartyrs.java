package application;

import java.util.Date;

public class DateOfMartyrs implements Comparable<DateOfMartyrs> {
 
	Date date ;
	LinkedList<Martyr> martyrs;
	
	
	public DateOfMartyrs(Date date) {
		
		this.date = date;
		this.martyrs = new LinkedList<>();
	}


	public Date getDate() {
		return date;
	}


	public LinkedList<Martyr> getMartyrs() {
		return martyrs;
	}


	@Override
	public int compareTo(DateOfMartyrs o) {
		// TODO Auto-generated method stub
		return date.compareTo(o.getDate());
	}
	
	public int numOfMaleMartyrs() {
		Node<Martyr> currMartyr = martyrs.getHead();
		int numOfMaleMartyrs = 0;
		while(currMartyr != null) {
			if(Character.toUpperCase(currMartyr.getData().getGender()) == 'M' )
				numOfMaleMartyrs++;
			currMartyr = currMartyr.getNext();
		}
		return numOfMaleMartyrs;
	}
	public int numOfFemaleMartyrs() {
		Node<Martyr> currMartyr = martyrs.getHead();
		int numOfFemaleMartyrs = 0;
		while(currMartyr != null) {
			if(Character.toUpperCase(currMartyr.getData().getGender()) == 'F' )
				numOfFemaleMartyrs++;
			currMartyr = currMartyr.getNext();
		}
		return numOfFemaleMartyrs;
	}
	 
	public int averageMartyrsAge() {
		int sum = 0;
		Node<Martyr> curr = martyrs.getHead();
		while(curr != null) {
			sum += curr.getData().getAge();
			curr = curr.getNext();
		}
		int numOfMartyrs = martyrs.length();
		if(numOfMartyrs != 0)
			return sum/numOfMartyrs;
		return 0;
	}
	
	public Martyr youngestMartyr() {
		if(martyrs.getHead() != null )
			return martyrs.getHead().getData();
		return null;
	}
	
	public Martyr oldestMartyr() {
		if(martyrs.getTail() != null )
			return martyrs.getTail().getData();
		return null;
	}
	
	public LinkedList<NameOfMartyr> martyrsSortedByName(){
		LinkedList<NameOfMartyr> martyrsSortedByName = new LinkedList<>();
		Node<Martyr> curr = martyrs.getHead();
		while(curr != null) {
			NameOfMartyr martyr = new NameOfMartyr(curr.getData().getName(), curr.getData().getAge(), curr.getData().getGender());
			martyrsSortedByName.insert(martyr);
			curr = curr.getNext();
		}
		return martyrsSortedByName;
	}
	
	public LinkedList<Martyr> findMartyrUsingPartofName(String part) { // find the martyr using part of his/her name
		Node<Martyr> curr = martyrs.getHead();
		LinkedList<Martyr> martyrsWithThisPart = new LinkedList<>();
		while (curr != null) {
			// if the martyr`s name contains the part 
			if (curr.getData().getName().toUpperCase().contains(part.toUpperCase()))
				martyrsWithThisPart.insert(curr.getData());
			curr = curr.getNext();

		}
		return martyrsWithThisPart;
	}
	 public String toString() {
		  if(date != null) 
				return (date.getMonth()+1)+"/"+(date.getDate())+"/"+(date.getYear()+1900)+","+martyrs.toString();
				else
					return "null";
	  }


	public void setDate(Date date) {
		this.date = date;
	}


	public void setMartyrs(LinkedList<Martyr> martyrs) {
		this.martyrs = martyrs;
	}
	 public String printDate() {
		 return Main.printDate(date);
	 }
	 
}
