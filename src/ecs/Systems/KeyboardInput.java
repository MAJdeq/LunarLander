package ecs.Systems;

import ecs.Components.KeyboardControlled;
import ecs.Components.Acceleration;
import ecs.Components.Movable;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends System {

    private final long window;
    private final float THRUST = 20f; // Adjust thrust strength as desired

    public KeyboardInput(long window) {
        super(KeyboardControlled.class, Acceleration.class, Movable.class);
        this.window = window;
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            var acceleration = entity.get(Acceleration.class);
            var input = entity.get(KeyboardControlled.class);

            acceleration.xAcceleration = 0f;
            acceleration.yAcceleration = 0f;

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.Up)) == GLFW_PRESS) {
                acceleration.yAcceleration = -THRUST; // upward thrust
            }
            if (glfwGetKey(window, input.lookup.get(Movable.Direction.Left)) == GLFW_PRESS) {
                acceleration.xAcceleration = -THRUST; // leftward thrust
            }
            if (glfwGetKey(window, input.lookup.get(Movable.Direction.Right)) == GLFW_PRESS) {
                acceleration.xAcceleration = THRUST; // rightward thrust
            }
        }
    }
}
