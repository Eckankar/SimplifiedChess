package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * A GUI controller listening for chess board field clicks and Play button clicks
 */
public interface GUIController extends ItemListener, ActionListener {

	/**
	 * The action to perform when a field on the chess board has been clicked
	 * @param e The field click event
	 */
	public abstract void itemStateChanged(ItemEvent e);

	/**
	 * The action to perform when the 'Play' button has been clicked
	 * @param e The 'Play' button click event
	 */
	public abstract void actionPerformed(ActionEvent e);

}