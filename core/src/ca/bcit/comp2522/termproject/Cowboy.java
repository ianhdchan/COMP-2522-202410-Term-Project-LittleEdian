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
    /** The number three. */
    public static final int THREE = 3;
    /** Screen width. */
    private static final int SCREEN_WIDTH = 800;
    /** Speed of horizontal movement. */
    private static final float HORIZONTAL_SPEED = 200;

    /** Speed of vertical movement for jumping/falling. */
    private static final float VERTICAL_SPEED = 250;

    /** Maximum height the cowboy can jump. */
    private static final float MAX_JUMP_HEIGHT = 200;

    /** Width of the cowboy. */
    private static final float COWBOY_WIDTH = 64;

    /** Height of the cowboy. */
    private static final float COWBOY_HEIGHT = 64;
    /** The GameScreen instance associated with this Cowboy object. */
    protected final GameScreen gameScreen;
    /** Array of textures for running left animation. */
    protected final Texture[] runningLeft = new Texture[THREE];

    /** Array of textures for running right animation. */
    protected final Texture[] runningRight = new Texture[THREE];

    /** Array of sprites for running left animation. */
    protected final Sprite[] runningLeftSprite = new Sprite[THREE];

    /** Array of sprites for running right animation. */
    protected final Sprite[] runningRightSprite = new Sprite[THREE];
    /** Rectangle representing the cowboy's position and dimensions. */
    protected Rectangle cowboy;

    /** Tracks current frame of sprites. */
    private int currentFrame = 0;

    /** Flag indicating if the cowboy is facing left. */
    private boolean isFacingLeft = false;

    /** Flag indicating if the cowboy is jumping. */
    private boolean isJumping = false;

    /** Flag indicating if the cowboy is falling. */
    private boolean isFalling = false;
    /** Texture for the cowboy when still facing left. */
    private Texture cowboyStillL;

    /** Texture for the cowboy when still facing right. */
    private Texture cowboyStillR;
    /**
     * Constructs a new Cowboy object associated with the specified GameScreen.
     *
     * @param gameScreen The GameScreen instance.
     */
    public Cowboy(final GameScreen gameScreen) {
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
        for (int i = 0; i < THREE; i++) {
            runningLeftSprite[i] = new Sprite(runningLeft[i]);
        }

        // Load cowboy sprite images for running right
        runningRight[0] = new Texture(Gdx.files.internal("Cowboy_R1.png"));
        runningRight[1] = new Texture(Gdx.files.internal("Cowboy_R2.png"));
        runningRight[2] = new Texture(Gdx.files.internal("Cowboy_R3.png"));
        for (int i = 0; i < THREE; i++) {
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
        final int cowboyPositionY = 0;
        cowboy = new Rectangle();
        cowboy.x = ((float) SCREEN_WIDTH / 2 - COWBOY_WIDTH / 2); // Center the cowboy horizontally
        cowboy.y = cowboyPositionY; // Position the cowboy at the bottom of the screen

        cowboy.width = COWBOY_WIDTH; // Set width to 64 pixels
        cowboy.height = COWBOY_HEIGHT; // Set height to 64 pixels
    }

    /**
     * Handles cowboy movement based on user input.
     * Supports left and right movement, jumping, and falling.
     */
    public void cowboyMovement() {
        handleHorizontalMovement();
        handleJumping();
        handleFalling();
    }

    private void handleHorizontalMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updateCowboy(runningLeftSprite);
            cowboy.x -= HORIZONTAL_SPEED * Gdx.graphics.getDeltaTime(); // Move left
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updateCowboy(runningRightSprite);
            cowboy.x += HORIZONTAL_SPEED * Gdx.graphics.getDeltaTime(); // Move right
        } else {
            handleStillPose();
        }
    }

    private void handleStillPose() {
        Texture stillSprite;
        if (isFacingLeft) {
            stillSprite = cowboyStillL;
        } else {
            stillSprite = cowboyStillR;
        }
        gameScreen.game.batch.draw(stillSprite, cowboy.x, cowboy.y);
    }

    private void handleJumping() {
        if (Gdx.input.isKeyPressed(Input.Keys.UP) && !isJumping && !isFalling) {
            isJumping = true;
        }

        if (isJumping) {
            cowboy.y += VERTICAL_SPEED * Gdx.graphics.getDeltaTime(); // Move up
            if (cowboy.y >= MAX_JUMP_HEIGHT - 1) {
                isJumping = false;
                isFalling = true;
            }
        }
    }

    private void handleFalling() {
        if (isFalling) {
            cowboy.y -= VERTICAL_SPEED * Gdx.graphics.getDeltaTime(); // Move down
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
    private void updateCowboy(final Sprite[] runningSprite) {
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
        if (cowboy.x > SCREEN_WIDTH - COWBOY_WIDTH) {
            cowboy.x = SCREEN_WIDTH - COWBOY_WIDTH; // Prevent cowboy from moving beyond the right edge
        }
    }
}
