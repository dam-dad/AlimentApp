package dad.alimentapp.models;

import java.util.HashMap;
import java.util.Map;

/**
 * El enum "Gender" representa los generos disponibles en la app.
 * 
 * @author Antonio
 *
 */
public enum Gender {
	HOMBRE(1), MUJER(2);

	private int id;
	private static Map<Integer, Gender> map = new HashMap<>();

	Gender(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	static {
		for (Gender gender : Gender.values()) {
			map.put(gender.id, gender);
		}
	}

	public static Gender valueOf(int id) {
		return map.get(id);
	}
}
