package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.board.*;
import model.board.generators.*;
import model.pieces.*;

/**
 * An implementation of the Game interface for Simple Chess
 * @author Sebastian Paaske Tørholm
 */
public class SimpleChessGame implements Game {
	private UpdatablePosition position;
	private LinkedList<DetailedMove> moveHistory;
	private PieceColor toMove, winner;
	private SquareFactory sqFactory;
	private boolean gameOver;
	
	/**
	 * Creates a new SimpleChessGame.
	 * @param startPositionFactory starting setup to use
	 */
	public SimpleChessGame(PositionFactory startPositionFactory) {
		this.position = new ArrayPosition(startPositionFactory.position());
		this.moveHistory = new LinkedList<DetailedMove>();
		this.toMove = PieceColor.WHITE;
		this.winner = null;
		this.sqFactory = new IntSquareFactory();
	}
	
	/**
	 * Creates a new SimpleChessGame with the default chess setup.
	 */
	public SimpleChessGame() { this(new StandardChessPositionFactory()); }
	
	@Override
	public List<Move> allValidMoves() {
		List<Move> validMoves = new LinkedList<Move>();
		for (Square sq : sqFactory) {
				Piece p = position.get(sq);
				if (p == null || p.color() != toMove)
					continue;
				
				Collection<Square> pieceTargets = p.validSquares(position);
				for (Square target : pieceTargets)
					validMoves.add(new Move(sq, target));
		}
		
		return validMoves;
	}

	@Override
	public void execute(Move move) {
		assert !gameOver() && isLegal(move);
		Piece p = position.get(move.from);
		Piece targetPiece = position.get(move.to);
		
		int moveFlags = 0;
		if (targetPiece != null)
			moveFlags |= DetailedMove.FLAG_CAPTURE;
		
		DetailedMove moveDetail = 
			new DetailedMove(move.from, move.to, p, targetPiece, position, moveFlags);
		
		p.moveTo(position, move.to);
		moveHistory.add(moveDetail);
		
		// Pawn promotion
		if ((p.type() == PieceType.PAWN)
		&&  (p.location().rank() == (p.color() == PieceColor.BLACK ? 1 : Square.MAX_RANK) )) {
			moveDetail.setPromotion(PieceType.QUEEN);
			p.type(PieceType.QUEEN);	
		}
		
		// Check if opponent is in check
		List<Square> oppKings = new LinkedList<Square>();
		Set<Square> coveredSquares = new HashSet<Square>();
		for (Square sq : sqFactory) {
			Piece piece = position.get(sq);
			if (piece == null)
				continue;
			
			if (piece.color() == toMove) {
				coveredSquares.addAll(piece.validSquares(position));
			} else if (piece.type() == PieceType.KING)
				oppKings.add(piece.location()); 
		}
		
		if (coveredSquares.removeAll(oppKings))
			moveDetail.addFlags(DetailedMove.FLAG_CHECK);
		
		
		// TODO: Check for checkmate
		
		// Game over if king dies
		if (targetPiece != null &&  targetPiece.type() == PieceType.KING) {
			winner = p.color();
			gameOver = true;
		}
		
		// Game over after 100 rounds ( = 100 * 2 turns )
		if (moveHistory.size() >= 200)
			gameOver = true;
		
		toMove = toMove.other();
	}

	@Override
	public boolean gameOver() {
		return gameOver;
	}

	@Override
	public boolean isLegal(Move move) {
		Piece movingPiece = position.get(move.from);
		return movingPiece != null
		    && movingPiece.color() == toMove
		    && movingPiece.validSquares(position).contains(move.to);
	}

	@Override
	public DetailedMove lastMove() {
		return moveHistory.peekLast();
	}

	@Override
	public int numMoves() {
		return moveHistory.size();
	}

	@Override
	public Position position() {
		return position;
	}

	@Override
	public PieceColor toMove() {
		return toMove;
	}

	@Override
	public PieceColor winner() {
		assert gameOver();
		return winner;
	}

}
