package dad.alimentapp.utils;

import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.models.app.Menu;

public class DataProvider {
	
	public static List<Menu> entitiesList = new ArrayList<>();
	
	public DataProvider(List<Menu> entitiesList) {
		
		DataProvider.entitiesList = entitiesList;
		
	}
	
	public static List<Menu> getEntitiesListDataProvider() {
		return entitiesList;
	}
	
	public static void setEntitiesList(List<Menu> entitiesList) {
		DataProvider.entitiesList = entitiesList;
	}
	

}
