
public class Vector2D {
	private int x, y;
	public Vector2D () {
		x = y = 0;
	}

	
	public Vector2D (int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	public int getX(){
		return x;
	}

	
	public int getY(){
		return y;
	}

	
	public void setX(int a){
		x = a;
	}

	
	public void setY(int b){
		y = b;
	}

	
	public Vector2D plus(Vector2D v) {
		if (v == null)
			return new Vector2D(x, y);
		return new Vector2D (x + v.x, y + v.y);
	}

	/**
	* Method to multiply a vector by a scalar
	* @param scalar scalar value
	*/
	public Vector2D times(int scalar) {
		return new Vector2D(x * scalar, y * scalar);
	}

	
	public Vector2D minus(Vector2D v) {
		if (v==null) return new Vector2D (x,y);
		else return new Vector2D(x - v.x, y - v.y);
	}
	
	public String toString() {
		return "Vector "+x+","+y;
	}
}