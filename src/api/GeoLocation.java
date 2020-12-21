package api;

public class GeoLocation implements geo_location {
	/**
	 * Implementation of a geo_location 
	 * Inculding the implementation of the following methods:
	 * •x()
	 * •y()
	 * •z()
	 * •distance()
	 * •equals()
	 * •toString()
	 */
	private double x;
	private double y;
	private double z;
	/**
	 * implementation of the GeoLocation
	 * complexity --> O(1)
	 * @param x
	 * @param y
	 * @param z
	 */
	public GeoLocation(double x, double y , double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	/**
	 * return the x of the location
	 * complexity --> O(1)
	 */
	public double x() {
		return x;
	}
	/**
	 * return the y of the location
	 * complexity --> O(1)
	 */
	@Override
	public double y() {
		return y;
	}
	/**
	 * return the z of the location
	 * complexity --> O(1)
	 */
	@Override
	public double z() {
		return z;
	}
	/**
	 * returns the distance between two locations
	 * complexity --> O(1)
	 * @param g
	 */
	@Override
	public double distance(geo_location g) {
		double distX = Math.pow((this.x - g.x()),2);
		double distY = Math.pow((this.y - g.y()),2);
		double distZ = Math.pow((this.z - g.z()),2);
		double solution = Math.sqrt(distX + distY + distZ);
		return solution;
	}
	/**
	 * return true or false if there is a equation between
	 * two locations
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
		GeoLocation other = (GeoLocation) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	/**
	 * Prints the location
	 * complexity --> O(1)
	 */
	public String toString() {
		return "" +this.x +","+this.y+ ","+ this.z;
	}

}