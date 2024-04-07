package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Laser extends Enemy {
    public Laser(final GameScreen gameScreen) {
        super(gameScreen);
    }

    @Override
    void spawnEnemy() {
        Rectangle laserBeam = new Rectangle();
        laserBeam.x = MathUtils.random(0, 800 - 64);
        laserBeam.y = 600;
        laserBeam.width = 10; // 10 pixels wide
        laserBeam.height = 64; // 64 pixels height
        enemy.add(laserBeam); // add the laserbeam into the array
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
                gameScreen.dropsGathered++;
                gameScreen.damageNoise.setVolume(gameScreen.damageNoise.play(),
                0.3F);
                iterEnemy.remove();
            }
        }

    }

    @Override
    void drawEnemy() {
        for (Rectangle laserDrop : enemy) {
            gameScreen.game.batch.draw(gameScreen.laserImage, laserDrop.x, laserDrop.y);
        }
    }

}