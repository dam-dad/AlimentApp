package dad.alimentapp.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Menu;
import dad.alimentapp.models.Profile;
import dad.alimentapp.service.MenuService;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class GeneratePDF {
	
	public static final String JRXML_FILE = "/reports/diets.jrxml";
	
	public static void generatePdf() throws JRException, IOException {

		JasperReport report = JasperCompileManager
				.compileReport(GeneratePDF.class.getResourceAsStream("/reports/diets.jrxml"));

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("Diets", "AlimentApp");

		JasperPrint print = JasperFillManager.fillReport(report, parameters,
				new JRBeanCollectionDataSource(DataProvider.getAllMenus()));

	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Guardar PDF");
	        fileChooser.getExtensionFilters().add(new ExtensionFilter("Report", ".pdf"));
	        fileChooser.getExtensionFilters().add(new ExtensionFilter("Todos los archivos", ".*"));
	        File archivoGuardado = fileChooser.showSaveDialog(App.getPrimaryStage()); 

	        JasperExportManager.exportReportToPdfFile(print, archivoGuardado.getPath());

	}

}
