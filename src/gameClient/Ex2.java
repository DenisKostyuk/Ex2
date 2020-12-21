package gameClient;

import Server.Game_Server_Ex2;
import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.game_service;
import api.node_data;
import gameClient.util.Point3D;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import javax.print.attribute.standard.Destination;

public class Ex2 implements Runnable{
	private static MyFrame _win;
	private static Arena _ar;
	static ArrayList<CL_Pokemon> listOfPokemons;
	private static HashMap<CL_Pokemon , Integer> vis = new HashMap<>();
	private static HashMap<Integer , List<node_data>> paths = new HashMap<>();
	private static HashMap<CL_Agent , List<List<node_data>>> lastTry = new HashMap<>() ;
	static int scenario_num ;



	public static void main(String[] a) throws JSONException {
		Ex2 aa = new Ex2();
		MyPanel mf = new MyPanel(aa);
		mf.setVisible(true);
		Thread client = new Thread(new Ex2());
		MyPanel.trd = client;
	}

	@Override
	/**
	 * method that makes the run function which is necessary because 
	 * this class implements runnable
	 */
	public void run() {
		game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
		//System.out.println("Pokemons : " +game.getPokemons());
			int id = 323266254;
			game.login(id);
		JSONObject node;
		try {
			node = new JSONObject(game.getGraph());
			JSONArray nodesArray = node.getJSONArray("Nodes");
			JSONArray edgesArray = node.getJSONArray("Edges");
			directed_weighted_graph gg = buildGraph(nodesArray, node, edgesArray);
			String g = game.getGraph();
			String pks = game.getPokemons();
			init(game);

			game.startGame();
			_win.setTitle("Ex2 - OOP: (NONE trivial Solution) "+game.toString());
			int ind=0;
			long dt=100;

			while(game.isRunning()) {
				try {

					moveAgants(game, gg);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if(ind%1==0) {_win.repaint();}
					Thread.sleep(dt);
					ind++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		} catch (JSONException e2) {
			e2.printStackTrace();
		}	

		String res = game.toString();
		System.out.println(res);
		System.exit(0);
	}
	/**
	 * this method inits the Arena with the Agents and the Pokemons
	 * and puts the Agents in the right places
	 * Using the following Data Structures:
	 * • PriorityQueue
	 * • List
	 * @param game
	 */
	private void init(game_service game) {
		Pokemon_Comparator c = new Pokemon_Comparator();
		String graphString = game.getGraph();//-->gets the graph as a string json format {edges : [...] , nodes : [...]}
		String pokemonsString = game.getPokemons();//--> gets the pokemons as a json format 
		directed_weighted_graph graph = game.getJava_Graph_Not_to_be_used();//-->inits the graph as a string json format {edges : [...] , nodes : [...]}
		_ar = new Arena();//--->inits the arena 
		_ar.setGraph(graph);//--->inits the arena with the graph]
		_ar.setPokemons(Arena.json2Pokemons(pokemonsString));//-->inits the arena with the pokemons
		_win = new MyFrame("test ex2");
		_win.setSize(1000,700);
		_win.update(_ar);
		_win.setVisible(true);

		String info = game.toString();//-->init the string with the json format of the game with json format: GameServe {...}
		JSONObject line;
		try {
			line = new JSONObject(info);//--> init the line json format of {GameServer}
			JSONObject gameS = line.getJSONObject("GameServer");
			int numberOfAgents = gameS.getInt("agents");
			listOfPokemons = Arena.json2Pokemons(game.getPokemons());
			for(int i=0;i<listOfPokemons.size();i++) {
				Arena.updateEdge(listOfPokemons.get(i), graph);	
			}
			_ar.setPokemons(listOfPokemons);

			PriorityQueue<CL_Pokemon> queue =  queuePokemons(game);
			for(int j = 0 ; j<numberOfAgents ; j++) {
				int ind = j%listOfPokemons.size();
				CL_Pokemon clp = queue.poll();
				CL_Pokemon tmp = null ;
				for(CL_Pokemon ll : listOfPokemons) {
					if(ll.getValue() == clp.getValue()) {
						tmp = ll;
					}
				}
				int positionOfAgent = 0;
				int srcc = tmp.get_edge().getSrc();
				int destt = tmp.get_edge().getDest();
				if(tmp.getType() == 1) {
					if(srcc < destt) positionOfAgent = srcc;
					else positionOfAgent = destt;
				}else {
					if(srcc > destt) positionOfAgent = srcc;
					else positionOfAgent = destt;
				}
				game.addAgent(positionOfAgent);
			}
		}catch (Exception e) {

		}

	}

	/** 
	 * Moves each of the agents along the edge,
	 * the Agent always gets the pokemon with the highest value and runs to it 
	 * in order to catch him and move imidiatly to an other pokemon with VALUE priority 
	 *  of the pokemon.
	 *  Data Structures : 
	 *  •List<>
	 *  •HashMap<>
	 * @param game
	 * @param gg
	 * @param
	 * @throws JSONException 
	 */
	private static  void moveAgants(game_service game, directed_weighted_graph graph ) throws JSONException  {
		String gameM = game.move();
		setingTheHashMaps(game, graph,gameM);
		List<CL_Agent> agents = Arena.getAgents(gameM, graph);
		_ar.setAgents(agents);
		String pokString = game.getPokemons();
		List<CL_Pokemon> listop = Arena.json2Pokemons(pokString);
		_ar.setPokemons(listop);
		Iterator<Entry<Integer , List<node_data>>> it = paths.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Integer , List<node_data>> set = (Map.Entry<Integer, List<node_data>>) it.next();
			int idd = set.getKey();
			List<node_data> listWillRun = set.getValue();
			for(int j = 0; j<agents.size();j++) {
				if(agents.get(j).getID() == idd) {
					int dest = agents.get(j).getNextNode();
					agentRunShortestPath(agents.get(j).getValue(), agents.get(j), listWillRun, game, idd, graph, dest);
				}
			}
		}
	}

	/**
	 * this method is setting for each Agent a specific Pokemon so he will run to him
	 * and catch him.
	 * If there is a Pokemon that is already been chased after , the Agent will get
	 * an other Pokemon to chase after him.
	 * In this method we initialize the HashMap , so that the key will be the Agent 
	 * and the Value will be the path that the Agent will need to go through so he could
	 * catch the Pokemon.
	 * Each Agent will get 1 Pokemon at a time.
	 * @param game
	 * @param graph
	 * @param str
	 */
	public static  void setingTheHashMaps(game_service game, directed_weighted_graph graph,String str) {
		PriorityQueue<CL_Pokemon> pokemons = queuePokemons(game);
		String pokString = game.getPokemons();
		listOfPokemons = Arena.json2Pokemons(game.getPokemons());
		for(int j = 0;j<listOfPokemons.size();j++) {
			Arena.updateEdge(listOfPokemons.get(j), graph);
			vis.put(listOfPokemons.get(j), 0);
		}
		Collections.sort(listOfPokemons);
		_ar.setPokemons(listOfPokemons);
		List<CL_Agent> agents = Arena.getAgents(str, graph);
		dw_graph_algorithms graphA = new DWGraph_Algo();
		graphA.init(graph);
		for(int i = 0 ; i<agents.size();i++) {
			boolean flag = false;
			CL_Pokemon tmp = null;
			for(CL_Pokemon ll : listOfPokemons) {
				if(flag == false) {
					if(vis.get(ll) == 0) {
						tmp = ll;
						flag = true;
					}
				}
			}
			vis.put(tmp, 1);
			int id = agents.get(i).getID();
			int src = agents.get(i).getSrcNode();
			List<node_data> listOfNodes = new LinkedList<>();
			int toWhere = 0;
			int srcc = tmp.get_edge().getSrc();
			int destt = tmp.get_edge().getDest();
			if(tmp.getType() == 1) {
				if(srcc < destt) toWhere = srcc;//------>HERE
				else toWhere = destt;
			}else {
				if(srcc > destt) toWhere = srcc;
				else toWhere = destt;
			}
			listOfNodes = graphA.shortestPath(src,toWhere );
			if(listOfPokemons.get(i).getType() == 1) {
				if(srcc < destt) listOfNodes.add(graph.getNode(destt));
				else listOfNodes.add(graph.getNode(srcc));
			}else {
				if(srcc > destt) listOfNodes.add(graph.getNode(destt));
				else listOfNodes.add(graph.getNode(srcc));
			}
			if(!listOfNodes.isEmpty()) {
				listOfNodes.remove(0);
			}
			int last = listOfNodes.get(listOfNodes.size()-1).getKey();
			boolean flag2 = false;
			for(CL_Pokemon cla : listOfPokemons) {
				if(flag2 == false && vis.get(cla) == 0 && cla.get_edge().getSrc() == last) {
					int source = cla.get_edge().getSrc();
					int destination = cla.get_edge().getDest();
					if(last == source) {
						listOfNodes.add(graph.getNode(destination)); 
						flag2 = true;
					}
					else if(last == destination) {
						listOfNodes.add(graph.getNode(source));
						flag2 = true;
					}
				}
			}
			paths.put(id,listOfNodes);
			//}
		}
	}
	/**
	 * in this method the if the destination of the Agent is -1
	 * it setts the new path for the Agent to walk on so he could
	 * catch the Pokemon which is waiting to be eaten on the other sise
	 * of the path.
	 * The path is the shortest path between the Agent and the Pokemon
	 * This function Moves the Agent towards him
	 * @param val
	 * @param cla
	 * @param listOfNodes
	 * @param game
	 * @param id
	 * @param graph
	 * @param dest
	 */
	public static  void agentRunShortestPath(double val,CL_Agent cla,List<node_data> listOfNodes ,game_service game ,int id , directed_weighted_graph graph , int dest) {
		if(dest == -1) {
			while(!listOfNodes.isEmpty()) {
				int nextDest = listOfNodes.remove(0).getKey();
				game.chooseNextEdge(id, nextDest);
				cla.setNextNode(nextDest);
				System.out.println("Agent: "+id+", val: "+val+"   turned to node: "+nextDest);
			}
		}
	}

	/**
	 * this method makes a priority for each pokemon from the Arena
	 * and initialize the priority queue so that the pokemon with the highest
	 * value will be always at the TOP .
	 * the priority queue initilaizes from a JSON .
	 * Data Structures : 
	 * • PriorityQueue
	 * • List<>
	 * @param game
	 * @return
	 */
	public static PriorityQueue<CL_Pokemon>  queuePokemons(game_service game) 
	{
		try {
			JSONObject Pokemons;
			Pokemons = new JSONObject(game.getPokemons());
			Pokemon_Comparator comp = new Pokemon_Comparator();
			JSONArray PokemonsArray = Pokemons.getJSONArray("Pokemons");
			PriorityQueue<CL_Pokemon> qPokemons =new PriorityQueue<CL_Pokemon>(comp);
			for(int i=0;i<PokemonsArray.length();i++) {
				JSONObject pp;
				pp = PokemonsArray.getJSONObject(i);
				JSONObject pk = pp.getJSONObject("Pokemon");
				int t = pk.getInt("type");
				double v = pk.getDouble("value");
				String p = pk.getString("pos");
				CL_Pokemon f = new CL_Pokemon(new Point3D(p), t, v, 0, null);
				qPokemons.add(f);
			}
			return qPokemons;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * building a graph from the JSON object 
	 * by initializing the graph with the specified
	 * information that is given from the JSON 
	 * @param VertexArray
	 * @param Vertex
	 * @param EdgesArray
	 * @return
	 */
	public static directed_weighted_graph buildGraph(JSONArray VertexArray ,JSONObject Vertex,JSONArray EdgesArray) {
		try {
			directed_weighted_graph graph = new DWGraph_DS();
			for(int i=0; i<VertexArray.length(); i++) 
			{
				Vertex = VertexArray.getJSONObject(i);
				int n = Vertex.getInt("id");
				String pos = (String)Vertex.get("pos");
				node_data node = new NodeData(n);
				String [] s = pos.split(",");
				node.setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
				graph.addNode(node);
			}
			JSONObject edges = new JSONObject();
			for(int i=0; i<EdgesArray.length(); i++) 
			{
				edges = EdgesArray.getJSONObject(i);
				int src = edges.getInt("src");
				int dest = edges.getInt("dest");
				double w = edges.getDouble("w");
				graph.connect(src, dest, w);
			}
			return graph;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}



}