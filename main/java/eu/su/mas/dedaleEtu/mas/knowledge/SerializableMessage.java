package eu.su.mas.dedaleEtu.mas.knowledge;


import java.io.Serializable;


import dataStructures.serializableGraph.SerializableSimpleGraph;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;

public class SerializableMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7126302223478378640L;
	private String position;
	public SerializableSimpleGraph<String, MapAttribute> sg;
	public int level;
	

	
	public SerializableMessage(int level,SerializableSimpleGraph<String, MapAttribute> map) {
		
		sg=map;
		this.level=level;
		
		// TODO Auto-generated constructor stub
	}
	public String getpos() {
		return position;
	}
	public SerializableSimpleGraph<String, MapAttribute> getsg(){
		return sg;
	}
	public int getname() {
		return level;
	}

}