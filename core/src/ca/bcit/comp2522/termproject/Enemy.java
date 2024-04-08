package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;

/**
 * Represents an abstract enemy class for the game.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */
public abstract class Enemy {
    /** The GameScreen instance associated with this enemy. */
    protected final GameScreen gameScreen;

    /** Array of enemy rectangles. */
    protected Array<Rectangle> enemy;

    /** Iterator for iterating over enemy rectangles. */
    protected Iterator<Rectangle> iterEnemy;

    /** Time when the last enemy was spawned. */
    protected long lastSpawnTime;

    /**
     * Constructs a new Enemy object associated with the specified GameScreen.
     *
     * @param gameScreen The GameScreen instance.
     */
    public Enemy(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.enemy = new Array<>();
    }

    /**
     * Abstract method to spawn an enemy.
     */
    abstract void spawnEnemy();

    /**
     * Abstract method to remove an enemy.
     */
    abstract void removeEnemy();

    /**
     * Abstract method to draw an enemy.
     */
    abstract void drawEnemy();

    /**
     * Abstract method to dispose of resources used by the enemy.
     */
    public abstract void dispose();
}
