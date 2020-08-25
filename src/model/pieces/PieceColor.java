package model.pieces;
/**
 * The colors of pieces
 */

public enum PieceColor {
	BLACK('B'),
	WHITE('W');
	
	public final char c;
	public final PieceColor other() {
		if (this==BLACK) return WHITE;
		else return BLACK;
	}
	
	PieceColor(char c) {
		this.c = c;
	}
}
