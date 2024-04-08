package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

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
    protected Texture laserImage;

    /**
     * Constructs a new Laser object.
     *
     * @param gameScreen The GameScreen instance.
     */
    public Laser(final GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    void spawnEnemy() {
        Rectangle laserBeam = new Rectangle();
        laserBeam.x = MathUtils.random(0, 800 - 15);
        laserBeam.y = 600;
        laserBeam.width = 1;
        laserBeam.height = 40;
        enemy.add(laserBeam);
        lastSpawnTime = TimeUtils.nanoTime();
    }

    @Override
    void removeEnemy() {
        iterEnemy = enemy.iterator();
        while (iterEnemy.hasNext()) {
            Rectangle laserDrop = iterEnemy.next();
            laserDrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if (laserDrop.y + 64 < 0) {
                iterEnemy.remove();
            }
            if (laserDrop.overlaps(gameScreen.player.cowboy)) {
                gameScreen.healthPoints--;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(), 0.3F);
                iterEnemy.remove();
            }
        }
    }

    @Override
    void drawEnemy() {
        laserImage = new Texture(Gdx.files.internal("laser.png"));
        for (Rectangle laserDrop : enemy) {
            gameScreen.game.batch.draw(laserImage, laserDrop.x, laserDrop.y);
        }
    }

    @Override
    public void dispose() {
        laserImage.dispose();
    }
}
