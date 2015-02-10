package meta;


import java.util.HashMap;
import java.util.Set;

public class RelationUnitMap {
	private static HashMap<String, String> unitMap = null;
	static {
			unitMap = new HashMap<String, String>();
			unitMap.put("AGL", "square metre");
			unitMap.put("FDI", "united states dollar");
			unitMap.put("GOODS", "united states dollar");
			unitMap.put("ELEC", "joule");
			unitMap.put("CO2", "kilogram");
			unitMap.put("DIESEL", "united states dollar per litre");
			unitMap.put("INF", "percent");
			unitMap.put("INTERNET", "percent");
			unitMap.put("GDP", "united states dollar");
			unitMap.put("LIFE", "second");
			unitMap.put("POP", "");
	}
	public static String getUnit(String rel) {
			return unitMap.get(rel);
	}
	public static Set<String> getRelations(){
		return unitMap.keySet();
	}
}
