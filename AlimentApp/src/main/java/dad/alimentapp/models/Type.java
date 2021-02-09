package dad.alimentapp.models;

import java.util.HashMap;
import java.util.Map;
/**
 * Representamos la tabla "Type" con un enum, en cual hacemos un mapeo 
 * para poder buscar o filtrar no solo por el nombre sino tambien por id.
 * @author Antonio
 *
 */
public enum Type {
	INDEFINIDO(0),
	FRUTAS(1),
	VERDURAS(2),
	HIDRATOS(3),
	PROTEICOS(4),
	GRASAS(5),
	LACTEOS(6);
	
	private int id;
    private static Map<Integer, Type> map = new HashMap<>();

    Type(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }	
    
    static {
        for (Type type : Type.values()) {
            map.put(type.id, type);
        }
    }
    
    public static Type valueOf(int id) {
        return map.get(id);
    }	
}
