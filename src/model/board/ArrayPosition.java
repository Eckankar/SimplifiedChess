package model.board;

import java.util.List;

import model.pieces.Piece;

/**
 * Represents the positions of pieces on a chess board, storing them in an array.
 * @author Sebastian Paaske Tørholm
 */
public class ArrayPosition implements UpdatablePosition {
	private Piece[][] pieces; 
	
	/**
	 * Creates a new ArrayPosition.
	 * Must not contain two or more pieces that share a square.
	 * @param pieces array of Pieces to put on the board
	 */
	public ArrayPosition(Piece[] pieces) {
		this.pieces = new Piece[Square.MAX_FILE][Square.MAX_RANK];
		for (Piece piece : pieces) {
			Square sq = piece.location();
			assert this.pieces[sq.file()-1][sq.rank()-1] == null;
			this.pieces[sq.file()-1][sq.rank()-1] = piece; 
		}
	}
	
	/**
	 * Creates a new empty ArrayPosition.
	 */
	public ArrayPosition() { this(new Piece[]{}); }
	
	/**
	 * Creates a new ArrayPosition.
	 * Must not contain two or more pieces that share a square.
	 * @param pieces list of Pieces to put on the board
	 */	
	public ArrayPosition(List<Piece> pieces) { this(pieces.toArray(new Piece[]{})); }
	
	/**
	 * Creates a new ArrayPosition that contains the same elements as the input.
	 * @param position Position to copy
	 */
	public ArrayPosition(Position position) {
		if (position instanceof ArrayPosition)
			this.pieces = ((ArrayPosition)position).pieces;
		else {
			this.pieces = new Piece[Square.MAX_FILE][Square.MAX_RANK];
			SquareFactory sf = new IntSquareFactory();
			for (Square sq : sf) {
				Piece p = position.get(sq);
				if (p != null)
					this.pieces[sq.file()-1][sq.rank()-1] = p; 
			}
		}
	}
	
	@Override
	public Piece get(Square square) {
		try {
			return pieces[square.file()-1][square.rank()-1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	@Override
	public Piece get(int file, int rank) {
		try {
			return pieces[file-1][rank-1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public void set(Square square, Piece piece) {
		try {
			pieces[square.file()-1][square.rank()-1] = piece;
		} catch (ArrayIndexOutOfBoundsException e) { }
	}

	@Override
	public void set(int file, int rank, Piece piece) {
		try {
			pieces[file-1][rank-1] = piece;
		} catch (ArrayIndexOutOfBoundsException e) { }
	}

	@Override
	public Position deepClone() {
		UpdatablePosition position = new ArrayPosition();
		for (Piece[] pieceRow : pieces)
			for (Piece piece : pieceRow) {
				if (piece == null)
					continue;
				
				position.set(piece.location(), (Piece)piece.clone());
			}
		
		return position;
	}

}
