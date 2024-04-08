package ca.bcit.comp2522.termproject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

/**
 * Represents the cowboy character in the game.
 * Handles cowboy movement, animation, and interaction with the environment.
 * Supports running left, running right, jumping, and falling actions.
 *
 * @author Edro Gonzales A01257468
 * @author Ian Chan A00910012
 * @version 2024
 */
public class Cowboy {
    /** The GameScreen instance associated with this Cowboy object. */
    protected final GameScreen gameScreen;

    /** Tracks current frame of sprites. */
    private int currentFrame = 0;

    /** Rectangle representing the cowboy's position and dimensions. */
    protected Rectangle cowboy;

    /** Flag indicating if the cowboy is facing left. */
    private boolean isFacingLeft = false;

    /** Flag indicating if the cowboy is jumping. */
    private boolean isJumping = false;

    /** Flag indicating if the cowboy is falling. */
    private boolean isFalling = false;

    /** Array of textures for running left animation. */
    protected final Texture[] runningLeft = new Texture[3];

    /** Array of textures for running right animation. */
    protected final Texture[] runningRight = new Texture[3];

    /** Array of sprites for running left animation. */
    protected final Sprite[] runningLeftSprite = new Sprite[3];

    /** Array of sprites for running right animation. */
    protected final Sprite[] runningRightSprite = new Sprite[3];

    /** Texture for the cowboy when still facing left. */
    protected Texture cowboyStillL;

    /** Texture for the cowboy when still facing right. */
    protected Texture cowboyStillR;

    /**
     * Constructs a new Cowboy object associated with the specified GameScreen.
     *
     * @param gameScreen The GameScreen instance.
     */
    public Cowboy(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /**
     * Loads texture resources for running animations and still poses.
     */
    void cowboyStillTexture() {
        // Load cowboy sprite images for running left
        runningLeft[0] = new Texture(Gdx.files.internal("Cowboy_L1.png"));
        runningLeft[1] = new Texture(Gdx.files.internal("Cowboy_L2.png"));
        runningLeft[2] = new Texture(Gdx.files.internal("Cowboy_L3.png"));
        for (int i = 0; i < 3; i++) {
            runningLeftSprite[i] = new Sprite(runningLeft[i]);
        }

        // Load cowboy sprite images for running right
        runningRight[0] = new Texture(Gdx.files.internal("Cowboy_R1.png"));
        runningRight[1] = new Texture(Gdx.files.internal("Cowboy_R2.png"));
        runningRight[2] = new Texture(Gdx.files.internal("Cowboy_R3.png"));
        for (int i = 0; i < 3; i++) {
            runningRightSprite[i] = new Sprite(runningRight[i]);
        }

        // Load textures for still poses
        cowboyStillL = new Texture(Gdx.files.internal("Cowboy_StillL.png"));
        cowboyStillR = new Texture(Gdx.files.internal("Cowboy_StillR.png"));
    }

    /**
     * Creates the cowboy's rectangle with initial position and dimensions.
     */
    void createCowboy() {
        cowboy = new Rectangle();
        cowboy.x = (float) (800 / 2 - 64 / 2); // Center the cowboy horizontally
        cowboy.y = 20; // Position the cowboy at the bottom of the screen

        cowboy.width = 64; // Set width to 64 pixels
        cowboy.height = 64; // Set height to 64 pixels
    }

    /**
     * Handles cowboy movement based on user input.
     * Supports left and right movement, jumping, and falling.
     */
    public void cowboyMovement() {
        float jumpVelocity = 250;
        float MAX_JUMP_HEIGHT = 200;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updateCowboy(runningLeftSprite);
            cowboy.x -= 200 * Gdx.graphics.getDeltaTime(); // Move left
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updateCowboy(runningRightSprite);
            cowboy.x += 200 * Gdx.graphics.getDeltaTime(); // Move right
        } else {
            // If neither left nor right key is pressed, show still pose
            Texture stillSprite = isFacingLeft ? cowboyStillL : cowboyStillR;
            gameScreen.game.batch.draw(stillSprite, cowboy.x, cowboy.y);
        }

        // Jump logic
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isJumping && !isFalling) {
            isJumping = true;
        }

        if (isJumping) {
            cowboy.y += jumpVelocity * Gdx.graphics.getDeltaTime(); // Move up
            if (cowboy.y >= MAX_JUMP_HEIGHT - 1) {
                isJumping = false;
                isFalling = true;
            }
        } else if (isFalling) {
            cowboy.y -= jumpVelocity * Gdx.graphics.getDeltaTime(); // Move down
            if (cowboy.y <= 0) {
                isJumping = false;
                isFalling = false;
            }
        }
    }

    /**
     * Updates the cowboy's animation based on the current frame.
     *
     * @param runningSprite The array of running sprites.
     */
    private void updateCowboy(Sprite[] runningSprite) {
        currentFrame = (currentFrame + 1) % runningSprite.length;
        runningSprite[currentFrame].setPosition(cowboy.x, cowboy.y);
        runningSprite[currentFrame].draw(gameScreen.game.batch);
        isFacingLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT); // Update facing direction
    }

    /**
     * Ensures the cowboy stays within the screen bounds.
     */
    protected void isWithinBounds() {
        if (cowboy.y < 0) {
            cowboy.y  = 0; // Prevent cowboy from moving below the bottom edge
        }

        // Ensure cowboy stays within screen bounds
        if (cowboy.x < 0) {
            cowboy.x = 0; // Prevent cowboy from moving beyond the left edge
        }
        if (cowboy.x > 800 - 64) {
            cowboy.x = 800 - 64; // Prevent cowboy from moving beyond the right edge
        }
    }
}
