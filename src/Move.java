public class Move {
	private int start_col;
	private int end_col;
	private int start_row;
	private int end_row;
	
	public Move(int start_c, int end_c, int start_r, int end_r) {
		this.start_col = start_c;
		this.end_col = end_c;
		this.start_row = start_r;
		this.end_row = end_r;
	}
	
	//start_col getter és setter
	public int getStartCol() {
		return this.start_col;
	}
	
	public void setStartCol(int start_c) {
		this.start_col = start_c;
	}
	
	//end_col getter és setter
	public int getEndCol() {
		return this.end_col;
	}
	
	public void setEndCol(int end_c) {
		this.end_col = end_c;
	}
	
	
	//start_row getter és setter
	public int getStartRow() {
		return this.start_row;
	}
	
	public void setStartRow(int start_r) {
		this.start_row = start_r;
	}
	
	//end_row getter és setter
	public int getEndRow() {
		return this.end_row;
	}
	
	public void setEndRow(int end_r) {
		this.end_row = end_r;
	}
}
