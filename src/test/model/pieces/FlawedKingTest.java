package test.model.pieces;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import model.pieces.*;
import model.board.*;

/**
 * Determines the flaw in the FlawedKing.
 * @author Sebastian Paaske Tørholm
 *
 */
public class FlawedKingTest {

	private FlawedKing edgePiece,	  // on the edge of the board, white
	                   edgePiece2,    // on the other edge of the board, white
	                   dummyPiece,    // next to edgePiece2, white
	                   nonEdgePiece;  // away from the edge, black
	private UpdatablePosition gameBoard;
	private IntSquareFactory factory;
	
	@Before
	public void setUp() {
		factory = new IntSquareFactory();
		edgePiece = new FlawedKing(PieceColor.WHITE, 
				                   factory.create(1, 1));
		edgePiece2 = new FlawedKing(PieceColor.WHITE, 
								    factory.create(Square.MAX_FILE, Square.MAX_RANK));
		dummyPiece = new FlawedKing(PieceColor.WHITE, 
				                    factory.create(Square.MAX_FILE-1, Square.MAX_RANK));
		nonEdgePiece = new FlawedKing(PieceColor.BLACK,
				                      factory.create(2, 2));
		
		gameBoard = new ArrayPosition(new Piece[]{edgePiece, edgePiece2, dummyPiece, nonEdgePiece});
	}

	/**
	 * We need to ensure that color, location and type are set properly.
	 */
	@Test
	public final void testInitialState() {
		// Check colors
		assertEquals(PieceColor.WHITE, edgePiece.color());
		assertEquals(PieceColor.WHITE, edgePiece2.color());
		assertEquals(PieceColor.WHITE, dummyPiece.color());
		assertEquals(PieceColor.BLACK, nonEdgePiece.color());
		// Check position
		assertEquals(factory.create(1,1), edgePiece.location());
		assertEquals(factory.create(Square.MAX_FILE,Square.MAX_RANK), edgePiece2.location());
		assertEquals(factory.create(Square.MAX_FILE-1,Square.MAX_RANK), dummyPiece.location());
		assertEquals(factory.create(2,2), nonEdgePiece.location());
		// Check type
		assertEquals(PieceType.KING, edgePiece.type());
		assertEquals(PieceType.KING, edgePiece2.type());
		assertEquals(PieceType.KING, dummyPiece.type());
		assertEquals(PieceType.KING, nonEdgePiece.type());
	}

	private static List<Square> squaresFromStrings(String[] strs) {
		LinkedList<Square> output = new LinkedList<Square>();
		for (String str : strs) {
			output.add(new IntSquareFactory().create(str));
		}
		return output;
	}

	/**
	 * We have some important things to test to see if validSquares() works:
	 * 	- That it doesn't returns squares past the edges
	 *  - That it ignores squares pieces of the same color are on
	 *  - That it doesn't ignore squares with pieces of a different color on them
	 *  
	 *  For this purpose we have 3 different pieces we will check.
	 *  edgePiece, which is next to an edge and a piece of a different color
	 *  edgePiece2, which is next to an edge and a piece of its own color
	 *  nonEdgePiece, which isn't next to any edge, but next to a piece of a different color
	 */
	@Test
	public final void testValidSquares() {
		// edgePiece:
		
		/*  +----
		 *  |Kx                          3 possible locations to move to
		 *  |xO <-- nonEdgeSquare, different color
		 *  | 
		 */
		Collection<Square> edgeSquares = edgePiece.validSquares(gameBoard);
		assertEquals(3,edgeSquares.size());
		edgeSquares.removeAll(squaresFromStrings(new String[]{"a2", "b2", "b1"}));
		assertEquals(0,edgeSquares.size());
		
		// edgePiece2:
		
		/*      |                        2 possible locations to move to
		 *    xO| <-- dummyPiece
		 *    xK| <-- edgeSquare2
		 *  ----+
		 */
		Collection<Square> edgeSquares2 = edgePiece2.validSquares(gameBoard);
		assertEquals(2,edgeSquares2.size());
		edgeSquares2.remove(factory.create(Square.MAX_FILE-1, Square.MAX_RANK-1));
		edgeSquares2.remove(factory.create(Square.MAX_FILE, Square.MAX_RANK-1));
		assertEquals(0,edgeSquares2.size());
		
		// nonEdgePiece:
		
		/*  +----
		 *  |Oxx                         8 possible locations to move to
		 *  |xKx
		 *  |xxx
		 */
		Collection<Square> nonEdgeSquares = nonEdgePiece.validSquares(gameBoard);
		assertEquals(8,nonEdgeSquares.size());
		nonEdgeSquares.removeAll(
			squaresFromStrings(new String[]{"a1", "a2", "a3", "b1", "b3", "c1", "c2", "c3"})
		);
		assertEquals(0,nonEdgeSquares.size());
	}

	/**
	 * We use the same test cases as we did in the validSquares test.
	 * We know from the test above that validSquares works, so we use that in this.
	 */
	@Test
	public final void testCanMoveTo() {
		IntSquareFactory fact = new IntSquareFactory();
		// edgePiece
		List<Square> edgeCanMoveTo = new LinkedList<Square>();
		for (Square s : fact)
			if (edgePiece.canMoveTo(gameBoard, s))
				edgeCanMoveTo.add(s);
		
		assertEquals(3,edgeCanMoveTo.size());
		edgeCanMoveTo.removeAll(squaresFromStrings(new String[]{"a2", "b2", "b1"}));
		assertEquals(0,edgeCanMoveTo.size());
		
		// edgePiece2
		List<Square> edge2CanMoveTo = new LinkedList<Square>();
		for (Square s : fact)
			if (edgePiece2.canMoveTo(gameBoard, s))
				edge2CanMoveTo.add(s);
		
		assertEquals(2,edge2CanMoveTo.size());
		edge2CanMoveTo.remove(factory.create(Square.MAX_FILE-1, Square.MAX_RANK-1));
		edge2CanMoveTo.remove(factory.create(Square.MAX_FILE, Square.MAX_RANK-1));
		assertEquals(0,edge2CanMoveTo.size());
		
		// nonEdgePiece
		List<Square> nonEdgeCanMoveTo = new LinkedList<Square>();
		for (Square s : fact)
			if (nonEdgePiece.canMoveTo(gameBoard, s))
				nonEdgeCanMoveTo.add(s);
		
		assertEquals(8,nonEdgeCanMoveTo.size());
		nonEdgeCanMoveTo.removeAll(
			squaresFromStrings(new String[]{"a1", "a2", "a3", "b1", "b3", "c1", "c2", "c3"})
		);
		assertEquals(0,nonEdgeCanMoveTo.size());
	}

	/**
	 * We need to make sure that moveTo:
	 *	- Updates location()
	 *  - Updates the board
	 */
	@Test
	public final void testMoveTo() {
		IntSquareFactory fact = new IntSquareFactory();
		for (Square s : fact) {
			// edgePiece
			if (edgePiece.canMoveTo(gameBoard, s)) {
				edgePiece.moveTo(gameBoard, s);
				assertEquals(edgePiece, gameBoard.get(s));
				assertEquals(s, edgePiece.location());
				setUp();
			}
			// edgePiece2
			if (edgePiece2.canMoveTo(gameBoard, s)) {
				edgePiece2.moveTo(gameBoard, s);
				assertEquals(edgePiece2, gameBoard.get(s));
				assertEquals(s, edgePiece2.location());
				setUp();
			}
			// nonEdgePiece
			if (nonEdgePiece.canMoveTo(gameBoard, s)) {
				nonEdgePiece.moveTo(gameBoard, s);
				assertEquals(nonEdgePiece, gameBoard.get(s));
				assertEquals(s, nonEdgePiece.location());
				setUp();
			}
		}
	}
}
