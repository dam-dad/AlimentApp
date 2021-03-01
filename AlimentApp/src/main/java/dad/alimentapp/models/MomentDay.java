package dad.alimentapp.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Representamos la tabla "Moment day" con un enum, en cual hacemos un mapeo
 * para poder buscar o filtrar no solo por el nombre sino tambien por id.
 * 
 * @author Antonio
 *
 */
public enum MomentDay {
	DESAYUNO(1), MEDIA_MAÑANA(2), ALMUERZO(3), MERIENDA(4), CENA(5);

	private int id;
	private static Map<Integer, MomentDay> map = new HashMap<>();

	MomentDay(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	static {
		for (MomentDay momentDay : MomentDay.values()) {
			map.put(momentDay.id, momentDay);
		}
	}

	public static MomentDay valueOf(int id) {
		return map.get(id);
	}
}
