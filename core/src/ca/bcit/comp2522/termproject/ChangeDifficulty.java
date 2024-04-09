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
        final float period = 1f;
        final float seventySeconds = 70f;
        final float sixtySeconds = 60f;
        final float fortyFiveSeconds = 45f;
        final float thirtySeconds = 30f;
        final float tenSeconds = 10f;
        final int oneSecondLow = 10_000_000;
        final int oneSecondMedium = 100_000_000;
        final int oneSecondHigh = 500_000_000;
        final int banditXMovement = 800;
        final int banditSpeed = -150;

        float deltaTime = Gdx.graphics.getDeltaTime();
        timeSeconds += deltaTime;

        if (timeSeconds > period) {
            if (timeSeconds > seventySeconds) {
                gameScreen.oneSecond = oneSecondLow;
            } else if (timeSeconds > sixtySeconds) {
                gameScreen.oneSecond = oneSecondMedium;
            } else if (timeSeconds > fortyFiveSeconds) {
                gameScreen.oneSecond = oneSecondHigh;
            } else if (timeSeconds > thirtySeconds) {
                gameScreen.bandit2.xMovement = banditXMovement;
                gameScreen.bandit2.setSpeed(banditSpeed);
            } else if (timeSeconds > tenSeconds) {
                gameScreen.oneSecond = oneSecondHigh;
            }
        }

    }

    /**
     * Gets time.
     *
     * @return time in seconds as a string
     */
    public String getTime() {
        return String.format("%.2f", timeSeconds);
    }

}
