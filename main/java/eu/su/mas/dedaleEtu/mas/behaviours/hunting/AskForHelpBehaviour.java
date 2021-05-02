package eu.su.mas.dedaleEtu.mas.behaviours.hunting;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.explo.ExploreCoopAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class AskForHelpBehaviour extends OneShotBehaviour {
	public IDCard idCard ;
	private int transition ;
	
	public AskForHelpBehaviour(ExploreCoopAgent exploreCoopAgent, IDCard idCard) {
		super(exploreCoopAgent);
		this.idCard =idCard;
		this.myAgent=exploreCoopAgent;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
	
	}

	public void sendMessage() {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.setSender(this.myAgent.getAID());
		msg.setProtocol("ASKED");
		msg.setContent(idCard.positionToCheck);
		
		for (String s:this.idCard.agentsNames) {
			msg.addReceiver(new AID(s,AID.ISLOCALNAME));
		}
		
		((AbstractDedaleAgent) this.myAgent).sendMessage(msg);
	}
	@Override
	public int onEnd() {
		

		return transition;
	}
	
}
