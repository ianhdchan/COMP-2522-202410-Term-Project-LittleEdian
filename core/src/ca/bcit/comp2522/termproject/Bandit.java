package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Represents the Bandit enemy in the game.
 * This class manages the spawning, movement, rendering, and removal of Bandit enemies.
 * Bandits move horizontally across the screen and can damage the player upon collision.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */
public class Bandit extends Enemy {
    /** Speed of the bandit. */
    protected int speed;
    /** X Movement of the Bandit. */
    protected int xMovement = 0;
    /** Image of an enemy. */
    protected Texture ufoImage;

    /**
     * Constructs a new Bandit.
     *
     * @param gameScreen The GameScreen instance.
     * @param texture Texture images.
     */
    public Bandit(final GameScreen gameScreen, final Texture texture) {
        super(gameScreen);
        this.ufoImage = texture;
    }

    /**
     * Spawns a new Bandit enemy.
     */
    @Override
    void spawnEnemy() {
        final int yAxis = 10;
        final int width = 30;
        final int height = 50;
        Rectangle eachBandit = new Rectangle();
        eachBandit.x = xMovement;
        eachBandit.y = yAxis;
        eachBandit.width = width; // 30 pixels wide
        eachBandit.height = height; // 50 pixels height
        enemy.add(eachBandit); // add the bandit into the array
        lastSpawnTime = TimeUtils.nanoTime();
    }

    /**
     * Removes Bandit enemies that are off-screen or collide with the player.
     */
    @Override
    void removeEnemy() {
        final int size = 64;
        final float volume = 0.3F;
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle banditRun = iterEnemy.next();
            banditRun.x += speed * Gdx.graphics.getDeltaTime();
            if (banditRun.x + size < 0) {
                iterEnemy.remove();
            }
            if (banditRun.overlaps(gameScreen.player.cowboy)) {
                gameScreen.healthPoints--;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(), volume);
                iterEnemy.remove();
            }
        }
    }

    /**
     * Set the speed of the bandit.
     * @param speed Speed as an int.
     */
    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    /**
     * Renders Bandit enemies on the screen.
     */
    @Override
    void drawEnemy() {
        for (Rectangle aBandit: enemy) {
            gameScreen.game.batch.draw(ufoImage, aBandit.x, aBandit.y);
        }
    }

    /**
     * Disposes of resources used by the Bandit.
     */
    @Override
    public void dispose() {
        if (ufoImage != null) {
            ufoImage.dispose();
            ufoImage = null;
        }
    }
}
