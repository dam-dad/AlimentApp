package dad.alimentapp.models;
/**
 * Representamos la tabla weekday con un enum.
 * @author Antonio
 *
 */

import java.util.HashMap;
import java.util.Map;

public enum Weekday {
	LUNES(1),
	MARTES(2),
	MIERCOLES(3),
	JUEVES(4),
	VIERNES(5),
	SABADO(6),
	DOMINGO(7);
	
	private int id;
    private static Map<Integer, Weekday> map = new HashMap<>();

    Weekday(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }	
    
    static {
        for (Weekday weekday : Weekday.values()) {
            map.put(weekday.id, weekday);
        }
    }
    
    public static Weekday valueOf(int id) {
        return map.get(id);
    }
    
    public static Weekday previous(int id) {
    	if(id == 1) {
    		id = 7;
    	} else {
    		id--; 
    	}
    	return Weekday.valueOf(id);
    }
    
    public static Weekday next(int id) {
    	if(id == 7) {
    		id = 1;
    	} else {
    		id++; 
    	}    
    	return Weekday.valueOf(id);
    }
}
