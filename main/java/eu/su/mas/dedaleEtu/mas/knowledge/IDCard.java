package eu.su.mas.dedaleEtu.mas.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.EdgeRejectedException;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.IdAlreadyInUseException;

import dataStructures.serializableGraph.SerializableNode;
import dataStructures.serializableGraph.SerializableSimpleGraph;
import dataStructures.tuple.Couple;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import jade.core.AID;
import jade.core.Node;
import jade.util.leap.Serializable;

/**
 * 
 * @author hider toula
 *
 */
public class IDCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public HashMap<String, Integer> indless;
	public Integer nbDisplays = 2;
	public Integer startExplo = 1;
	public boolean onMyPath;
	public Integer MaxTry = 10;
	public ArrayList<String> agentsNames;
	public Integer maxMoves = 15;
	public Integer cpt = 0;

	public ArrayList<Couple<String, Integer>> idleness;

	public MapRepresentation myMap;

	public List<String> openNodes;
	private HashMap<String, ArrayList<String>> edges;
	private ArrayList<String> closedNodes;
	public ArrayList<String> nodes;
	public ArrayList<String> agentsToShareMap;

	public String myPosition;
	public String myName;
	public Integer level;

	public ArrayList<Couple<String, SerializableSimpleGraph<String, MapAttribute>>> agentsMaps;
	public ArrayList<Couple<String, MapRepresentation>> maps;

	public String positionToCheck;
	public ArrayList<String> getClosedNodes() {
		return closedNodes;
	}

	public HashMap<String, ArrayList<String>> getEdges() {
		return this.edges;
	}

	public IDCard(String myName, AID aid, ArrayList<String> agentsNames, int level) {
		// this.agentsMaps = new HashMap<String, SerializableMessage>();
		Random rand = new Random();
		this.openNodes = new ArrayList<String>();
		this.closedNodes = new ArrayList<String>();
		this.edges = new HashMap<String, ArrayList<String>>();
		this.level = rand.nextInt(100 - 0 + 1);
		this.agentsNames = agentsNames;
		this.myName = myName;

		this.agentsToShareMap = new ArrayList<String>();

		// agentsMaps = new ArrayList<>();

		this.agentsMaps = new ArrayList<Couple<String, SerializableSimpleGraph<String, MapAttribute>>>();
		for (String s : this.agentsNames) {
			Couple<String, SerializableSimpleGraph<String, MapAttribute>> c = new Couple<>(s,
					new SerializableSimpleGraph<String, MapAttribute>());
			agentsMaps.add(c);
		}

		this.onMyPath = true;
		this.idleness = new ArrayList<Couple<String, Integer>>();

	}

	/*
	 * add the agent position in closedNodes and create/update the key
	 */
	public void addCurrentPositon(String currentPosition) {
		if (!closedNodes.contains(currentPosition)) {
			closedNodes.add(currentPosition);

			// for the moment just concatenate string position
		}

	}

	@SuppressWarnings("null")
	public void setMap(SerializableSimpleGraph<String, MapAttribute> map, String index) {

		ArrayList<Couple<String, SerializableSimpleGraph<String, MapAttribute>>> res = new ArrayList<Couple<String, SerializableSimpleGraph<String, MapAttribute>>>();
		Iterator<Couple<String, SerializableSimpleGraph<String, MapAttribute>>> itr = this.agentsMaps.iterator();
		while (itr.hasNext()) {
			Couple<String, SerializableSimpleGraph<String, MapAttribute>> temp = itr.next();
			if (temp.getLeft().equals(index))
				res.add(new Couple<String, SerializableSimpleGraph<String, MapAttribute>>(index, map));
			else
				res.add(temp);
		}

		this.agentsMaps = res;
	}

	public SerializableSimpleGraph<String, MapAttribute> getMap(String index) {

		Iterator<Couple<String, SerializableSimpleGraph<String, MapAttribute>>> itr = this.agentsMaps.iterator();
		while (itr.hasNext()) {

			Couple<String, SerializableSimpleGraph<String, MapAttribute>> tmp = itr.next();

			if (tmp.getLeft().equals(index)) {
				if (tmp.getRight() == null)
					return new SerializableSimpleGraph<String, MapRepresentation.MapAttribute>();
				else
					return tmp.getRight();

			}

		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public SerializableSimpleGraph<String, MapAttribute> updateMap(String index) {

		SerializableSimpleGraph<String, MapAttribute> res = (SerializableSimpleGraph<String, MapAttribute>) myMap
				.clone();
		SerializableSimpleGraph<String, MapAttribute> agentMap = getMap(index);

		// res.getNode(pos).setContent(MapAttribute.closed

		for (SerializableNode<String, MapAttribute> n : ((SerializableSimpleGraph<String, MapAttribute>) myMap.clone())
				.getAllNodes()) {

			if ((agentMap.getAllNodes().contains(n)) && (n.getNodeContent() == MapAttribute.open)) {

				res.getAllNodes().remove(n);
			}

		}

		return res;
	}

	public synchronized void addEdge(SerializableSimpleGraph<String, MapAttribute> res, String idNode1,
			String idNode2) {
		myMap.setNbEdges();
		try {

			res.addEdge(((Integer) this.myMap.getNbEdges()).toString(), idNode1, idNode2);

		} catch (IdAlreadyInUseException e1) {
			System.err.println("ID existing");
			System.exit(1);
		} catch (EdgeRejectedException e2) {
			System.out.println("fallait déminuier le nombre de edgs .....");
		} catch (ElementNotFoundException e3) {

		}
	}

	/*
	 * public void update(String id, MapAttribute content) {
	 * 
	 * SerializableNode<String, MapAttribute> n = new SerializableNode<String,
	 * MapAttribute>(id); n.setContent(content);
	 * 
	 * for (String s : agentsNames) { if (getMap(s).getAllNodes().contains(n)) {
	 * 
	 * getMap(s).getAllNodes().remove(n); } } }
	 */
	public void updateIdleness(String index) {

		ArrayList<Couple<String, Integer>> res = new ArrayList<Couple<String, Integer>>();

		boolean found = false;
		for (Couple<String, Integer> elm : this.idleness) {

			if (elm.getLeft().equals(index)) {
				res.add(new Couple<String, Integer>(index, 0));
				found = true;
			} else
				res.add(new Couple<String, Integer>(elm.getLeft(), elm.getRight() + 1));
		}
		if (!found) {
			res.add(new Couple<String, Integer>(index, 0));
		}

		this.idleness = res;

	}

	public void initializeIdleness() {
		ArrayList<Couple<String, Integer>> res = new ArrayList<Couple<String, Integer>>();
		for (SerializableNode<String, MapAttribute> n : myMap.getSerializableGraph().getAllNodes()) {
			boolean found = false;

			for (Couple<String, Integer> elm : this.idleness) {
				if (elm.getLeft().equals(n.getNodeId()))
					found = true;
			}

			if (!found) {
				res.add(new Couple<String, Integer>(n.getNodeId(), 0));
			}

		}

		for (Couple<String, Integer> elm : this.idleness) {
			res.add(elm);

		}
		this.idleness = res;
	}

	public String maxIdleness() {

		Couple<String, Integer> max = idleness.get(0);

		for (Couple<String, Integer> elm : idleness) {
			if (elm.getRight() > max.getRight())
				max = elm;
		}

		return max.getLeft();
	}

	public void setIdleness(String index) {

		ArrayList<Couple<String, Integer>>  ind = new ArrayList<Couple<String, Integer>>();

		for (Couple<String, Integer> elm : idleness) {
			if (elm.getLeft().equals(index))
				ind.add(new Couple<String, Integer>(index, 0));
			else ind.add(elm);
		}

	}

}
