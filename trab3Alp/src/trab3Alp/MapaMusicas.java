package trab3Alp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//import java.util.Set;

public class MapaMusicas {
	private Map<String, Integer> mapa = new HashMap<String,Integer>();
	
	public Map<String,Integer> getMap() {
		return this.mapa;
	}
	public void addToMap(String key, int count) {
		mapa.put(key,count);
	}
	public boolean existInMap(String key) {
		if(mapa.containsKey(key)) {
			return true;
		}
		return false;
	}
	public void countMap(String key) {
		mapa.replace(key, mapa.get(key), (mapa.get(key)+1));
	}
	public void showMap() {
		//Set st = (Set) mapa.entrySet();
		Iterator<Map.Entry<String, Integer>> it = mapa.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, Integer> entrada = it.next();
			System.out.println(entrada.getKey()+" : ("+entrada.getValue()+")");
		}
	}
}
