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
    public float speed = 150;
    /** X Movement of the Bandit. */
    public int xMovement = 0;
    protected Texture ufoImage;

    /**
     * Constructs a new Bandit.
     *
     * @param gameScreen The GameScreen instance.
     */
    public Bandit(final GameScreen gameScreen) {
        super(gameScreen);
    }

    /**
     * Spawns a new Bandit enemy.
     */
    @Override
    void spawnEnemy() {
        Rectangle eachBandit = new Rectangle();
        eachBandit.x = xMovement;
        eachBandit.y = 10;
        eachBandit.width = 30; // 30 pixels wide
        eachBandit.height = 50; // 50 pixels height
        enemy.add(eachBandit); // add the bandit into the array
        lastSpawnTime = TimeUtils.nanoTime();
    }

    /**
     * Removes Bandit enemies that are off-screen or collide with the player.
     */
    @Override
    void removeEnemy() {
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle banditRun = iterEnemy.next();
            banditRun.x += speed * Gdx.graphics.getDeltaTime();
            if (banditRun.x + 64 < 0) {
                iterEnemy.remove();
            }
            if (banditRun.overlaps(gameScreen.player.cowboy)) {
                gameScreen.healthPoints--;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(), 0.3F);
                iterEnemy.remove();
            }
        }
    }

    /**
     * Renders Bandit enemies on the screen.
     */
    @Override
    void drawEnemy() {
        ufoImage = new Texture(Gdx.files.internal("ufo_sprite.png"));
        for (Rectangle aBandit: enemy) {
            gameScreen.game.batch.draw(ufoImage, aBandit.x, aBandit.y);
        }
    }

    /**
     * Disposes of resources used by the Bandit.
     */
    @Override
    public void dispose() {
        ufoImage.dispose();
    }
}
