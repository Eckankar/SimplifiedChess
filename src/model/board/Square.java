package model.board;

/**
 * A square on an chess board
 */
public interface Square {

	/**
	 * Symbolic constant for number of files of chess board
	 */
	public static final int MAX_FILE = 8; 

	/**
	 * Symbolic constant for number of ranks of chess board
	 */	
	public static final int MAX_RANK = 8; 
	
	/**
	 * Symbolic constant for the highest of MAX_FILE and MAX_RANK
	 */
	public static final int MAX_FILE_RANK = Math.max(MAX_FILE, MAX_RANK);
	
	/**
	 * The file (column) coordinate of this square
	 * @return the file coordinate
	 */
    public abstract int file();

	/**
	 * The rank (row) coordinate of this square
	 * @return
	 */
    public abstract int rank();
	
}