package dad.alimentapp.models;

import java.util.HashMap;
import java.util.Map;
/**
 * Representamos la tabla "Origin" con un enum, en cual hacemos un mapeo 
 * para poder buscar o filtrar no solo por el nombre sino tambien por id.
 * @author Antonio
 *
 */
public enum Origin {
	INDEFINIDO(0),
	VEGETAL(1),
	ANIMAL(2),
	MINERAL(3);
	
	private int id;
    private static Map<Integer, Origin> map = new HashMap<>();

	Origin(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }	
    
    static {
        for (Origin origin : Origin.values()) {
            map.put(origin.id, origin);
        }
    }
    
    public static Origin valueOf(int id) {
        return map.get(id);
    }
}
