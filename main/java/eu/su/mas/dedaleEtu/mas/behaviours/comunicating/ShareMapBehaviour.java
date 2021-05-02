package eu.su.mas.dedaleEtu.mas.behaviours.comunicating;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import eu.su.mas.dedaleEtu.mas.agents.dummies.explo.ExploreCoopAgent;
import dataStructures.serializableGraph.SerializableSimpleGraph;
import dataStructures.tuple.Couple;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
//import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import eu.su.mas.dedaleEtu.mas.knowledge.SerializableMessage;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

/**
 * The agent periodically share its map. It blindly tries to send all its graph
 * to its friend(s) If it was written properly, this sharing action would NOT be
 * in a ticker behaviour and only a subgraph would be shared.
 * 
 * @author hc
 *
 */
public class ShareMapBehaviour extends OneShotBehaviour {

	public IDCard idCard;
	private int transition = -1;

	/**
	 * The agent periodically share its map. It blindly tries to send all its graph
	 * to its friend(s) If it was written properly, this sharing action would NOT be
	 * in a ticker behaviour and only a subgraph would be shared.
	 * 
	 * @param a         the agent
	 * @param period    the periodicity of the behaviour (in ms)
	 * @param mymap     (the map to share)
	 * @param nodeState
	 * @param receivers
	 * @param receivers the list of agents to send the map to
	 */
	public ShareMapBehaviour(final AbstractDedaleAgent myagent, IDCard idCard

	)

	{
		super(myagent);
		// this.myMap=myMap;
		this.idCard = idCard;
		this.myAgent = myagent;

	}

	protected void sendMap() {

		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("MAP");

		String reciver = idCard.agentsToShareMap.get(0);
		SerializableMessage m = new SerializableMessage(idCard.level, idCard.updateMap(reciver));
		
		if(idCard.nbDisplays > 0){
			System.out.println("Je suis l'agent :"+this.idCard.myName+" Voici la taille de ma carte "+idCard.myMap.getSerializableGraph().getAllNodes().size()+" Et voila la taille de la carte que je vais envoyer "+m.sg.getAllNodes().size());
			idCard.nbDisplays--;
			
		}
		

		idCard.setMap(idCard.myMap.getSerializableGraph(), reciver);
		
		try {
			
			msg.setContentObject(m);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (String s : this.idCard.agentsToShareMap) {
			msg.addReceiver(new AID(s, AID.ISLOCALNAME));

		}

		((AbstractDedaleAgent) this.myAgent).sendMessage(msg);
		


	}

	public void reciveMap() throws UnreadableException {

		
		final MessageTemplate template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
				MessageTemplate.MatchProtocol("MAP"));

		ACLMessage msg = this.myAgent.receive(template);

		while (msg != null) {
			SerializableMessage content = (SerializableMessage) msg.getContentObject();
			if(content.level<idCard.level) idCard.onMyPath = false;
			this.idCard.myMap.mergeMap(content.sg);
			
			ACLMessage answer = new ACLMessage(ACLMessage.CONFIRM);
			answer.setSender(this.myAgent.getAID());
			answer.setProtocol("OKI");
			answer.addReceiver(new AID(msg.getSender().getLocalName(), AID.ISLOCALNAME));

			((AbstractDedaleAgent) this.myAgent).sendMessage(answer);
			msg = this.myAgent.receive(template);
		}
		

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -568863390879327961L;

	@Override

	public void action() {
		transition = 4;

		if (!idCard.updateMap(idCard.agentsToShareMap.get(0)).getAllNodes().isEmpty()) {
			

			sendMap();
			try {
				reciveMap();
			} catch (UnreadableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void substraction(SerializableSimpleGraph<String, MapAttribute> map1,
			SerializableSimpleGraph<String, MapAttribute> map2) {

	}

	@Override
	public int onEnd() {

		this.idCard.agentsToShareMap.clear();

		return transition;
	}
	


}
