package model.pieces.types;

import java.util.LinkedList;
import java.util.List;

import model.board.Position;
import model.board.Square;
import model.pieces.Piece;
import model.pieces.PieceColor;

/**
 * A pattern that allows movement like a regular chess pawn.
 * @author Sebastian Paaske Tørholm
 */
public class PawnPattern implements MovementPattern {

	@Override
	public List<int[]> legalMoves(Piece piece, Position board) {
		LinkedList<int[]> moves = new LinkedList<int[]>();
		PieceColor color = piece.color();
		Square square = piece.location();
		int rank = square.rank(),
	    file = square.file();
		boolean isWhite = color == PieceColor.WHITE;
		int dir = isWhite ? 1 : -1; // direction to move
		{
			Piece p = board.get(file, rank+dir);
			if (p == null) { // can't capture by going forward
				moves.add(new int[]{0, dir});
		
				// can't skip across a piece
				if (rank == (isWhite ? 2 : Square.MAX_RANK - 1 )) { // starting row
					Piece p2 = board.get(file, rank+2*dir);
					if (p2 == null) // can't capture by going forward
						moves.add(new int[]{0, 2*dir});					
				}
			}
		} 	
		if (rank != (isWhite ? Square.MAX_RANK : 1 )) { // not final row
			{
				Piece p = board.get(file-1, rank+dir);
				if (p != null && p.color() != color)
					moves.add(new int[]{-1, dir});
			}
			{
				Piece p = board.get(file+1, rank+dir);
				if (p != null && p.color() != color)
					moves.add(new int[]{1, dir});
			}
		}
		return moves;
	}

}
