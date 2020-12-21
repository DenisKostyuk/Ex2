package api;

public class EdgeData implements edge_data{
	/**
	 * Implementation of an edge class
	 * including the following methods :
	 * • getSrc()
	 * • getDest()
	 * • getWeight()
	 * • getInfo()
	 * • setInfo()
	 * • getTag()
	 * • setTag()
	 * • equals()
	 * • toString()
	 */
	private int src;
	private int dest;
	private double weight;
	private String info;
	private int tag;
		
	public EdgeData(int src , int dest , double weight) {
		this.src = src;
		this.dest = dest;
		this.weight = weight;
	}
	/**
	 * return the source of the edge
	 * complexity --> O(1)
	 */
	@Override
	public int getSrc() {
		return this.src;
	}
	/**
	 * return the destination of the edge
	 * complexity --> O(1)
	 */
	@Override
	public int getDest() {
		return this.dest;
	}
	/**
	 * return the weight of the edge
	 * complexity --> O(1)
	 */
	@Override
	public double getWeight() {
		return this.weight;
	}
	/**
	 * return the info of the edge
	 * complexity --> O(1)
	 */
	@Override
	public String getInfo() {
		return this.info;
	}
	/**
	 * sets the info of an edge
	 * complexity --> O(1)
	 * @param s
	 */
	@Override
	public void setInfo(String s) {
		this.info = s;
		
	}
	/**
	 * return the tag of the edge
	 * complexity --> O(1)
	 */
	@Override
	public int getTag() {
		return this.tag;
	}
	/**
	 * sets the tag of the edge
	 * complexity --> O(1)
	 * @param t
	 */
	@Override
	public void setTag(int t) {
		this.tag = t;
		
	}
	/**
	 * retrun true or false if there is an equation between
	 * two edges
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
		EdgeData other = (EdgeData) obj;
		if (dest != other.dest)
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (src != other.src)
			return false;
		if (tag != other.tag)
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}
	/**
	 * prints the edge 
	 * complexity --> O(1)
	 */
	public String toString() {
		return "src:" + this.src +"," +"w:" + this.weight +",dest:" + this.dest;
	}
	
	

}