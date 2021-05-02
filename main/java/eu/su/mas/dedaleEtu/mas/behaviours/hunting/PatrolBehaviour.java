package eu.su.mas.dedaleEtu.mas.behaviours.hunting;

import java.util.List;

import com.sun.javafx.sg.prism.NGExternalNode;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.explo.ExploreCoopAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import jade.core.behaviours.OneShotBehaviour;

public class PatrolBehaviour extends OneShotBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IDCard idCard;
	private int transition = 6;

	public PatrolBehaviour(ExploreCoopAgent exploreCoopAgent, IDCard idCard) {
		// TODO Auto-generated constructor stub
		super(exploreCoopAgent);
		// this.myMap=myMap;
		this.idCard = idCard;
		this.myAgent = exploreCoopAgent;

	}

	@Override
	public void action() {

		if (idCard.startExplo > 0) {
			idCard.startExplo--;

			System.out.println("**********************************************************");
			System.out.println("***********" + this.idCard.myName + " :START PATROL ***********");
			System.out.println("**********************************************************");
		}

		try {
			this.myAgent.doWait(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String myPosition = ((AbstractDedaleAgent) this.myAgent).getCurrentPosition();
		idCard.updateIdleness(myPosition);
		String dist = idCard.maxIdleness();
		List<String> path = idCard.myMap.getShortestPath(myPosition, dist);
		String nextNode = path.get(0);

		List<Couple<String, List<Couple<Observation, Integer>>>> lobs = ((AbstractDedaleAgent) this.myAgent).observe();


		String newDist = odour(lobs);
		if (newDist != "") {
			((AbstractDedaleAgent) this.myAgent).moveTo(newDist);
			idCard.updateIdleness(myPosition);
			transition = 7;
			idCard.positionToCheck =newDist;
		}
		else {
			if (!((AbstractDedaleAgent) this.myAgent).moveTo(nextNode))
				idCard.setIdleness(dist);
		}
		

	}

	public int onEnd() {
		return transition;
	}

	public String odour(List<Couple<String, List<Couple<Observation, Integer>>>> lobs) {
		for (Couple<String, List<Couple<Observation, Integer>>> elm : lobs) {
			if (elm.getRight().size() > 0)
				return elm.getLeft();

			
		}

		return "";
	}
}
