

package application;



	
	import javafx.beans.property.*;
	import javafx.beans.property.SimpleIntegerProperty;
	import javafx.beans.property.SimpleStringProperty;

	public class NameOfMartyr implements Comparable<NameOfMartyr> {

		private SimpleStringProperty name;
		private SimpleIntegerProperty age;
		private SimpleObjectProperty<Character> gender;
		

		
		NameOfMartyr(String name,int age ,char gender){
			this.name=new SimpleStringProperty (name);
			this.age=new SimpleIntegerProperty(age);
			this.gender=new SimpleObjectProperty<Character> (gender);
		}
		NameOfMartyr(String name){
			this.name=new SimpleStringProperty (name);
			
		}
		public int compareTo(NameOfMartyr o) { // compareTo according the age //baaaaaaaaack
		
			return name.get().compareToIgnoreCase(o.getName());
			
		
		
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


