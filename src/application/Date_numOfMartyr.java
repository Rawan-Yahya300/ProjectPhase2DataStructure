package application;
import java.util.*;
public class Date_numOfMartyr implements Comparable<Date_numOfMartyr> {
  private Date dateOFDeath;
  private int numOfMartyr;
  
public Date_numOfMartyr(Date dateOFDeath, int numOfMartyr) {
	this.dateOFDeath = dateOFDeath;
	this.numOfMartyr = numOfMartyr;
}

public Date getDateOFDeath() {
	return dateOFDeath;
}

public void setDateOFDeath(Date dateOFDeath) {
	this.dateOFDeath = dateOFDeath;
}

public int getNumOfMartyr() {
	return numOfMartyr;
}

public void setNumOfMartyr(int numOfMartyr) {
	this.numOfMartyr = numOfMartyr;
}

@Override
public int compareTo(Date_numOfMartyr o) {
	// TODO Auto-generated method stub
	return numOfMartyr - o.getNumOfMartyr();
}
 
  
}
