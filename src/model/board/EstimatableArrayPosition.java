package model.board;

import model.pieces.Piece;
import model.players.minimax.estimators.BoardEstimator;

/**
 * ArrayPosition with built-in estimate.
 * @author Sebastian Paaske Tørholm
 */
public class EstimatableArrayPosition extends ArrayPosition {
	private int estimate;
	private BoardEstimator estimator;
	
	public EstimatableArrayPosition(Position pos, BoardEstimator estimator) {
		this(pos, estimator, estimator.boardEstimate(pos));
	}
	
	public EstimatableArrayPosition(Position pos, BoardEstimator estimator, int estimate) {
		super(pos);
		this.estimator = estimator;
		this.estimate = estimate;
	}
	
	/**
	 * Creates a new EstimatableArrayPosition from the given position, if conversion is needed.
	 * @param pos position to convert
	 * @param estimator estimator to use
	 * @return an EstimatableArrayPosition
	 */
	public static EstimatableArrayPosition from(Position pos, BoardEstimator estimator) {
		if (pos instanceof EstimatableArrayPosition)
			return (EstimatableArrayPosition)pos;
		return new EstimatableArrayPosition(pos, estimator);
	}
	
	/**
	 * @return a board estimate
	 */
	public int estimate() {
		return estimate;
	}
	
	@Override
	public void set(Square square, Piece piece) {
		estimate += estimator.estimateChange(this, square, piece);
		super.set(square, piece);
	}

	@Override
	public void set(int file, int rank, Piece piece) {
		estimate += estimator.estimateChange(this, file, rank, piece);
		super.set(file, rank, piece);
	}
	
	@Override
	public Position deepClone() {
		return new EstimatableArrayPosition(super.deepClone(), estimator, estimate);	
	}
}
