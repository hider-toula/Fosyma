package eu.su.mas.dedaleEtu.princ;

import eu.su.mas.dedale.env.EnvironmentType;
import eu.su.mas.dedale.env.GeneratorType;

/**
 * Configuration file for a Dedale instance 
 * 1) Network and platform parameters
 * 2) Environment parameters 
 * 
 * 
 * @author hc
 *
 */
public final class ConfigurationFile {


	/************************************
	 ***********************************
	 *
	 * 1) Network and platform parameters
	 * 
	 ***********************************/

	public static boolean PLATFORMisDISTRIBUTED= false;
	public static boolean COMPUTERisMAIN= true;

	public static String PLATFORM_HOSTNAME="127.0.0.1";
	public static String PLATFORM_ID="Ithaq";
	public static Integer PLATFORM_PORT=8887;
	
	public static String LOCAL_CONTAINER_NAME=PLATFORM_ID+"_"+"container1";
	public static String LOCAL_CONTAINER2_NAME=PLATFORM_ID+"_"+"container2";
	public static String LOCAL_CONTAINER3_NAME=PLATFORM_ID+"_"+"container3";
	public static String LOCAL_CONTAINER4_NAME=PLATFORM_ID+"_"+"container4";


	/************************************
	 ************************************
	 *
	 * 2) Environment parameters 
	 * 
	 ************************************/

	/**
	 * The environment is either a GraphStream (2D discrete) or JME (3D continuous) one.
	 */
	public static EnvironmentType ENVIRONMENT_TYPE=EnvironmentType.GS;
	
	/**
	 * The environment is either manually designed, or generated with a specific generator
	 */
	public static GeneratorType GENERATOR_TYPE=GeneratorType.MANUAL;

	/**
	 * 	The GateKeeper is in charge of the Platform and of the agents within, do not change its name.
	 */
	public static String DEFAULT_GATEKEEPER_NAME="GK";


	/************************************
	 *
	 * 2-a) Environment parameters when the environment is loaded. We need :
	 *  - a topology, 
	 *  - the configuration of the elements on the map,
	 *
	 *  These parameters must be empty if the environment is generated or already online 
	 *****************************/
	
	/**
	 * Give the topology 
	 */
	//public static String INSTANCE_TOPOLOGY=null;
	
	public static String INSTANCE_TOPOLOGY="resources/topology/map2020-topologyExam1-graph.dgs";
	//public static String INSTANCE_TOPOLOGY="resources/topology/HouatTopology";
	//public static String INSTANCE_TOPOLOGY="resources/topology/map2020-topologyExam1-tree.dgs";
	//public static String INSTANCE_TOPOLOGY="resources/topology/map2020-topologyExam1-tree.dgs";
	//public static String INSTANCE_TOPOLOGY="resources/interlocking/mapInterlocking2-topology";
	//public static String INSTANCE_TOPOLOGY="resources/map2018-topology";
	//public static String INSTANCE_TOPOLOGY="resources/map2019-topologyExam1";

	
	/**
	 * Give the elements available on the map, if any
	 */
	// If the environment is loaded but you do not want to define elements on the map
	public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/distributedExploration/emptyMap";
	
	// otherwise
	//public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/treasureHunt/map2019-elementsExam1";
	//public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/treasureHunt/Houat-elements";
	//public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/treasureHunt/map2018-elements-ica";
	//public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/interlocking/mapInterlocking2-elements";
	//public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/map2018-elements";
	//public static String INSTANCE_CONFIGURATION_ELEMENTS="resources/mapInterlocking2-elements";
	
	
	
	/************************************
	 * 
	 * 
	 * 2-b) Environment parameters when it is generated 
	 * 
	 * 
	 ***********************************/

	/**
	 * Size of the generated environment, mandatory
	 */
	public static Integer ENVIRONMENT_SIZE=10;
	// Parameters required for some generators (see dedale.gitlab.io)
	public static Integer OPTIONAL_ADDITIONAL_ENVGENERATOR_PARAM1=1;//used by the BARABASI_ALBERT generator to know the number of childs
	public static Integer[] GENERATOR_PARAMETERS= {ENVIRONMENT_SIZE,OPTIONAL_ADDITIONAL_ENVGENERATOR_PARAM1};

	/**
	 * Wumpus proximity detection radius
	 */
	public static final Integer DEFAULT_DETECTION_RADIUS = 1;


	/**
	 * 	Agents communication radius
	 */
	public static Integer DEFAULT_COMMUNICATION_REACH=3;

	/**
	 * Elements on the map
	 */
	
	/**true if a grid environment should be generated, false otherwise (A dogoronev env is generated)**/
	
	public static boolean ACTIVE_WELL=false;
	public static boolean ACTIVE_GOLD=true;
	public static boolean ACTIVE_DIAMOND=false;

	/************************************
	 ************************************
	 *
	 * 3) Agents characteristics
	 * 
	 ************************************/

	/**
	 * Must'nt be null as it describes the native agents' capabilities 
	 */
	//public static String INSTANCE_CONFIGURATION_ENTITIES=null;
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/agentExplo";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/agentExplo-2";
	public static String INSTANCE_CONFIGURATION_ENTITIES="resources/agentExploCoop-2";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/agentKeyboardControlled";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/hunt/map2020-entitiesTree";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/hunt/map2020-entitiesGraph";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/map2018-entities2";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/map2019-entitiesExam1";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/monoAgent-entities";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="resources/mapInterlocking2-entities";
	//public static String INSTANCE_CONFIGURATION_ENTITIES=null;
	


	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2018-agentExplo";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/agentExploSolo";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/agentExploCoop-2";	
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2018-agentTanker";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2018-agentCollect";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2018-entities";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2018-agentGolem";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2018-agentTankerCollect";
	//public static String INSTANCE_CONFIGURATION_ENTITIES="src/test/java/resources/map2019-entitiesExam1";

}
