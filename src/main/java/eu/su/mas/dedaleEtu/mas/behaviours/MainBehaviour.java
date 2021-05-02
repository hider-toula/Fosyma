package eu.su.mas.dedaleEtu.mas.behaviours;

import eu.su.mas.dedaleEtu.mas.behaviours.comunicating.ConnectionBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.comunicating.ShareMapBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.exploring.ExploCoopBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.hunting.AskForHelpBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.hunting.AskedForHelpBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.hunting.HuntBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.hunting.IsBlockedBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.hunting.PatrolBehaviour;
import eu.su.mas.dedaleEtu.mas.behaviours.hunting.RestBehaviour;
import eu.su.mas.dedaleEtu.mas.knowledge.IDCard;
import jade.core.behaviours.FSMBehaviour;

public class MainBehaviour extends FSMBehaviour {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IDCard idCard;
	
    public ExploCoopBehaviour explorBehaviour;
    public ShareMapBehaviour shareMapBehaviour;
    public ConnectionBehaviour connectionBehaviour;
    
    public HuntBehaviour huntingBehaviour;
    public PatrolBehaviour patrolBehaviour;

	private AskForHelpBehaviour askForHelpBehaviour;

	private AskedForHelpBehaviour askedForHelpBehaviour;


	private RestBehaviour RestBehaviour;

	private IsBlockedBehaviour isBlockedBehaviour;

	private HuntBehaviour huntBehaviour;





    //public Conversation conversationBehaivour;


    public MainBehaviour(eu.su.mas.dedaleEtu.mas.agents.dummies.explo.ExploreCoopAgent exploreCoopAgent,IDCard idCard){
        
    	super(exploreCoopAgent);
        this.idCard = idCard;
        
        
        explorBehaviour = new ExploCoopBehaviour(exploreCoopAgent,idCard);
        shareMapBehaviour = new ShareMapBehaviour(exploreCoopAgent,idCard);

        connectionBehaviour = new ConnectionBehaviour(exploreCoopAgent, idCard);
        patrolBehaviour = new PatrolBehaviour(exploreCoopAgent, idCard);
        
        huntBehaviour = new HuntBehaviour(exploreCoopAgent, idCard);

        askForHelpBehaviour = new AskForHelpBehaviour(exploreCoopAgent, idCard);
        askedForHelpBehaviour = new AskedForHelpBehaviour(exploreCoopAgent, idCard);
        RestBehaviour=new RestBehaviour(exploreCoopAgent, idCard);
        isBlockedBehaviour =new IsBlockedBehaviour(exploreCoopAgent, idCard);


       
        String [] exploring = {"EXPLORING"};
        String [] comunicating = {"COMUNICATING"};
        String [] sharing = {"SHARING"};
        String [] patroling = {"PATROLING"};
        String [] ask = {"ASK"};
        String [] asked = {"ASKED"};
        
        String [] rest = {"REST"};
        String [] block = {"BLOCK"};
        String [] hunt = {"HUNT"};

      

        //Création des états
        registerFirstState(explorBehaviour,"EXPLORING");
        
        registerState(connectionBehaviour,"COMUNICATING");
        registerState(shareMapBehaviour,"SHARING");
        registerState(patrolBehaviour,"PATROLING");
        
        
        
        registerState(askForHelpBehaviour,"ASK");
        registerState(askedForHelpBehaviour,"ASKED");
        registerState(RestBehaviour,"REST");
        registerState(isBlockedBehaviour,"BLOCK");
        
        registerState(huntBehaviour,"Hunt");


        
        // phase d'exploration
        registerTransition("EXPLORING","COMUNICATING",1,comunicating);
        
        registerTransition("COMUNICATING","EXPLORING",2,exploring);
        registerTransition("COMUNICATING","SHARING",3,sharing);
        registerTransition("SHARING","EXPLORING",4,exploring);
        
        //phase de patrouille 
        registerTransition("EXPLORING","PATROLING",5,patroling);
        
        registerTransition("PATROLING","ASKED",6,asked);
        
        registerTransition("PATROLING","Hunt",7,hunt);
        
        registerTransition("Hunt","PATROLING",8,hunt);

        registerTransition("Hunt","ASK",9,ask);
        
        
        
        
        
        registerTransition("ASK","Hunt",10,hunt);
        registerTransition("ASKED","PATROLING",11,patroling);
        registerTransition("Hunt","BLOCK",12,block);
        
        
        registerTransition("BLOCK","Hunt",13,hunt);
        registerTransition("BLOCK","BLOCK",14,rest);
        
        



    }


}