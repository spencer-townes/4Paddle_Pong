
public enum enuPaddleType {
	
	//=======================================================
	VERTICAL		(0, 1),
	HORIZONTAL	(1, 0);
	
	//======================================================= Properties

	private int xMult, yMult;

	//=======================================================Constructors
	
	private enuPaddleType(int xMult, int yMult) {
		this.xMult = xMult;
		this.yMult = yMult;
	}
	
	//======================================================= Getters

	public int getXMult() { return xMult; }
	public int getYMult() { return yMult; }
}

