

package application;

import javafx.beans.property.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Martyr implements Comparable<Martyr> {

	private SimpleStringProperty name;
	private SimpleIntegerProperty age;
	private SimpleObjectProperty<Character> gender;
	

	
	Martyr(String name,int age ,char gender){
		this.name=new SimpleStringProperty (name);
		this.age=new SimpleIntegerProperty(age);
		this.gender=new SimpleObjectProperty<Character> (gender);
	}
	public int compareTo(Martyr o) { // compareTo according the age then to the gender
	int compare =  age.get() - o.getAge();
	if(compare != 0)
		return compare;
	return Character.compare(Character.toUpperCase(gender.getValue()), Character.toUpperCase(o.getGender()));
		
	
	
}
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name =  new SimpleStringProperty (name);
	}

	public int getAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age = new  SimpleIntegerProperty (age);
	}

	public char getGender() {
		return gender.get();
	}

	public void setGender(char gender) {
		this.gender = new SimpleObjectProperty<>(gender) ;
	}

	
	
	@Override
	public String toString() { // toString for martyr information
		return name.get() + "," + age.get() + "," + gender.get();
	}
	
}
