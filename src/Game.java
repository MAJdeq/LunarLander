import edu.usu.graphics.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;

public class Game {
    private final Graphics2D graphics;

    private ParticleSystem particleSystemFire;
    private ParticleSystem particleSystemSmoke;
    private ParticleSystemRenderer particleSystemRendererFire;
    private ParticleSystemRenderer particleSystemRendererSmoke;
    private HashMap<GameStateEnum, IGameState> states;
    private IGameState currentState;
    GameStateEnum nextStateEnum = GameStateEnum.MainMenu;
    GameStateEnum prevStateEnum = GameStateEnum.MainMenu;

    private Texture texSessler;
    public Game(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public void initialize() {
        states = new HashMap<>() {
            {
                put(GameStateEnum.MainMenu, new MainMenuView());
                put(GameStateEnum.GamePlay, new GamePlayView());
                put(GameStateEnum.HighScores, new HighScoresView());
                put(GameStateEnum.KeyboardSettings, new KeyboardSettingsView());
                put(GameStateEnum.Credits, new CreditsView());
            }
        };
        for (var state : states.values()) {
            state.initialize(graphics);
        }

        currentState = states.get(GameStateEnum.MainMenu);
        currentState.initializeSession();

        texSessler = new Texture("resources/images/space.jpg");;
        particleSystemFire = new ParticleSystem(
                new Vector2f(0, 0),
                0.01f, 0.005f,
                0.12f, 0.05f,
                2, 0.5f);

        particleSystemSmoke = new ParticleSystem(
                new Vector2f(0, 0),
                0.015f, 0.004f,
                0.07f, 0.05f,
                3, 1);

        particleSystemRendererFire = new ParticleSystemRenderer();
        particleSystemRendererFire.initialize("resources/images/fire.png");

        particleSystemRendererSmoke = new ParticleSystemRenderer();
        particleSystemRendererSmoke.initialize("resources/images/smoke.png");
    }

    public void shutdown() {
        particleSystemRendererFire.cleanup();
        texSessler.cleanup();
    }

    public void run() {
        // Grab the first time
        double previousTime = glfwGetTime();

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!graphics.shouldClose()) {
            double currentTime = glfwGetTime();
            double elapsedTime = currentTime - previousTime;    // elapsed time is in seconds
            previousTime = currentTime;



            processInput(elapsedTime);
            update(elapsedTime);
            render(graphics.getWindow(), elapsedTime);
        }
    }

    private void processInput(double elapsedTime) {
        // Poll for window events: required in order for window, keyboard, etc events are captured.
        glfwPollEvents();

        nextStateEnum = currentState.processInput(elapsedTime);
    }

    private void update(double elapsedTime) {
        if (nextStateEnum == GameStateEnum.Quit) {
            glfwSetWindowShouldClose(graphics.getWindow(), true);
        } else {
            if (nextStateEnum == prevStateEnum) {
                currentState.update(elapsedTime);
            } else {
                currentState = states.get(nextStateEnum);
                currentState.initializeSession();
                prevStateEnum = nextStateEnum;
            }
        }

        particleSystemFire.update(elapsedTime);
        particleSystemSmoke.update(elapsedTime);
    }

    private void render(long window, double elapsedTime) {
        graphics.begin();

        currentState.render(elapsedTime);

        graphics.end();
    }

}
