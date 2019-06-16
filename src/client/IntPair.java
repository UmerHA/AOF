package client;

import java.util.Objects;

public class IntPair {
	public int x;
	public int y;

	public IntPair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
	@Override
	public boolean equals(Object other) {
	    if (this == other)
	        return true;

	    if (other == null)
	        return false;
	   
	    if (getClass() != other.getClass())
	        return false;

	    IntPair otherPair = (IntPair) other;

	    return (this.x == otherPair.x) && (this.y == otherPair.y);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
