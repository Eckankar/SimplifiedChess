package model.pieces.types;

import java.util.Arrays;

/**
 * Defines the MovementPattern for a standard chess queen.
 * @author Sebastian Paaske Tørholm
 *
 */
public class QueenPattern extends MultiPattern {
	public QueenPattern() {
		super(Arrays.asList(new MovementPattern[]{
			new RookPattern(),
			new BishopPattern()
		}));
	}

}
