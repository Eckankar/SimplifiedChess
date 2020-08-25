package model.board;

import model.pieces.*;

/**
 * A more detailed Move.
 * @author Sebastian Paaske Tørholm
 */
public class DetailedMove extends Move {
	/**
	 * Indicates that there was a capture in the move.
	 */
	public static final int FLAG_CAPTURE          = 0x1;
	/**
	 * Indicates that the move consisted of queenside castling.
	 */
	public static final int FLAG_CASTLE_QUEENSIDE = 0x02;
	/**
	 * Indicates that the move consisted of kingside castling.
	 */
	public static final int FLAG_CASTLE_KINGSIDE  = 0x04;
	/**
	 * Indicates that the move brought the other player into check.
	 */
	public static final int FLAG_CHECK            = 0x08;
	/**
	 * Indicates that the move brought the other player into check mate.
	 */
	public static final int FLAG_CHECKMATE        = 0x18; // automatically is FLAG_CHECK
	
	/*-------------------------------------------------------------*/
	
	private Piece targetPiece;
	private PieceType pieceType, promoteTo;
	private boolean capture, check, checkmate;
	private String uniqueIdentifier; // identifies the moving piece uniquely for ACN
	
	/**
	 * Creates a DetailedMove.
	 * @param from source square of move
	 * @param to target square of move
	 * @param mover piece that performs the move
	 * @param target piece that is targetted by the move (can be null if no capture takes place)
	 * @param startingBoard state of the board prior to the move
	 * @param flags bitfield of flags for the move
	 * @require from != null && to != null && mover != null
	 * @require !from.equals(to)
	 */
	public DetailedMove(Square from, Square to, 
			            Piece mover, Piece target,
			            Position startingBoard,
			            int flags) {
		super(from, to);
		assert mover != null;
		
		this.addFlags(flags);
		this.targetPiece = target;
		this.pieceType = mover.type();
		
		// We need to make sure if the piece location is ambiguous in Algebraic Chess Notation
		IntSquareFactory sqFactory = new IntSquareFactory();
		boolean rankClash = false, fileClash = false, ambiguity = false;
		
		for (Square sq : sqFactory) {
			if (sq.equals(from))
				continue; // skip the piece itself
			Piece p = startingBoard.get(sq);
			if ((p == null) 
			||  (p.color() != mover.color())       // we are looking for our other pieces
		    ||  (p.type() != mover.type())         // of the same type
		    ||  (!p.canMoveTo(startingBoard, to))) // that can move to the same spot
				continue;
			
			ambiguity = true;
			rankClash = rankClash || (p.location().rank() == from.rank());
			fileClash = fileClash || (p.location().file() == from.file());
		}
		
		if (!ambiguity)
			uniqueIdentifier = "";
		else if (!fileClash)
			uniqueIdentifier = new Character((char)('a'+from.file()-1)).toString();
		else if (!rankClash)
			uniqueIdentifier = ""+from.rank();
		else
			uniqueIdentifier = from.toString();
	}
	
	/**
	 * Adds flags to this move.
	 * @param flags bitfield of flags
	 */
	public void addFlags(int flags) {
		if ((flags & FLAG_CAPTURE) == FLAG_CAPTURE) {
			capture = true;
		}
		
		if ((flags & FLAG_CHECK) == FLAG_CHECK)
			check = true;
		
		if ((flags & FLAG_CHECKMATE) == FLAG_CHECKMATE)
			checkmate = true;
	}
	
	/**
	 * Sets that this move promotes a piece.
	 * @param promoteTo the type it promotes to - null to unset it
	 */
	public void setPromotion(PieceType promoteTo) {
		this.promoteTo = promoteTo;
	}
	
	/**
	 * @return true iff the move makes a capture
	 */
	public boolean capture() { return capture; }
	/**
	 * @return true iff the move brings the opponent into check
	 */
	public boolean check() { return check; }
	/**
	 * @return true iff the move brings the opponent into checkmate
	 */
	public boolean checkmate() { return checkmate; }
	
	/**
	 * @return the move in Algebraic Chess Notation
	 */
	public String inACN() {
		return String.format("%s%s%s%s%s%s",
				             pieceType.c == 'P' ? "" : Character.toString(pieceType.c), 
				             uniqueIdentifier, capture ? "x" : "",
				             to.toString(),
				             promoteTo == null ? "" : "="+Character.toString(promoteTo.c),
				             checkmate ? "#" : check ? "+" : "");
	}
	
	/**
	 * @return the piece captured by this move
	 * @require this.capture()
	 */
	public Piece capturedPiece() {
		assert capture;
		return targetPiece;
	}
}
