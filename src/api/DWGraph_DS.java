package api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;



public class DWGraph_DS implements directed_weighted_graph{
	/**
	 * implementation of directional weighted graph
	 * including imlementations of the following methods :
	 * • getNode()
	 * •getEdge()
	 * • addNode()
	 * •connect()
	 * • getV()
	 * • getE()
	 * • removeNode()
	 * • removeEdge()
	 * • nodeSize()
	 * • edgeSize()
	 * • getMC()
	 * • equals()
	 * • toString()
	 */
	private HashMap <Integer ,node_data > nodes;
	private HashMap<Integer, HashMap<Integer, edge_data>> Ni;
	private int MC;
	private int edgeSize;
	private int nodeSize;
	
	public DWGraph_DS() {
		this.nodes = new HashMap<>();
		this.Ni = new HashMap<>();
	}
	/**
	 * return the node with the following key
	 * complexity -->O(1)
	 * @param key
	 */
	@Override
	public node_data getNode(int key) {
		return nodes.get(key);
	}
	/**
	 * return the edge between the src and the dest
	 * complexity -->O(1)
	 * @param src
	 * @param dest
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		if(Ni.get(src) != null && Ni.get(src).get(dest) != null) return Ni.get(src).get(dest);
		return null;
	}
	/**
	 * adds a node to the graph 
	 * complexity -->O(1)
	 * @param n
	 */
	@Override
	public void addNode(node_data n) {
		if(nodes.containsKey(n.getKey()) != true) {
			nodes.put(n.getKey(), n);
			Ni.put(n.getKey(), new HashMap<>());	
			MC++;
			nodeSize++;
		}	
	}
	/**
	 * connecets two nodes and adds an edge between them
	 * and inits the weight of the edge
	 * complexity -->O(1)
	 * @param src
	 * @param dest
	 * @param w
	 */
	@Override
	public void connect(int src, int dest, double w) {
		if(w < 0) return;
		edge_data ed = new EdgeData(src, dest, w);
		if(Ni.get(src).containsKey(dest) != true && src != dest) {
			Ni.get(src).put(dest, ed);
			MC++;
			edgeSize++;
		}
	}
	/**
	 * return the all the vertecies of the specific graph
	 * complexity -->O(1)
	 */
	@Override
	public Collection<node_data> getV() {
		return nodes.values();
	}
	/**
	 * the method gets a vertex and it return all his neighbors
	 * complexity -->O(1)
	 * @param node_id
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		Collection <edge_data> cln = new ArrayList<>();
		if(Ni.get(node_id) == null) return cln;
		for(Integer i : Ni.get(node_id).keySet()) {
			cln.add(getEdge(node_id, i));
		}
		return cln;
	}
	/**
	 * removes a node with a given key from the graph
	 * complexity -->O(1)
	 * @param key
	 */
	@Override
	public node_data removeNode(int key) {
		if(nodes.containsKey(key) == false) return null;
		for(Integer i : Ni.get(key).keySet()) {
			Ni.get(i).remove(key);
			edgeSize--;
		}
		node_data n = nodes.remove(key);
		nodes.remove(key);
		MC++;
		nodeSize--;
		return n;
	}
	/**
	 * removes an edge between the src node and the dest node
	 * complexity -->O(1)
	 * @param src
	 * @param dest
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		if(Ni.get(src).containsKey(dest) == false) return null;
		edge_data ed = Ni.get(src).remove(dest);
		edgeSize--;
		MC++;
		return ed;
	}
	/**
	 * return the number of the vertecies that are in the graph
	 * complexity -->O(1)
	 */
	@Override
	public int nodeSize() {
		return this.nodeSize;
	}
	/**
	 * return the number of the edges that are in the graph
	 * complexity -->O(1)
	 */
	@Override
	public int edgeSize() {
		return this.edgeSize;
	}
	/**
	 * return the number of the changes that were done on the specified
	 * graph
	 * complexity -->O(1)
	 */
	@Override
	public int getMC() {
		return this.MC;
	}
	/**
	 * retrun true or false if there is an equation between two graphs
	 * complexity -->O(1)
	 * @param ojb
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DWGraph_DS other = (DWGraph_DS) obj;
		if (MC != other.MC)
			return false;
		if (Ni == null) {
			if (other.Ni != null)
				return false;
		} else if (!Ni.equals(other.Ni))
			return false;
		if (edgeSize != other.edgeSize)
			return false;
		if (nodeSize != other.nodeSize)
			return false;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		} else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}
	/**
	 * prints the nodes and the edges of a specified graph
	 * complexity -->O(1)
	 */
	public String toString() {
		String str = "Edges ";
		for(node_data nd : getV()) {
			str+=getE(nd.getKey())+",";
		}
		str+="Nodes ";
		for(node_data nd : getV()) {
			str+=nd.getLocation()+" ";
			str+=nd.getKey()+" ";
		}
		return str;
	}

}