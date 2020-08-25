package test.model.board;

import static org.junit.Assert.*;

import java.util.LinkedList;

import model.board.*;
import org.junit.*;

/**
 * Tests the IntSquareFactory
 * @author Sebastian Paaske Tørholm
 */
public class IntSquareFactoryTest {
	private SquareFactory intFactory,
	                      pairFactory;
	
	@Before
	public void setUp() throws Exception {
		intFactory = new IntSquareFactory();
		pairFactory = new PairSquareFactory();
	}

	/**
	 * We need to ensure that IntSquareFactory.isSquare returns the correct value.
	 * To do this we first run through all the values we expect to be square, as there are only 
	 * Square.MAX_FILE * Square.MAX_RANK of those.
	 * 
	 * As for what else to test, we have the following equivalency classes: 
	 * 		- Integers below the valid range
	 *      - Integers in the valid range
	 *      - Integers above the valid range
	 *      
	 * So we pick a representative for each group, -4, 1 (always in the valid range), MAX_(FILE|RANK) + 3
	 * and check every possible combination of those. (Except for 2 valid ones, as we've checked all possible of those
	 * with the first check.)
	 */
	@Test
	public final void testIsSquare() {
		for (int file = 1; file <= Square.MAX_FILE; file++)
			for (int rank = 1; rank <= Square.MAX_RANK; rank++)
				assertTrue(intFactory.isSquare(file, rank));
		
		assertFalse(intFactory.isSquare(-4, 1));
		assertFalse(intFactory.isSquare(1, -4));
		assertFalse(intFactory.isSquare(-4, -4));
		
		assertFalse(intFactory.isSquare(Square.MAX_FILE + 3, 1));
		assertFalse(intFactory.isSquare(1, Square.MAX_RANK + 3));
		assertFalse(intFactory.isSquare(Square.MAX_FILE + 3, Square.MAX_RANK + 3));

		assertFalse(intFactory.isSquare(-4, Square.MAX_RANK + 3));
		assertFalse(intFactory.isSquare(Square.MAX_FILE + 3, -4));
	}

	/**
	 * We need to make sure that our iterator iterates through the squares correctly.
	 * To do this, we first iterate through the PairSquareFactory and store the results in a linked list,
	 * after which we simply iterate through the IntSquareFactory and shift off an element from the linked list
	 * and see if we get the correct file/rank order. 
	 */
	@Test
	public final void testIterator() {
		LinkedList<Square> correctOrder = new LinkedList<Square>();
		for (Square s : pairFactory)
			correctOrder.offer(s);
		
		for (Square s : intFactory) {
			assertTrue(correctOrder.size() > 0);
			Square correct = correctOrder.poll();
			assertEquals(correct, s);
		}
		
		assertTrue(correctOrder.size() == 0);
	}

}
