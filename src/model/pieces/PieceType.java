package model.pieces;
/**
 * Types of pieces
 */

import model.pieces.types.*;

public enum PieceType {
	PAWN('P', new PawnPattern()),
	ROOK('R', new RookPattern()),
	KNIGHT('N', new KnightPattern()),
	BISHOP('B', new BishopPattern()),
	QUEEN('Q', new QueenPattern()),
	KING('K', new SimplifiedKingPattern());
	
	public final char c;
	public final MovementPattern pattern;
	
	PieceType(char c, MovementPattern pattern) {
		this.c = c;
		this.pattern = pattern;
	}

}