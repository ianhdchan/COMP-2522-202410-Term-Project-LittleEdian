package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import static ca.bcit.comp2522.termproject.GameScreen.SCREEN_X;
import static ca.bcit.comp2522.termproject.GameScreen.SCREEN_Y;

/**
 * Represents the Laser class for the game.
 * Laser objects serve as enemies in the game, which the player must avoid.
 * Lasers spawn randomly at the top of the screen and move downwards.
 * If the player collides with a laser, their health points decrease.
 * Handles spawning, removing, and drawing laser enemies.
 *
 * @author Edro Gonzales
 * @author Ian Chan
 * @version 2024
 */
public class Laser extends Enemy {
    /**
     * Implement the laser image.
     */
    protected Texture laserImage;

    /**
     * Constructs a new Laser object.
     *
     * @param gameScreen The GameScreen instance.
     * @param texture The Texture images.
     */
    public Laser(final GameScreen gameScreen, final Texture texture) {
        super(gameScreen);
        this.laserImage = texture;
    }

    @Override
    void spawnEnemy() {
        final int width = 1;
        final int height = 40;
        final int size = 15;
        Rectangle laserBeam = new Rectangle();
        laserBeam.x = MathUtils.random(0, SCREEN_X - size);
        laserBeam.y = SCREEN_Y;
        laserBeam.width = width;
        laserBeam.height = height;
        enemy.add(laserBeam);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    @Override
    void removeEnemy() {
        final int size = 64;
        final int dropRate = 200;
        final float volume = 0.3F;
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle laserDrop = iterEnemy.next();
            laserDrop.y -= dropRate * Gdx.graphics.getDeltaTime();
            if (laserDrop.y + size < 0) {
                iterEnemy.remove();
            }
            if (laserDrop.overlaps(gameScreen.player.cowboy)) {
                gameScreen.healthPoints--;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(), volume);
                iterEnemy.remove();
            }
        }
    }

    @Override
    void drawEnemy() {
        for (Rectangle laserDrop : enemy) {
            gameScreen.game.batch.draw(laserImage, laserDrop.x, laserDrop.y);
        }
    }

    @Override
    public void dispose() {
        if (laserImage != null) {
            laserImage.dispose();
            laserImage = null;
        }
    }
}
