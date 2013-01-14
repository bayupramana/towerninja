package net.codegames.towerninja;

import java.util.Vector;

import processing.core.PApplet;

// kennt alle steine
// kennt Turm

/**
 * @author chameleon
 * 
 *         This class contains a tower data structure. The method createStone
 *         allows creating new stones for free spots within the tower.
 */
public class Game {

	private final long NEW_STONE_DELAY = 1000L;
	private PApplet mApplet;
	private long mLastTimeStamp = System.currentTimeMillis();

	/**
	 * A tower represented by a 2-dimensional array. the first dimension
	 * describes the towers width. The second its height. When a new stone is
	 * added to a certain location, it must not actually be located at that
	 * position already. It will fly towards that position though.
	 */
	private Stone[][] tower = new Stone[4][10];

	/**
	 * Game constructor
	 * 
	 * @param applet
	 *            <code>Main</code> instance
	 */
	public Game(PApplet applet) {
		this.mApplet = applet;
	}

	public void update(float dT, Vector<Player> players) {

		if (System.currentTimeMillis() - mLastTimeStamp > NEW_STONE_DELAY) {
			mLastTimeStamp = System.currentTimeMillis();
			System.out.println("creating a stone");
			createStone();
		}
//		mApplet.rect(10, 10, 50, 70);
		moveStones(dT);

		
		// steine erzeugen
		// steine bewegen

		mApplet.ellipseMode(mApplet.CENTER);
		// display left hand
		for (int i = 0; i < players.size(); i++) {
			float x = players.get(i).getLeftX();
			float y = players.get(i).getLeftY();
			mApplet.fill(255, 0, 0);
			mApplet.ellipse(x, y, 20, 20);
			mApplet.fill(0);
			mApplet.text(players.get(i).getUserId(), x, y);
		}
		// display right hand
		for (int i = 0; i < players.size(); i++) {
			float x = players.get(i).getRightX();
			float y = players.get(i).getRightY();
			mApplet.fill(0, 255, 0);
			mApplet.ellipse(x, y, 20, 20);
			mApplet.fill(0);
			mApplet.text(players.get(i).getUserId(), x, y);
		}
	}

	/**
	 * Moves all stones for the passed number of frames
	 * 
	 * @param dT
	 */
	private void moveStones(float dT) {
		towerHeightLoop: for (int i = 0; i < tower.length; i++) {
			for (int j = 0; j < tower[0].length; j++) {
				if (tower[i][j] == null) {
					break towerHeightLoop;
				} else {
					tower[i][j].moveToDestination(dT);
					mApplet.rect(tower[i][j].getxLocation(),
							tower[i][j].getyLocation(), tower[i][j].getWidth(),
							tower[i][j].getHeight());
				}
			}
		}
	}

	/**
	 * This method loops through the tower data structure and finds the lowest
	 * leftmost free spot. It then creates a stone for that spot and returns its
	 * reference
	 * 
	 * @return a reference to the next {@link Stone}
	 */
	private Stone createStone() {
		towerHeightLoop: for (int i = 0; i < tower.length; i++) {
			for (int j = 0; j < tower[0].length; j++) {
				if (tower[i][j] == null) {
					tower[i][j] = new Stone(50, 5, i, j);
					return tower[i][j];
					// break towerHeightLoop;
				}
			}
		}
		return null;
	}

	/**
	 * draws every stone
	 */
	void drawStones() {

	}
}
