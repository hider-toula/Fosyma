package eu.su.mas.dedaleEtu.mas.behaviours.exploring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import dataStructures.tuple.Couple;
import eu.su.mas.dedale.env.Observation;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;

import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;

import jade.core.behaviours.OneShotBehaviour;

/**
 * <pre>


 * This behavior allows an agent to explore the environment and learn the associated topological map.
 * The algorithm is a pseudo - DFS computationally consuming because its not optimised at all.
 * 
 * When all the nodes around him are visited, the agent randomly select an open node and go there to restart its dfs. 
 * This (non optimal) behavior is done until all nodes are explored. 
 * 
 * Warning, this behavior does not save the content of visited nodes, only the topology.
 * Warning, the sub-behavior ShareMap periodically share the whole map
 * </pre>
 * 
 * @author hc
 *
 */

public class ExploCoopBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 8567689731496787661L;

	/**
	 * Current knowledge of the agent regarding the environment
	 */

	public IDCard idCard;
	private int transition = 1;

	/**
	 * 
	 * @param myagent eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute
	 * 
	 */
	public ExploCoopBehaviour(

			final AbstractDedaleAgent myagent, IDCard idCard) {
		super(myagent);
		// this.myMap=myMap;
		this.idCard = idCard;
		this.myAgent = myagent;

	}

	@Override
	public void action() {
		if (idCard.startExplo > 0) {
			idCard.startExplo--;

			System.out.println("**********************************************************");
			System.out.println("***********" + this.idCard.myName + " :START EXPLORATION ***********");
			System.out.println("**********************************************************");
		}

		ArrayList<String> neh = new ArrayList<>();
		if (this.idCard.myMap == null)
			this.idCard.myMap = new MapRepresentation();

		/******************************************************************/

		String myPosition = ((AbstractDedaleAgent) this.myAgent).getCurrentPosition();
		if (myPosition != null) {

			List<Couple<String, List<Couple<Observation, Integer>>>> lobs = ((AbstractDedaleAgent) this.myAgent)
					.observe();

			this.idCard.myMap.addNode(myPosition, MapAttribute.closed);
			idCard.updateIdleness(myPosition);

			String nextNode = null;
			Iterator<Couple<String, List<Couple<Observation, Integer>>>> iter = lobs.iterator();
			while (iter.hasNext()) {
				String nodeId = iter.next().getLeft();
				neh.add(nodeId);
				boolean isNewNode = this.idCard.myMap.addNewNode(nodeId);

				if (myPosition != nodeId) {
					this.idCard.myMap.addEdge(myPosition, nodeId);

					if (nextNode == null && isNewNode)
						nextNode = nodeId;
				}
			}

			if (!this.idCard.myMap.hasOpenNode()) {
				idCard.initializeIdleness();
				System.out.println("***********************************************************");
				System.out.println("***********" + this.idCard.myName + " :END OF EXPLORATION ***********");
				System.out.println("***********************************************************");

				System.out.println("Je suis l'agent :" + this.idCard.myName + " Voici la taille de ma carte "
						+ idCard.myMap.getSerializableGraph().getAllNodes().size());

				System.out.println("La taille de idlnesse est  :" + idCard.idleness.size());
				transition = 5;
				this.idCard.startExplo=1;
			}

			else {

				if (nextNode == null && idCard.MaxTry < 0 && !idCard.onMyPath) {
					// openNodes = idCard.myMap.getOpenNodes();
					nextNode = this.idCard.myMap.getLongestPathToClosestOpenNode(myPosition).get(0);

					idCard.MaxTry--;
				}

				else {
					if (nextNode == null)
						nextNode = this.idCard.myMap.getShortestPathToClosestOpenNode(myPosition).get(0);
					idCard.MaxTry = 15;
				}

				if (((AbstractDedaleAgent) this.myAgent).moveTo(nextNode)) {

					idCard.cpt = 0;
					myPosition = ((AbstractDedaleAgent) this.myAgent).getCurrentPosition();
					lobs = ((AbstractDedaleAgent) this.myAgent).observe();
					this.idCard.myMap.addNode(myPosition, MapAttribute.closed);
					idCard.updateIdleness(myPosition);

					transition = 1;

					nextNode = null;
					iter = lobs.iterator();
					neh.clear();
					while (iter.hasNext()) {
						String nodeId = iter.next().getLeft();

						boolean isNewNode = this.idCard.myMap.addNewNode(nodeId);

						neh.add(nodeId);
						if (myPosition != nodeId) {
							this.idCard.myMap.addEdge(myPosition, nodeId);

							if (nextNode == null && isNewNode)
								nextNode = nodeId;
						}
					}

				} else {

					if (idCard.cpt < neh.size())
						nextNode = neh.get(idCard.cpt);
					((AbstractDedaleAgent) this.myAgent).getCurrentPosition();
					idCard.cpt++;

					myPosition = ((AbstractDedaleAgent) this.myAgent).getCurrentPosition();
					lobs = ((AbstractDedaleAgent) this.myAgent).observe();
					this.idCard.myMap.addNode(myPosition, MapAttribute.closed);
					idCard.updateIdleness(myPosition);

					transition = 1;

					nextNode = null;
					iter = lobs.iterator();
					while (iter.hasNext()) {
						String nodeId = iter.next().getLeft();

						boolean isNewNode = this.idCard.myMap.addNewNode(nodeId);

						if (myPosition != nodeId) {
							this.idCard.myMap.addEdge(myPosition, nodeId);

							if (nextNode == null && isNewNode)
								nextNode = nodeId;
						}

				
					}
				}

			}

		}

	}

	@Override
	public int onEnd() {

		return transition;
	}

}
