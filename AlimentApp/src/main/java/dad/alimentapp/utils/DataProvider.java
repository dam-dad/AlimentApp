package dad.alimentapp.utils;

import java.util.ArrayList;
import java.util.List;

import dad.alimentapp.controllers.MainController;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Product;
import dad.alimentapp.models.ProductMomentDay;
import dad.alimentapp.models.Profile;
import dad.alimentapp.service.MenuService;

public class DataProvider {
	
	static Profile menuProfile = MainController.getProfileSelected();
	static List<ReportsMenu> reportsList = new ArrayList<>();
	static List<ReportsMenu> reportsAux = new ArrayList<>();
	public static List<ReportsMenu> getAllMenus() {
		
		List<Menu> menusList = new ArrayList<>();
		
		menusList = MenuService.getAllMenus(menuProfile);
		
		reportsAux.clear();
		reportsList.clear();
		
		for(int i=0; i<menusList.size(); i++) {
			
			ProductMomentDay breakfastProduct = menusList.get(i).getBreakfastProducts();
			ProductMomentDay midMorningProduct = menusList.get(i).getMidMorningProducts();
			ProductMomentDay lunchProduct = menusList.get(i).getLunchProducts();
			ProductMomentDay snackProduct = menusList.get(i).getSnackProducts();
			ProductMomentDay dinnerProduct = menusList.get(i).getDinnerProducts();
			List<Product> breakfastList = breakfastProduct.getProducts();
			List<Product> midMorningList = midMorningProduct.getProducts();
			List<Product> lunchList = lunchProduct.getProducts();
			List<Product> snackList = snackProduct.getProducts();
			List<Product> dinnerList = dinnerProduct.getProducts();
			
			if(breakfastList.size() >= 1) {	
				for(int j=0; j<breakfastList.size(); j++) {
					ReportsMenu reportsMenu = new ReportsMenu();
					reportsMenu.setId(menusList.get(i).getId());
					reportsMenu.setName(menusList.get(i).getName());
					reportsMenu.setProductName(breakfastList.get(j).getName());
					reportsMenu.setMomentName("Desayuno");
					reportsAux.add(reportsMenu);
				}
			}
			if(midMorningList.size() >= 1) {	
				for(int j=0; j<midMorningList.size(); j++) {
					ReportsMenu reportsMenu = new ReportsMenu();
					reportsMenu.setId(menusList.get(i).getId());
					reportsMenu.setName(menusList.get(i).getName());
					reportsMenu.setProductName(midMorningList.get(j).getName());
					reportsMenu.setMomentName("Media-Mañana");
					reportsAux.add(reportsMenu);
				}
			}
			if(lunchList.size() >= 1) {	
				for(int j=0; j<lunchList.size(); j++) {
					ReportsMenu reportsMenu = new ReportsMenu();
					reportsMenu.setId(menusList.get(i).getId());
					reportsMenu.setName(menusList.get(i).getName());
					reportsMenu.setProductName(lunchList.get(j).getName());
					reportsMenu.setMomentName("Almuerzo");
					reportsAux.add(reportsMenu);
				}
			}
			if(snackList.size() >= 1) {	
				for(int j=0; j<snackList.size(); j++) {
					ReportsMenu reportsMenu = new ReportsMenu();
					reportsMenu.setId(menusList.get(i).getId());
					reportsMenu.setName(menusList.get(i).getName());
					reportsMenu.setProductName(snackList.get(j).getName());
					reportsMenu.setMomentName("Merienda");
					reportsAux.add(reportsMenu);
				}
			}
			if(dinnerList.size() >= 1) {	
				for(int j=0; j<dinnerList.size(); j++) {
					ReportsMenu reportsMenu = new ReportsMenu();
					reportsMenu.setId(menusList.get(i).getId());
					reportsMenu.setName(menusList.get(i).getName());
					reportsMenu.setProductName(dinnerList.get(j).getName());
					reportsMenu.setMomentName("Cena");
					reportsAux.add(reportsMenu);
				}
			}
			
		}
		
		int i = 0;
		while(i != reportsAux.size()) {
			
			reportsList.add(reportsAux.get(i));
			i++;
			
		}
		
		return reportsList;
		
	}
	
}
