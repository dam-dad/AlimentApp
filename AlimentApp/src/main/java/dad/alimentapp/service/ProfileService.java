package dad.alimentapp.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dad.alimentapp.main.App;
import dad.alimentapp.models.Gender;
import dad.alimentapp.models.Profile;
import dad.alimentapp.utils.Messages;

/**
 *  En esta clase tenemos almacenadas todas las consultas a la base de datos, en referencia a la tabla Profile.
 * @author Antonio
 *
 */
public class ProfileService {

/**
 * El metodo "getProfile" lo utilizamos para obtener todos los datos del perfil indicado.
 * @param id le pasamos por parametros el identificador por parametros.
 * @return retornamos dicho perfil.
 */
	public static Profile getProfile(Integer id) {
		Profile profile = null;
		try {
			String sql = "SELECT name_profile, name, surname, age, weight, height, imc, gender, image_profile FROM profile WHERE id = ?";
			PreparedStatement query = App.connection.prepareStatement(sql);
			query.setInt(1, id);
			ResultSet result = query.executeQuery();
			while (result.next()) {
				profile = new Profile(id, result.getString(1), result.getString(2), result.getString(3),
						result.getInt(4), result.getInt(5), result.getInt(6), result.getDouble(7),
						Gender.valueOf(result.getInt(8)));
			}
		} catch (SQLException e) {
			Messages.error("Error al obtenner el perfil selecionado", e.getMessage());
		}
		return profile;
	}
}
