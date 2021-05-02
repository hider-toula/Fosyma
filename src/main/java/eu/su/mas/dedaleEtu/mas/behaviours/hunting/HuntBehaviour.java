package eu.su.mas.dedaleEtu.mas.behaviours.hunting;

import java.util.List;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.explo.ExploreCoopAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class HuntBehaviour extends OneShotBehaviour {
	public IDCard idCard;
	@SuppressWarnings("unused")
	private int transition;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HuntBehaviour(ExploreCoopAgent exploreCoopAgent, IDCard idCard) {
		// TODO Auto-generated constructor stub
		super(exploreCoopAgent);
		this.idCard = idCard;
		this.myAgent = exploreCoopAgent;

		/* a refaire */
	}

	@Override
	public void action() {
		
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
			transition = 9;
			idCard.positionToCheck =newDist;
		}
		else {
			transition =8;
		}

	}


	

	public String odour(List<Couple<String, List<Couple<Observation, Integer>>>> lobs) {
		for (Couple<String, List<Couple<Observation, Integer>>> elm : lobs) {
			if (elm.getRight().size() > 0)
				return elm.getLeft();

			
		}

		return "";
	}
	
	public int onEnd() {
		return 8;
	}
}