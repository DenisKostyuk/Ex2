package api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DWGraph_Algo implements dw_graph_algorithms , Comparator<node_data> {
	/**
	 * this class representing algorithms on a directional weighted graph.
	 * including the following methods:
	 * • init()
	 * • getGraph()
	 * • copy()
	 * • isConnected()
	 * • shortestPathDist()
	 * •shortestPath()
	 * • save()
	 * • load()
	 * • reverseGraph()
	 * • compare()
	 */
	private directed_weighted_graph g;
	List<Integer>[] graph;
	boolean[] visited;
	Stack<node_data> stack;
	int time;
	int[] lowlink;
	List<List<Integer>> components;
	
	public DWGraph_Algo() {}
	/**
	 * initialize the object DWGraph_Algo with the specified
	 * graph that we are going to do the written algorithms on
	 * complexity -> O(1)
	 * @param g
	 */
	@Override
	public void init(directed_weighted_graph g) {
		this.g = g;
		
	}
	/**
	 * return the graph that this class is working on
	 * complexity -> O(1)
	 */
	@Override
	public directed_weighted_graph getGraph() {
		return this.g;
	}
	/**
	 * makes a deep copy of the specified graph this class is working on
	 * complexity --> O(|v|) + O(|v|)*O(|e|) = O(|v|) * O(|e|)
	 */
	@Override
	public directed_weighted_graph copy() {
		directed_weighted_graph copied = new DWGraph_DS();
		for(node_data node : g.getV()) {
			copied.addNode(new NodeData(node.getKey()));
		}
		for(node_data nd : g.getV()) {
			for(edge_data ed : g.getE(nd.getKey())) {
				g.connect(nd.getKey(), ed.getDest(), ed.getWeight());
			}
		}
		return copied;
	}
	/**
	 * return true or false if the graph is connected 
	 * by saying connect the meaning is that we can travel from any node 
	 * to any other node we wish to . in case there is a node that cannot be reached , the
	 * method will return false, other wise will return true
	 * complexity --> O(|v|+|e|) + O(|v|) = O(|v|+|e|)
	 * the data structures that are used in this algorithm are
	 * •LinkedList
	 * •Queue
	 */
	@Override
	public boolean isConnected() {
		boolean ans1 = true;
		boolean ans2 = true;
		if(g == null) return true;
		Queue<node_data> q = new LinkedList<node_data>();
		Iterator<node_data> it = g.getV().iterator();
		if(g.nodeSize() == 0) return true; //{
		q.add(it.next());
		while(!q.isEmpty()) {
			node_data temp =  q.poll();
			if(temp.getInfo() != "b") temp.setInfo("b");				
			for(edge_data t : g.getE(temp.getKey())) {
				if(g.getNode(t.getDest()).getInfo() != "b" && g.getNode(t.getDest()).getInfo() != "g")  {//(t.getInfo()!="b" && t.getInfo()!="g") {
					q.add(g.getNode(t.getDest()));
					t.setInfo("g");
				}
			}
		}
		//}
		for(node_data n : g.getV()) { //O(|v|)
			if (n.getInfo() != "b") ans1 = false;
			n.setInfo("");
		}
		directed_weighted_graph rev = reverseGraph();
		if(rev == null) return true;
		Queue<node_data> q2 = new LinkedList<node_data>();
		Iterator<node_data> it2 = rev.getV().iterator();
		if(rev.nodeSize() == 0) return true; //{
		q2.add(it2.next());
		while(!q2.isEmpty()) {
			node_data temp =  q2.poll();
			if(temp.getInfo() != "b") temp.setInfo("b");				
			for(edge_data t : rev.getE(temp.getKey())) {
				if(rev.getNode(t.getDest()).getInfo() != "b" && rev.getNode(t.getDest()).getInfo() != "g")  {//(t.getInfo()!="b" && t.getInfo()!="g") {
					q2.add(rev.getNode(t.getDest()));
					t.setInfo("g");
				}
			}
		}
		for(node_data n : rev.getV()) { //O(|v|)
			if (n.getInfo() != "b") ans2 = false;
			n.setInfo("");
		}
		return ans1 && ans2;
	}
	
	/**
	 * gets a source and a destination keys as arguments and return the shortest
	 * path distance between those two nodes with the specified keys uses Dijkstra's algorithm . 
	 * the return is represented by minimum weight
	 * sum between each possible path to get from the source node to the destination node.
	 * if there is no such path , the method will return -1
	 * complexity -->  O (|v|+ |e*logv|) 
	 * the data structures that are used in this algorithm are : 
	 * • PriorityQueue
	 * @param src
	 * @param dest
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		if(g.getE(dest).isEmpty() || g.getE(src).isEmpty()) return -1;
		for(node_data n : g.getV()) {  
			n.setWeight((int) Double.POSITIVE_INFINITY);
		}
		g.getNode(src).setWeight(0);
		Comparator<node_data> c = new Comparator<node_data>() {
			public int compare(node_data ed1, node_data ed2) {
				return Double.compare(ed1.getWeight() , ed2.getWeight());
			}
		};
		PriorityQueue<node_data> pq = new PriorityQueue<node_data>(c);
		pq.add(g.getNode(src));
		while(!pq.isEmpty()) {
			node_data tmp = pq.poll(); 
			if(tmp.getInfo() != "visited") tmp.setInfo("visited");
			for(edge_data d: g.getE(tmp.getKey())) {
				if(g.getNode(d.getDest()).getInfo() != "visited") {
					if((tmp.getWeight() + g.getEdge(tmp.getKey(), g.getNode(d.getDest()).getKey()).getWeight() < g.getNode(d.getDest()).getWeight())) {
						g.getNode(d.getDest()).setWeight(tmp.getWeight() + g.getEdge(tmp.getKey(), g.getNode(d.getDest()).getKey()).getWeight()); 
						pq.add(g.getNode(d.getDest()));
					}
				}
			}
		}
		double dist = g.getNode(dest).getWeight();
		for(node_data n : g.getV()) {
			n.setInfo(null);
			n.setWeight(0);
		}
		return dist;
	}

	@Override
	/**
	 * return a list of nodes with the shortest path between a source node to a destination node.
	 * uses Dijkstra's algorithm which returns us the shortest path by counting the minimum weight 
	 * between nodes , adds it to the sum and will return the minimum weight from the source node 
	 * to the destination node
	 * complexity -->  O (|v|+ |e*logv|) 
	 * the data structures that are used in this algorithm are : 
	 * • PriorityQueue
	 * • HashMap
	 * • LinkedList
	 * @param src
	 * @param dest
	 */
	public List<node_data> shortestPath(int src, int dest) {
		if(g.getE(dest).isEmpty() || g.getE(src).isEmpty()) return null;
		for(node_data n : g.getV()) {  
			n.setWeight((int) Double.POSITIVE_INFINITY);
		}
		g.getNode(src).setWeight(0);
		Comparator<node_data> c = new Comparator<node_data>() {
			public int compare(node_data ed1, node_data ed2) {
				return Double.compare(ed1.getWeight() , ed2.getWeight());
			}
		};
		PriorityQueue<node_data> pq = new PriorityQueue<node_data>(c);
		HashMap<node_data, node_data> parents = new HashMap<node_data, node_data>();
		pq.add(g.getNode(src));
		while(!pq.isEmpty()) {
			node_data tmp = pq.poll(); 
			if(tmp.getInfo() != "visited") tmp.setInfo("visited");
			for(edge_data d: g.getE(tmp.getKey())) {
				if(g.getNode(d.getDest()).getInfo() != "visited") {
					if((tmp.getWeight() + g.getEdge(tmp.getKey(), g.getNode(d.getDest()).getKey()).getWeight() < g.getNode(d.getDest()).getWeight())) {
						g.getNode(d.getDest()).setWeight(tmp.getWeight() + g.getEdge(tmp.getKey(), g.getNode(d.getDest()).getKey()).getWeight()); 
						pq.add(g.getNode(d.getDest()));
						parents.put(g.getNode(d.getDest()), tmp);
					}
				}
			}
		}
		List<node_data> listOfThePath = new LinkedList<>();
		node_data tmp = g.getNode(dest);
		while(tmp != null) {
			listOfThePath.add(0, tmp); // O(distance(source , destination))
			tmp = parents.get(tmp);
		}
		for(node_data n : g.getV()) {
			n.setInfo(null);
			n.setWeight(0);
		}
		return listOfThePath;
	}
	
	/**
	 * saves the graph to a JSON file with specified fields
	 */
	@Override
	public boolean save(String file)  {
		try {
		JSONObject edgesObj = new JSONObject();
		JSONArray Edges = new JSONArray();
		JSONObject nodeObj = new JSONObject();
		JSONArray Nodes = new JSONArray();
		JSONObject AllObj = new JSONObject();

		Collection<node_data> node = g.getV();
		for (node_data node_data : node) {
			edgesObj = new JSONObject();
			Collection<edge_data> edges = g.getE(node_data.getKey());
			for (edge_data edge : edges) {
				edgesObj.put("src", edge.getSrc());
				edgesObj.put("w", edge.getWeight());
				edgesObj.put("dest", edge.getDest());
				Edges.put(edgesObj);
			}
		}

		AllObj.put("Edges", Edges);

		for (node_data node_data : node) {
			nodeObj = new JSONObject();
			String str = ""+node_data.getLocation();
			nodeObj.put("pos", str);
			nodeObj.put("id", node_data.getKey());
			Nodes.put(nodeObj);
			str ="";
		}

		AllObj.put("Nodes", Nodes);
		try 
		{
			PrintWriter pw = new PrintWriter("graph.json");
			pw.write(AllObj.toString());
			pw.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}catch (Exception e) {
		return false;
	}
}
	/**
	 * loads the graph information from a JSON file 
	 * and creates a graph
	 */
	@Override
	public boolean load(String file) {
		try 
		{
			directed_weighted_graph loaded = new DWGraph_DS();
			Scanner scanner = new Scanner(new File(file));
			String jsonString = scanner.useDelimiter("\\A").next();
			scanner.close();

			JSONObject vertex = new JSONObject();
			JSONObject edges = new JSONObject();
			JSONObject jsonObject = new JSONObject(jsonString);

			JSONArray vertexArray = jsonObject.getJSONArray("Nodes");

			for(int i=0; i<vertexArray.length(); i++) 
			{
				vertex = vertexArray.getJSONObject(i);
				int n = vertex.getInt("id");
				String pos = (String)vertex.get("pos");
				node_data node = new NodeData(n);
				String [] s = pos.split(",");
				node.setLocation(new GeoLocation(Double.parseDouble(s[0]), Double.parseDouble(s[1]), Double.parseDouble(s[2])));
				loaded.addNode(node);
			}

			JSONArray edgesArray = jsonObject.getJSONArray("Edges");
			for(int i=0; i<edgesArray.length(); i++) 
			{
				edges = edgesArray.getJSONObject(i);
				int src = edges.getInt("src");
				int dest = edges.getInt("dest");
				double w = edges.getDouble("w");
				loaded.connect(src, dest, w);
			}
			this.g = loaded;
		}
		catch(FileNotFoundException | JSONException e)
		{
			e.printStackTrace();
		}

		return true;
	}
	/**
	 * makes a revers of the graph that this function
	 * works on.
	 * complexity --> O(|V|+|E|)
	 * @return reversed Graph
	 */
	private directed_weighted_graph reverseGraph() {
		directed_weighted_graph reversed = new DWGraph_DS();
		for(node_data n : getGraph().getV()) {
			reversed.addNode(new NodeData(n.getKey()));
		}
		for(node_data nd : getGraph().getV()) {
			for(edge_data ed : getGraph().getE(nd.getKey())) {
				reversed.connect(ed.getDest(), ed.getSrc(),ed.getWeight());
			}
		}
		return reversed;
	}

	public int compare(node_data ed1, node_data ed2) {
		if (ed1.getWeight() < ed2.getWeight())
			return -1;
		if (ed1.getWeight() > ed2.getWeight())
			return 1;
		return 0;
	}


	
}