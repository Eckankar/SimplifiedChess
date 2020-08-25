package model.board.generators;

import java.util.LinkedList;
import java.util.List;

import model.board.*;
import model.pieces.*;

/**
 * Implementation of PositionFactory that can generate positions based on arrays of strings.
 * @author Sebastian Paaske Tørholm
 */
public class GenericPositionFactory implements PositionFactory {
	private List<Piece> blackPieces, whitePieces, allPieces;
	private UpdatablePosition position;
	
	/**
	 * Creates a new GenericPositionFactory.
	 * startingBoard must be Square.MAX_RANK * Square.MAX_FILE.
	 * Each entry in startingBoard must be either "" or a 2-character string
	 * of the form "CT", where "C" is the color of the piece ("B" for black,
	 * "W" for white), and "T" is the type of the piece ("P" for pawn, "R" for
	 * rook, "N" for knight, "B" for bishop, "Q" for queen, "K" for king). 
	 * @param startingBoard 2d array of starting pieces.
	 */
	public GenericPositionFactory(String[][] startingBoard) {
		blackPieces = new LinkedList<Piece>();
		whitePieces = new LinkedList<Piece>();
		allPieces   = new LinkedList<Piece>();
		SquareFactory sqFactory = new IntSquareFactory();
		
		try {
			for (int rank = 0; rank < Square.MAX_RANK; rank++) {
				String[] rankArr = startingBoard[rank];
				for (int file = 0; file < Square.MAX_FILE; file++) {
					char[] tile = rankArr[file].toUpperCase().toCharArray();
					if (tile.length == 0)
						continue;
					assert tile.length == 2;
					PieceColor color = null; // initialize to remove warnings
					PieceType type = null;   // won't make it uninitialized through
											 // if asserts are enabled.
					switch (tile[0]) {       
						case 'W':
							color = PieceColor.WHITE;
							break;
						case 'B':
							color = PieceColor.BLACK;
							break;
						default:
							assert false : "first character must be either W or B";
					}
					switch (tile[1]) {
						case 'P':
							type = PieceType.PAWN;
							break;
						case 'R':
							type = PieceType.ROOK;
							break;
						case 'N':
							type = PieceType.KNIGHT;
							break;
						case 'B':
							type = PieceType.BISHOP;
							break;
						case 'Q':
							type = PieceType.QUEEN;
							break;
						case 'K':
							type = PieceType.KING;
							break;
						default:
							assert false : "second character must be in {P,R,N,B,Q,K}";
					}
					Piece newPiece = new GenericPiece(color, sqFactory.create(file+1, rank+1), type);
					if (color == PieceColor.BLACK)
						blackPieces.add(newPiece);
					else
						whitePieces.add(newPiece);
					
					allPieces.add(newPiece);
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			assert false : "array must be MAX_RANK * MAX_FILE";
		}
		
		position = new ArrayPosition(allPieces);
	}
	
	@Override
	public List<Piece> blackPieces() {
		return blackPieces;
	}

	@Override
	public Position position() {
		return position;
	}

	@Override
	public List<Piece> whitePieces() {
		return whitePieces;
	}
	
	@Override
	public List<Piece> allPieces() {
		return allPieces;
	}

}
