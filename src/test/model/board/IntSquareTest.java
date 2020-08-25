package test.model.board;

import static org.junit.Assert.*;
import org.junit.*;
import model.board.*;

import java.util.HashSet;

/**
 * Tests IntSquare.
 * @author Sebastian Paaske Tørholm
 */
public class IntSquareTest {
	private IntSquareFactory intFactory;
	private PairSquareFactory pairFactory;
	
	@Before
	public void setUp() throws Exception {
		intFactory = new IntSquareFactory();
		pairFactory = new PairSquareFactory();
	}

	/**
	 * We need to make sure that all hash codes are distinct.
	 * This will be accomplished by adding them all to a HashSet.
	 * add returns false at any point, we have a collision.
	 */
	@Test
	public final void testHashCode() {
		HashSet<Integer> hashes = new HashSet<Integer>(Square.MAX_FILE * Square.MAX_RANK);
		for (Square sq : intFactory)
			assertTrue(hashes.add(sq.hashCode()));
	}

	/**
	 * We need to check that for every IntSquare, IntSquare.fileRankToPosition returns
	 * the correct position.
	 */
	@Test
	public final void testFileRankToPosition() {
		for (Square sq : intFactory)
			assertEquals(((IntSquare)sq).position(),
					     IntSquare.fileRankToPosition(sq.file(), sq.rank()));
	}

	/**
	 * We need to test that the char-integer constructor works properly, and we do that by
	 * creating every possible legal combination of chars and ints and seeing if we get
	 * the right IntSquare.
	 */
	@Test
	public final void testIntSquareCharInt() {
		for (int file = 1; file <= Square.MAX_FILE; file++)
			for (int rank = 1; rank <= Square.MAX_RANK; rank++) {
				Square square = intFactory.create((char)('a'+file-1), rank);
				Square expected = intFactory.create(file, rank);
				assertEquals(expected, square);
			}
	}

	/**
	 * We need to test that the char-integer constructor works properly, and we do that by
	 * creating every possible legal combination of integers and checking if they get the
	 * right file and rank.
	 */
	@Test
	public final void testIntSquareIntInt() {
		for (int file = 1; file <= Square.MAX_FILE; file++)
			for (int rank = 1; rank <= Square.MAX_RANK; rank++) {
				Square square = intFactory.create(file, rank);
				assertEquals(file, square.file());
				assertEquals(rank, square.rank());
			}
	}

	/**
	 * We need to ensure that the string constructor works properly, and we do that by creating
	 * every possible legal string representation of a square and comparing it to the expected square.
	 */
	@Test
	public final void testIntSquareString() {
		for (int file = 1; file <= Square.MAX_FILE; file++)
			for (int rank = 1; rank <= Square.MAX_RANK; rank++) {
				Square squareLower = intFactory.create(String.format("%c%d", 'a'+file-1, rank));
				Square squareUpper = intFactory.create(String.format("%c%d", 'A'+file-1, rank));
				Square expected = intFactory.create(file, rank);
				assertEquals(expected, squareLower);
				assertEquals(expected, squareUpper);
			}
	}

	/**
	 * We need to ensure that the equals method works.
	 * We have the following equivalence groups:
	 *    - IntSquares with the same value
	 *    - Non-IntSquare Squares with the same value
	 *    
	 *    - IntSquares with a different value
	 *    - Non-IntSquare Squares with a different value
	 *    
	 *    - Other objects
	 *    
	 * For this test we will run through all IntSquares, and compare each to all PairSquares, all IntSquares and the String "Fritz". 
	 * We should only get two matches for each iteration, the matching  IntSquare and the equivalent PairSquare.
	 */
	@Test
	public final void testEqualsObject() {
		for (Square square : intFactory) {
			for (Square pairSquare : pairFactory) {
				if (pairSquare.rank() == square.rank() && pairSquare.file() == square.file())
					assertTrue(square.equals(pairSquare));
				else
					assertFalse(square.equals(pairSquare));
			}
			for (Square intSquare : intFactory) {
				if (intSquare.rank() == square.rank() && intSquare.file() == square.file())
					assertTrue(square.equals(intSquare));
				else
					assertFalse(square.equals(intSquare));
			}
			assertFalse(square.equals("Fritz"));
		}
	}
}
