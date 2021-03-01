package dad.alimentapp.main;

import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dad.alimentapp.utils.DataProvider;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class Main {
	
	/*public static void generarPdf() throws JRException, IOException {
		
		List<Menu> menus = new ArrayList<>();
		
		Producto p1 = new Producto();
		p1.setId(1234L);
		p1.setNombre("Mi productito");
		p1.setCantidad(500);
		menus.add(p1);
		
		Producto p2 = new Producto();
		p2.setId(4321L);
		p2.setNombre("Otro productito");
		p2.setCantidad(123);
		menus.add(p2);

		// compila el informe
		JasperReport report = JasperCompileManager.compileReport(Main.class.getResourceAsStream("/reports/diets.jrxml"));		

		// mapa de parámetros para el informe
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("Empresa", "AlimentApp"); // no lo uso, pero se lo paso
		
		// generamos el informe (combinamos el informe compilado con los datos) 
        JasperPrint print  = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(DataProvider.getEntitiesListDataProvider()));
        
        // exporta el informe a un fichero PDF
        JasperExportManager.exportReportToPdfFile(print, "pdf/diets.pdf");
        
        // Abre el archivo PDF generado con el programa predeterminado del sistema
		Desktop.getDesktop().open(new File("pdf/diets.pdf"));
	}*/
	
	public static void main(String[] args) throws JRException, IOException {
		App.main(args);
		//generarPdf();
		
	}

}
