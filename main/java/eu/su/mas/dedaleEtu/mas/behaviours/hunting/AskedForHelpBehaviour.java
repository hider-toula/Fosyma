package eu.su.mas.dedaleEtu.mas.behaviours.hunting;

import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedaleEtu.mas.agents.dummies.explo.ExploreCoopAgent;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AskedForHelpBehaviour extends OneShotBehaviour{
	public IDCard idCard ;
	private int transition ;
	public AskedForHelpBehaviour(ExploreCoopAgent exploreCoopAgent, IDCard idCard) {
		// TODO Auto-generated constructor stub
		this.idCard =idCard;
		this.myAgent=exploreCoopAgent;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		askedForHelp();
	}

public void askedForHelp() {
		
		
		final MessageTemplate template =MessageTemplate.and( 
								MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
								MessageTemplate.MatchProtocol("ASKED"));
		
		ACLMessage msg = this.myAgent.receive(template);
		
		
		if(msg != null) {
			idCard.positionToCheck=msg.getContent();
			
		}
		

		
	}
		

		
	

public int onEnd() {
	return 11;
}
}
