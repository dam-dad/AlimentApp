package dad.alimentapp.models;

import java.util.HashMap;
import java.util.Map;

public enum ControlDietMenu {
	Dieta(1),
	Menú(2);
	
	private int id;
    private static Map<Integer, ControlDietMenu> map = new HashMap<>();

    ControlDietMenu(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }	
    
    static {
        for (ControlDietMenu controlDietMenu : ControlDietMenu.values()) {
            map.put(controlDietMenu.id, controlDietMenu);
        }
    }
    
    public static ControlDietMenu valueOf(int id) {
        return map.get(id);
    }	
}
