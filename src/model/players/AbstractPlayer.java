package model.players;

/**
 * An abstract implementation of the Player interface.
 * @author Sebastian Paaske Tørholm
 */
public abstract class AbstractPlayer implements Player {
	protected String name;
	
	protected AbstractPlayer(String name) {
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}

}
