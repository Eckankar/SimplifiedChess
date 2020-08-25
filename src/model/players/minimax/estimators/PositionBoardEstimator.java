package model.players.minimax.estimators;

import model.board.*;
import model.pieces.Piece;
import model.pieces.PieceColor;

/**
 * A simple BoardEstimator that evaluates solely based on which pieces are on the board.
 * @author Sebastian Paaske Tørholm
 */
public class PositionBoardEstimator implements BoardEstimator {
	private SquareFactory sqFactory;
	
	public PositionBoardEstimator() {
		sqFactory = new IntSquareFactory();
	}
	
	@Override
	public int boardEstimate(Position board) {
		int sum = 0;
		for (Square sq : sqFactory) {
			Piece p = board.get(sq);
			sum += evaluatePiece(p);
		}
		
		return sum;
	}

	@Override
	public int estimateChange(Position pos, Square square, Piece piece) {
		return estimateChange(pos, square.file(), square.rank(), piece);
	}

	@Override
	public int estimateChange(Position pos, int file, int rank, Piece piece) {
		return evaluatePiece(piece) - evaluatePiece(pos.get(file, rank));
	}
	
	/**
	 * Evaluates the value of a given piece.
	 * @param p piece
	 * @return value
	 */
	private static int evaluatePiece(Piece p) {
		int val = 0;
		
		if (p != null) {
			switch (p.type()) { // http://en.wikipedia.org/wiki/Chess_piece_point_value#Hans_Berliner.27s_system
				case PAWN:   val = 10; break;
				case KNIGHT: val = 32; break;
				case BISHOP: val = 33; break;
				case ROOK:   val = 51; break;
				case QUEEN:  val = 88; break;
				case KING:   val = 10000; break;
			}
			
			int sign = p.color() == PieceColor.WHITE ? 1 : -1;
			
			val *= sign;
		}
		return val;
	}
}
