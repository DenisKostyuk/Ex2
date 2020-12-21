package api;

public class NodeData implements node_data  {
	/**
	 * class that represtets a ndoe in a directed weighted graph
	 * Inculding the implementation of the following methods:
	 * • NodeData()
	 * • getKey()
	 * • getLocation();
	 * • setLocation()
	 * • getWeight()
	 * • setWeight()
	 * • getInfo()
	 * • setInfo()
	 * • getTag()
	 * • setTag()
	 * • equals()
	 * • toString()
	 */
	private int key;
	private geo_location nodeLocation;
	private double weight;
	private String info;
	private int tag;
	
	/**
	 * implementation of Node object which get a key
	 * as argument and makes a new Node type object
	 * complexity --> O(1)
	 * @param key
	 */
	public NodeData(int key ) {
		this.key = key;
		this.nodeLocation = null;
	}
	/**
	 * returns the key of a specified Node
	 * complexity --> O(1)
	 */
	public int getKey() {
		return this.key;
	}
	/**
	 * returns the Location of a specified Node
	 * complexity --> O(1)
	 */
	@Override
	public geo_location getLocation() {
		return this.nodeLocation;
	}
	/**
	 * sets the location of a specified Node
	 * complexity --> O(1)
	 * @param p
	 */
	@Override
	public void setLocation(geo_location p) { //---> to check if this is legit (this equation)
		this.nodeLocation = p;
	}
	
	/**
	 * return the weight of a specified Node
	 * complexity --> O(1)
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}
	/**
	 * sets the weight of a specified Node
	 * complexity --> O(1)
	 * @param w
	 */
	@Override
	public void setWeight(double w) {
		this.weight = w;
		
	}
	
	/**
	 * return the Info of a specified Node
	 * complexity --> O(1)
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * sets the Info of a specified Node
	 * complexity --> O(1)
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info = s;
	}
	/**
	 * return the Tag of a specified Node
	 * complexity --> O(1)
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	/**
	 * sets the Tag of a specified Node
	 * complexity --> O(1)
	 * @param t
	 */
	@Override
	public void setTag(int t) {
		this.tag = t;
		
	}
	/**
	 * returns true or false if there is an equation between
	 * two nodes
	 * complexity --> O(1)
	 * @param obj
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeData other = (NodeData) obj;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (key != other.key)
			return false;
		if (nodeLocation == null) {
			if (other.nodeLocation != null)
				return false;
		} else if (!nodeLocation.equals(other.nodeLocation))
			return false;
		if (tag != other.tag)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}
	/**
	 * toString function which prints the Node 
	 * complexity --> O(1)
	 */
	public String toString() {
		return this.key +"pos = "+this.nodeLocation;
	}

}