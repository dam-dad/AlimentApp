package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Representamos la tabla user con un clase y generamos sus getter and setters.
 * Para poder obtener toda la información.
 * 
 * @author Antonio
 *
 */
public class User {

	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surName = new SimpleStringProperty();
	private IntegerProperty age = new SimpleIntegerProperty();
	private IntegerProperty weight = new SimpleIntegerProperty();
	private IntegerProperty height = new SimpleIntegerProperty();
	private ObjectProperty<Gender> gender = new SimpleObjectProperty<>();
	private StringProperty image = new SimpleStringProperty();

	public User(Integer id, String name, String surName, Integer age, Integer weight, Integer height, Gender gender) {
		super();
		this.setId(id);
		this.setName(name);
		this.setSurName(surName);
		this.setAge(age);
		this.setWeight(weight);
		this.setHeight(height);
		this.setGender(gender);
	}

	public final IntegerProperty idProperty() {
		return this.id;
	}

	public final int getId() {
		return this.idProperty().get();
	}

	public final void setId(final int id) {
		this.idProperty().set(id);
	}

	public final StringProperty nameProperty() {
		return this.name;
	}

	public final String getName() {
		return this.nameProperty().get();
	}

	public final void setName(final String name) {
		this.nameProperty().set(name);
	}

	public final StringProperty surNameProperty() {
		return this.surName;
	}

	public final String getSurName() {
		return this.surNameProperty().get();
	}

	public final void setSurName(final String surName) {
		this.surNameProperty().set(surName);
	}

	public final IntegerProperty ageProperty() {
		return this.age;
	}

	public final int getAge() {
		return this.ageProperty().get();
	}

	public final void setAge(final int age) {
		this.ageProperty().set(age);
	}

	public final IntegerProperty weightProperty() {
		return this.weight;
	}

	public final int getWeight() {
		return this.weightProperty().get();
	}

	public final void setWeight(final int weight) {
		this.weightProperty().set(weight);
	}

	public final IntegerProperty heightProperty() {
		return this.height;
	}

	public final int getHeight() {
		return this.heightProperty().get();
	}

	public final void setHeight(final int height) {
		this.heightProperty().set(height);
	}

	public final ObjectProperty<Gender> genderProperty() {
		return this.gender;
	}

	public final Gender getGender() {
		return this.genderProperty().get();
	}

	public final void setGender(final Gender gender) {
		this.genderProperty().set(gender);
	}

	public final StringProperty imageProperty() {
		return this.image;
	}

	public final String getImage() {
		return this.imageProperty().get();
	}

	public final void setImage(final String image) {
		this.imageProperty().set(image);
	}

	public static User getUser(Integer id) {
		User user = null;
		try {
			String sql = "SELECT * FROM users WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				user = new User(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4),
						result.getInt(5), result.getInt(6), Gender.valueOf(result.getInt(7)));
			}
		} catch (SQLException e) {
			Messages.error("Error al obtenner el menu selecionado", e.getMessage());
		}
		return user;
	}
}
