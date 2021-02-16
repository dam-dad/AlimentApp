package dad.alimentapp.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dad.alimentapp.main.App;
import dad.alimentapp.utils.Messages;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Representamos la tabla Dietas menu con un clase y generamos sus getter and setters. Para poder obtener toda la información. 
 * @author Antonio
 *
 */
public class DietsMenu {
	
	private ObjectProperty<Diet> diets = new SimpleObjectProperty<>();
	private ListProperty<Menu> menu = new SimpleListProperty<>(FXCollections.observableArrayList());
			
	public DietsMenu() {
		this.setDiets(new Diet("Nueva Dieta"));
		this.setMenu(FXCollections.observableArrayList(menu));
	}
	
	public DietsMenu(Diet diets) {
		this.setDiets(diets);
	}
	
	public DietsMenu(List<Menu> menu) {
		this.setMenu(FXCollections.observableArrayList(menu));
	}
	
	public DietsMenu(Diet diets, List<Menu> menu) {
		this.setDiets(diets);
		this.setMenu(FXCollections.observableArrayList(menu));
	}

	public final ObjectProperty<Diet> dietsProperty() {
		return this.diets;
	}
	
	public final Diet getDiets() {
		return this.dietsProperty().get();
	}
	
	public final void setDiets(final Diet diets) {
		this.dietsProperty().set(diets);
	}

	public final ListProperty<Menu> menuProperty() {
		return this.menu;
	}	

	public final ObservableList<Menu> getMenu() {
		return this.menuProperty().get();
	}	

	public final void setMenu(final ObservableList<Menu> menu) {
		this.menuProperty().set(menu);
	}
	
	public static DietsMenu getAllMenusForDiet(Diet diet) {
		DietsMenu dietMenu = new DietsMenu(diet);
		try {
			String sql = "SELECT id_menu FROM diets_menus WHERE id_diets = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, diet.getId());
			ResultSet result = query.executeQuery();
			while (result.next()) {
				dietMenu.menu.add(Menu.getMenu(result.getInt(1)));
			}
		} catch (SQLException e) {
			Messages.error("Error al obtener todos los menus de la dieta indicada",  e.getMessage());
		}
		return dietMenu;
	}
}
