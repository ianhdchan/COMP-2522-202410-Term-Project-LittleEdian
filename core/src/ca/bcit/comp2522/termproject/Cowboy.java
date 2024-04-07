package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Cowboy {
    protected final GameScreen gameScreen;

    /** Tracks current frame of sprites **/
    private int currentFrame = 0;
    protected Rectangle cowboy;
    /** Checks if sprite is facing left **/
    private boolean isFacingLeft = false;


    /** Determines isJumping state of character. */
    private boolean isJumping = false;

    /** Determines isJumping state of character. */
    private boolean isFalling = false;

    /** How fast the jump action will be. */
    private final float jumpVelocity = 250;
    /** Max Jump Height. */
    private final float MAX_JUMP_HEIGHT = 200;

    public Cowboy(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }



    void createCowboy(){
        cowboy = new Rectangle();
        cowboy.x = 800 / 2 - 64 / 2; // This centers the cowboy horizontally (x-axis)
        cowboy.y = 20; // bottom

        cowboy.width = 64; // 64 pixels
        cowboy.height = 64; // 64 pixels
    }

    public void cowboyMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updateCowboy(gameScreen.runningLeftSprite);
            cowboy.x -= 200 * Gdx.graphics.getDeltaTime();
            // Right key pressed
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updateCowboy(gameScreen.runningRightSprite);
            cowboy.x += 200 * Gdx.graphics.getDeltaTime();
            // Checks if facing left direction
        } else {
            Texture stillSprite = isFacingLeft ? gameScreen.cowboyStillL : gameScreen.cowboyStillR;
            gameScreen.game.batch.draw(stillSprite, cowboy.x, cowboy.y);
        }


        // todo: ugly smelly code please fix
        //  y
        // JUMP LOGIC
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isJumping && !isFalling) {
            isJumping = true;
        }

        if (isJumping) {
            cowboy.y += jumpVelocity * Gdx.graphics.getDeltaTime();
            if (cowboy.y >= MAX_JUMP_HEIGHT - 1) {
                isJumping = false;
                isFalling = true;
            }
        } else if (isFalling) {
            cowboy.y -= jumpVelocity * Gdx.graphics.getDeltaTime();
            if (cowboy.y <= 0) {
                isJumping = false;
                isFalling = false;
            }
        }
    }

    private void updateCowboy(Sprite[] runningSprite) {
        currentFrame = (currentFrame + 1) % runningSprite.length;
        runningSprite[currentFrame].setPosition(cowboy.x, cowboy.y);
        runningSprite[currentFrame].draw(gameScreen.game.batch);
        isFacingLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT); // update facing direction
    }

    protected void isWithinBounds() {
        if (cowboy.y < 0) {
            cowboy.y  = 0;
        }

        // Ensure cowboy stays within screen bounds
        if (cowboy.x < 0) {
            cowboy.x = 0;
        }
        if (cowboy.x > 800 - 64) {
            cowboy.x = 800 - 64;
        }

    }
}
