package test.model.pieces;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;
import model.pieces.*;
import model.board.*;

/**
 * Ensures that the GenericPiece works correctly.
 * @author Sebastian Paaske T�rholm
 */
public class GenericPieceTest {

	private GenericPiece edgePiece,	  // on the edge of the board, white
	                     edgePiece2,    // on the other edge of the board, white
	                     dummyPiece,    // next to edgePiece2, white
	                     nonEdgePiece;  // away from the edge, black
	private UpdatablePosition gameBoard;
	private IntSquareFactory factory;
	
	@Before
	public void setUp() {
		factory = new IntSquareFactory();
		
		edgePiece = new GenericPiece(PieceColor.WHITE, 
				                     factory.create(1, 1),
				                     PieceType.KING);
		edgePiece2 = new GenericPiece(PieceColor.WHITE, 
								      factory.create(Square.MAX_FILE, Square.MAX_RANK),
								      PieceType.KING);
		dummyPiece = new GenericPiece(PieceColor.WHITE, 
				                      factory.create(Square.MAX_FILE-1, Square.MAX_RANK),
				                      PieceType.KING);
		nonEdgePiece = new GenericPiece(PieceColor.BLACK,
				                        factory.create(2, 2),
				                        PieceType.KING);
		
		gameBoard = new ArrayPosition(new Piece[]{edgePiece, edgePiece2, dummyPiece, nonEdgePiece});
		
		setUpPawns();
	}

	/**
	 * We need to ensure that color, location and type are set properly.
	 */
	@Test
	public void testInitialState() {
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
	public void testValidSquares() {
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
	public void testCanMoveTo() {
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
	public void testMoveTo() {
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
	
	//////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	////////////// PAWN SPECIFIC CODE ////////////////////////////
	//////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////
	
	private GenericPiece blackPawnStartingRank, // pawn on the starting file, to check that it lets us move 2 spaces
	                     blackPawnMiddleBoard,  // pawn in the middle, to check general movement
	                     blackPawnThreatenOther,// pawn threatening other pawn, to check that it adds movement possibilities
	                     blackPawnDummy,			// dummy piece that is in front of a pawn 
	                     whitePawnStartingRank,
	                     whitePawnMiddleBoard,
	                     whitePawnThreatenOther,
	                     whitePawnDummy;
	
	private UpdatablePosition pawnPosition;
	
	/**
	 * Setting up pawns for our tests.
	 * We will test whether it allows correct movement of pawns:
	 * 	- That it allows you to move 2 spaces in the beginning
	 *  - that it only allows you to move 1 space forward when not in the beginning
	 *  - that it allows you to move to capture other pieces
	 *  - that it doesn't allow you to move to capture other pieces in front of you
	 */
	private void setUpPawns() {		
		blackPawnStartingRank = new GenericPiece(PieceColor.BLACK,
				                                 factory.create(3, Square.MAX_RANK-1),
				                                 PieceType.PAWN);
		blackPawnMiddleBoard = new GenericPiece(PieceColor.BLACK,
                								factory.create("G5"),
                								PieceType.PAWN);
		blackPawnThreatenOther = new GenericPiece(PieceColor.BLACK,
												  factory.create("E5"),
												  PieceType.PAWN);
		blackPawnDummy = new GenericPiece(PieceColor.BLACK,
				     					  factory.create("D5"),
				     					  PieceType.PAWN);
		whitePawnStartingRank = new GenericPiece(PieceColor.WHITE,
                                                 factory.create("C2"),
                                                 PieceType.PAWN);
		whitePawnMiddleBoard = new GenericPiece(PieceColor.WHITE,
						                        factory.create("B4"),
						                        PieceType.PAWN);
		whitePawnThreatenOther = new GenericPiece(PieceColor.WHITE,
						                          factory.create("D4"),
						                          PieceType.PAWN);
		whitePawnDummy = new GenericPiece(PieceColor.WHITE,
				  			              factory.create("E4"),
				                          PieceType.PAWN);
	
		pawnPosition = new ArrayPosition(
				new Piece[]{ // we only add dummies when needed
					blackPawnStartingRank, blackPawnMiddleBoard,
					blackPawnThreatenOther, 
					whitePawnStartingRank, whitePawnMiddleBoard,
					whitePawnThreatenOther
				}				
		);
	}
	
	/**
	 * Ensure the correct starting state of our pawns.
	 */
	@Test
	public void testInitialPawnState() {
		// Check colors:
		assertEquals(PieceColor.BLACK, blackPawnStartingRank.color());
		assertEquals(PieceColor.BLACK, blackPawnMiddleBoard.color());
		assertEquals(PieceColor.BLACK, blackPawnThreatenOther.color());
		assertEquals(PieceColor.BLACK, blackPawnDummy.color());
		assertEquals(PieceColor.WHITE, whitePawnStartingRank.color());
		assertEquals(PieceColor.WHITE, whitePawnMiddleBoard.color());
		assertEquals(PieceColor.WHITE, whitePawnThreatenOther.color());
		assertEquals(PieceColor.WHITE, whitePawnDummy.color());
		// Check position
		assertEquals(factory.create(3, Square.MAX_RANK-1), blackPawnStartingRank.location());
		assertEquals(factory.create("G5"), blackPawnMiddleBoard.location());
		assertEquals(factory.create("E5"), blackPawnThreatenOther.location());
		assertEquals(factory.create("D5"), blackPawnDummy.location());
		assertEquals(factory.create("C2"), whitePawnStartingRank.location());
		assertEquals(factory.create("B4"), whitePawnMiddleBoard.location());
		assertEquals(factory.create("D4"), whitePawnThreatenOther.location());
		assertEquals(factory.create("E4"), whitePawnDummy.location());
		// Check type
		assertEquals(PieceType.PAWN, blackPawnStartingRank.type());
		assertEquals(PieceType.PAWN, blackPawnMiddleBoard.type());
		assertEquals(PieceType.PAWN, blackPawnThreatenOther.type());
		assertEquals(PieceType.PAWN, blackPawnDummy.type());
		assertEquals(PieceType.PAWN, whitePawnStartingRank.type());
		assertEquals(PieceType.PAWN, whitePawnMiddleBoard.type());
		assertEquals(PieceType.PAWN, whitePawnThreatenOther.type());
		assertEquals(PieceType.PAWN, whitePawnDummy.type());
	}
	
	/**
	 * Ensure that we can move two spaces from the starting rank.
	 */
	@Test
	public void testPawnStartingMovement() {
		// black
		Collection<Square> blackMovement = blackPawnStartingRank.validSquares(pawnPosition);
		assertEquals(2,blackMovement.size());
		blackMovement.removeAll(squaresFromStrings(new String[]{"c6", "c5"}));
		assertEquals(0,blackMovement.size());
		
		// white
		Collection<Square> whiteMovement = whitePawnStartingRank.validSquares(pawnPosition);
		assertEquals(2,whiteMovement.size());
		whiteMovement.removeAll(squaresFromStrings(new String[]{"c3", "c4"}));
		assertEquals(0,whiteMovement.size());
	}
	
	/**
	 * Ensure that we can move only one space forward on a normal board.
	 */
	@Test
	public void testPawnMiddleBoardMovement() {
		// black
		Collection<Square> blackMovement = blackPawnMiddleBoard.validSquares(pawnPosition);
		assertEquals(1,blackMovement.size());
		blackMovement.removeAll(squaresFromStrings(new String[]{"g4"}));
		assertEquals(0,blackMovement.size());
		
		// white
		Collection<Square> whiteMovement = whitePawnMiddleBoard.validSquares(pawnPosition);
		assertEquals(1,whiteMovement.size());
		whiteMovement.removeAll(squaresFromStrings(new String[]{"b5"}));
		assertEquals(0,whiteMovement.size());		
	}
	
	/**
	 * Ensure that we can capture pieces diagonally.
	 */
	@Test
	public void testPawnCanCaptureDiagonally() {
		// black
		Collection<Square> blackMovement = blackPawnThreatenOther.validSquares(pawnPosition);
		assertEquals(2,blackMovement.size());
		blackMovement.removeAll(squaresFromStrings(new String[]{"d4", "e4"}));
		assertEquals(0,blackMovement.size());
		
		// white
		Collection<Square> whiteMovement = whitePawnThreatenOther.validSquares(pawnPosition);
		assertEquals(2,whiteMovement.size());
		whiteMovement.removeAll(squaresFromStrings(new String[]{"d5", "e5"}));
		assertEquals(0,whiteMovement.size());		
	}
	
	/**
	 * Ensure that we can not capture pieces in front of us.
	 */
	@Test
	public void testPawnCannotCaptureStraight() {
		// add blocking dummies
		pawnPosition.set(blackPawnDummy.location(), blackPawnDummy);
		pawnPosition.set(whitePawnDummy.location(), whitePawnDummy);
		
		// black
		Collection<Square> blackMovement = blackPawnThreatenOther.validSquares(pawnPosition);
		assertEquals(1,blackMovement.size());
		blackMovement.removeAll(squaresFromStrings(new String[]{"d4"}));
		assertEquals(0,blackMovement.size());
		
		// white
		Collection<Square> whiteMovement = whitePawnThreatenOther.validSquares(pawnPosition);
		assertEquals(1,whiteMovement.size());
		whiteMovement.removeAll(squaresFromStrings(new String[]{"e5"}));
		assertEquals(0,whiteMovement.size());		
	}
}
