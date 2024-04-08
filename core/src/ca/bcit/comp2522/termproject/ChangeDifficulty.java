package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;

/**
 * Manages the dynamic adjustment of game difficulty over time.
 * This class adjusts parameters such as enemy spawn rate and behavior based on elapsed game time.
 * As the game progresses, the difficulty gradually increases to provide a more challenging experience.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */
public class ChangeDifficulty {

    /** The GameScreen instance associated with this ChangeDifficulty object. */
    protected final GameScreen gameScreen;

    /** Elapsed time since the game started. */
    protected float timeSeconds = 0f;

    /**
     * Constructs a new ChangeDifficulty object.
     *
     * @param gameScreen The GameScreen instance.
     */
    public ChangeDifficulty(final GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Updates game difficulty based on elapsed time.
     * Adjusts parameters such as enemy spawn rate and behavior as the game progresses.
     */
    protected void timer() {
        float period = 1f;
        timeSeconds += Gdx.graphics.getDeltaTime();

        if (timeSeconds > period) {
            if (timeSeconds > 70) {
                gameScreen.oneSecond = 10000000;
            } else if (timeSeconds > 60) {
                gameScreen.oneSecond = 100000000;
            } else if (timeSeconds > 45) {
                gameScreen.oneSecond = 500000000;
            } else if (timeSeconds > 30) {
                gameScreen.bandit2.xMovement = 800;
                gameScreen.bandit2.speed = -150;
            } else if (timeSeconds > 10) {
                gameScreen.oneSecond = 800000000;
            }
        }
    }
}
