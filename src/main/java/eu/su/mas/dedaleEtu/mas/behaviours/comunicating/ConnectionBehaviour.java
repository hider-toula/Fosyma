package eu.su.mas.dedaleEtu.mas.behaviours.comunicating;


import eu.su.mas.dedale.mas.AbstractDedaleAgent;

import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;


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
 * @author hc
 *
 */
public class ConnectionBehaviour extends OneShotBehaviour {

	private static final long serialVersionUID = 8567689731496787661L;


	/**
	 * Current knowledge of the agent regarding the environment
	 */
	
	public IDCard idCard ;
	private int transition ;
	

/**
 * 
 * @param myagent
 * eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute

 */
	public ConnectionBehaviour
	(
			
			final AbstractDedaleAgent myagent,
			IDCard idCard
	){
		super(myagent);
		this.idCard =idCard;
		this.myAgent=myagent;
		
	}

	@Override
	public void action() {
		
		
		/* Send Message ******************************************/

		this.transition = 2;
	

		
		sendMessage();
		checkBox();
		
		
		try {
			this.myAgent.doWait(500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if(confirmation()) transition = 3;
		else this.idCard.agentsToShareMap.clear();
		
	}

	
	
	

	
	public void sendMessage() {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("COMUNICATE");
		
		for (String s:this.idCard.agentsNames) {
			msg.addReceiver(new AID(s,AID.ISLOCALNAME));
		}
		
		((AbstractDedaleAgent) this.myAgent).sendMessage(msg);
	}
	
	
	
	public void checkBox() {
		
		
		final MessageTemplate template =MessageTemplate.and( 
								MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
								MessageTemplate.MatchProtocol("COMUNICATE"));
		
		ACLMessage msg = this.myAgent.receive(template);
		
		
		while(msg != null) {
			
			
			
			ACLMessage answer=new ACLMessage(ACLMessage.CONFIRM);
			answer.setSender(this.myAgent.getAID());
			answer.setProtocol("OK");
			answer.addReceiver(new AID(msg.getSender().getLocalName(),AID.ISLOCALNAME));
			
			((AbstractDedaleAgent) this.myAgent).sendMessage(answer);
			msg = this.myAgent.receive(template);
		}
		

		
	}
	
	protected boolean confirmation() {
		boolean confirm =false;
		final MessageTemplate template =MessageTemplate.and( 
				MessageTemplate.MatchPerformative(ACLMessage.CONFIRM), 
				MessageTemplate.MatchProtocol("OK"));

		final ACLMessage msg = this.myAgent.receive(template);
		while (msg != null) {
			confirm = true;
			msg.getContent();
			this.idCard.agentsToShareMap.add(msg.getSender().getLocalName());

			return true;
		}
		return confirm;
	}
	
	
	
	
	
	
	
	
	@Override
	public int onEnd() {
		

		return transition;
	}

}
