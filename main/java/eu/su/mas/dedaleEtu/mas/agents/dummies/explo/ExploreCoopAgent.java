package eu.su.mas.dedaleEtu.mas.agents.dummies.explo;

import java.util.ArrayList;
import java.util.List;
import eu.su.mas.dedale.mas.AbstractDedaleAgent;
import eu.su.mas.dedale.mas.agent.behaviours.startMyBehaviours;
import eu.su.mas.dedaleEtu.mas.behaviours.MainBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploring.ExploCoopBehaviour;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation;
import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
//import eu.su.mas.dedaleEtu.mas.knowledge.MapRepresentation.MapAttribute;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.HashMap;
import java.util.Iterator;

//import dataStructures.serializableGraph.SerializableNode;
import dataStructures.serializableGraph.SerializableSimpleGraph;

/**
 * <pre>
 * ExploreCoop agent. 
 * Basic example of how to "collaboratively" explore the map
 *  - It explore the map using a DFS algorithm and blindly tries to share the topology with the agents within reach.
 *  - The shortestPath computation is not optimized
 *  - Agents do not coordinate themselves on the node(s) to visit, thus progressively creating a single file. It's bad.
 *  - The agent sends all its map, periodically, forever. Its bad x3.
 *   - You should give him the list of agents'name to send its map to in parameter when creating the agent.
 *   Object [] entityParameters={"Name1","Name2};
 *   ag=createNewDedaleAgent(c, agentName, ExploreCoopAgent.class.getName(), entityParameters);
 *  
 * It stops when all nodes have been visited.
 * 
 * 
 * </pre>
 * 
 * @author hc
 *
 */

public class ExploreCoopAgent extends AbstractDedaleAgent {

	private static final long serialVersionUID = -7969469610241668140L;
	
	
	
	
	
	
	
	
	
	
	private IDCard idCard;
	private List<String> list_explorers ;
	private HashMap<String,Boolean> meetings;

	
	
	
	/**
	 * This method is automatically called when "agent".start() is executed.
	 * Consider that Agent is launched for the first time. 1) set the agent
	 * attributes 2) add the behaviors
	 * 
	 */

	protected void setup() {

		super.setup();

		
		
		singInYellowPage(this);
		
		idCard = new IDCard(this.getLocalName(), this.getAID(),(ArrayList<String>) this.getAgentsList() , 0);
		
		this.setList_explorers(this.getDFAgentsList("Explorer"));
		
		


		
		
		

		
		
		


		List<Behaviour> lb = new ArrayList<Behaviour>();

		/************************************************
		 * 
		 * ADD the behaviors of the Dummy Moving Agent
		 * 
		 ************************************************/

		lb.add(new MainBehaviour(this,this.idCard));

		/***
		 * MANDATORY TO ALLOW YOUR AGENT TO BE DEPLOYED CORRECTLY
		 */

		addBehaviour(new startMyBehaviours(this, lb));

		//System.out.println("the  agent " + this.getLocalName() + " is started");

	}


	public List<String> getAgentsList() {
		AMSAgentDescription[] agentsDescriptionCatalog = null;

		List<String> agentsNames = new ArrayList<String>();

		try {
			SearchConstraints c = new SearchConstraints();
			c.setMaxResults(Long.valueOf(-1));
			agentsDescriptionCatalog = AMSService.search(this, new AMSAgentDescription(), c);
		} catch (Exception e) {
			System.out.println("Problem searching AMS: " + e);
			e.printStackTrace();
		}
		for (int i = 0; i < agentsDescriptionCatalog.length; i++) {
			AID agentID = agentsDescriptionCatalog[i].getName();
			agentsNames.add(agentID.getLocalName());
		}

		agentsNames.remove("sniffeur");
		agentsNames.remove("GK");
		agentsNames.remove("Golem");
		agentsNames.remove("rma");
		agentsNames.remove("ams");
		agentsNames.remove("df");
		agentsNames.remove(this.getLocalName());
		return agentsNames;
	}


	public List<String> getDFAgentsList(String type) {

		List<String> agentsNames = new ArrayList<String>();
		DFAgentDescription dfd = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type); // the function of the agents
		dfd.addServices(sd);

		DFAgentDescription[] result;
		try {
			result = DFService.search(this, dfd);
			for (int i = 0; i < result.length; i++) {
				agentsNames.add(result[i].getName().getLocalName());
			}
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			System.out.println("Problem searching Yellow Page: " + e);
			e.printStackTrace();
		} // You get the list of all the agents (AID)offering this service
		
		agentsNames.remove(this.getLocalName());
		return agentsNames;
	}


	public void singInYellowPage(AbstractDedaleAgent me) {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(me.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Explorer");

		sd.setName(getLocalName());
		dfd.addServices(sd);
		
		
		
		DFAgentDescription yl;
		try {

			yl = DFService.register(me, dfd);
			System.out. println ( "-------\n"+yl+ "results \n--------" ) ;
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}

	public List<String> getList_explorers() {
		return list_explorers;
	}

	public void setList_explorers(List<String> list_explorers) {
		this.list_explorers = list_explorers;
	}
	
	
	public List<String> agentsToSend() {
		
		List<String> distionations =new ArrayList<String>();
		
		for (String s :this.meetings.keySet()) {
			if(this.meetings.get(s)) {
				distionations.add(s);
			}
		}
		
		return distionations;
	}
	


	public boolean isIdenticalList (List<String> receivers, List<String> h2) {
	    if ( receivers.size() != h2.size() ) {
	        return false;
	    }
	    List<String> clone = new ArrayList<String>(h2);

	    Iterator<String> it = receivers.iterator();
	    System.out.println("iterateur "+it);
	    while (it.hasNext() ){
	        String A = it.next();
	        if (clone.contains(A)){
	            clone.remove(A);
	        } else {
	            return false;
	        }
	    }
	    return true; 
	}
	
	
	
	
}
