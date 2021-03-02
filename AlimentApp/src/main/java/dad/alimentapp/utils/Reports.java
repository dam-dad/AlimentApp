package dad.alimentapp.utils;

public class Reports {
	
	// datos del usuario
	private String name;
	private String surname;
	private double imc;
	private int age;
	private int weight;
	private int height;
	
	public Reports(String name, String surname, double imc, int age, int weight, int height) {
		super();
		this.name = name;
		this.surname = surname;
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

	public double getImc() {
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
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
