package dad.alimentapp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dad.alimentapp.main.App;
/**
 * La clase "Connection" nos permite conectarnos a la base de datos creada mediante H2. En esta clase, se implemntan otros metodos como 
 * cerrar conexion, crear base de datos, leer de un archivo.
 * @author Antonio
 *
 */
public class ConnectionDB {

	// JDBC driver name and database URL
	static final String DB_URL = "jdbc:h2:./db/db_alimentapp";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "";
	/**
	 * El metodo "Connection" nos permite conectarnos a la base de datos hecha en H2. Nos conectaremos mediante
	 * una dependencia indicada en el pom, le pasaremos por parametros la url de la base de datos, el usuario y la contraseña.
	 * @return nos retornara la conexion a la base de datos
	 */
	
	public static Connection connection() {
		Connection conect = null;
		try {
			// Cargamos el controlador que esta en el pom
			Class.forName("org.h2.Driver");
			try {
				System.out.println("Connecting to database...");
				// Almacenamos la conexion con la base de datos
				conect = DriverManager.getConnection(DB_URL, USER, PASS);
				System.out.println("CONECTADO con H2");
			} catch (SQLException e) {
				System.err.println("Error en la conexión H2: " + e);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conect;
	}
	/**
	 * El Metodo "closeConnection" lo utilizaremos para cerrar la conexion con la base de datos.
	 * @param conect le pasaremos por parametros la conexion.
	 */
	public void closeConnection(Connection conect) {
		try {
			// Recibimos la conexión por paremetro y la cerramos
			conect.close();
			System.out.println("Conexión cerrada");
		} catch (SQLException e) {
			System.err.println("Error: " + e);
		}
	}
	/**
	 * El metodo "createDB" lo usaremos para crear la base de datos de la aplicación (esta función solo debe ejecutarse la primera vez).
	 */
	public static void createDB() {
		try {
			PreparedStatement stmt = App.connection.prepareStatement(readSQLDB());
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * El metodo "readSQLDB" lo usamos para leer el fichero sql adjunto al proyecto, el cual nos permite crear las tablas de la BD 
	 * e insertar sus datos.
	 * @return retornamos todo el contenido del fichero sql para crear las tablas e insertar los datos en una sola intrucción.
	 */
	private static String readSQLDB() {
		// Cargamos el archivo SQL
		File file;
		String info = "";
		try {
			file = new File(ConnectionDB.class.getResource("/db/db_alimentapp.sql").toURI());
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String line;
				// Leemos y almacenamos en "info" las consultas del script
				while ((line = br.readLine()) != null) {
					info += line;
				}
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		return info;
	}	
}
