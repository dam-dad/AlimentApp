package dad.alimentapp.utils;

public class Reports {
	
	/*// nombres de los menús y dietas
	String dietsName;*/
	
	// datos del usuario
	String name;
	String surname;
	String gender;
	double imc;
	String age;
	int weight;
	int height;
	
	public Reports(String name, String surname, String gender, double imc, String age, int weight, int height) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.imc = imc;
		this.age = age;
		this.weight = weight;
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getImc() {
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
