package ecs.Systems;

import ecs.Components.*;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends System {

    private final long window;
    private final float ROTATION_SPEED = 10f; // degrees per second
    private final float THRUST = 10f; // thrust acceleration

    public KeyboardInput(long window) {
        super(KeyboardControlled.class, Acceleration.class, Movable.class, Position.class);
        this.window = window;
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            var acceleration = entity.get(Acceleration.class);
            var movable = entity.get(Movable.class);
            var position = entity.get(Position.class);
            var input = entity.get(KeyboardControlled.class);

            // Reset acceleration every frame
            acceleration.xAcceleration = 0f;
            acceleration.yAcceleration = 0f;
            movable.facing = Movable.Direction.Stopped;

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.Up)) == GLFW_PRESS) {
                // Accelerate forward based on angle
                float rad = (float)Math.toRadians(position.angle - 90);
                acceleration.xAcceleration = (float)Math.cos(rad) * THRUST;
                acceleration.yAcceleration = (float)Math.sin(rad) * THRUST;
                movable.facing = Movable.Direction.Up;
            }

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.RotationLeft)) == GLFW_PRESS) {
                position.angle -= ROTATION_SPEED * elapsedTime; // Only rotate angle, no velocity change
            }

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.RotationRight)) == GLFW_PRESS) {
                position.angle += ROTATION_SPEED * elapsedTime; // Only rotate angle, no velocity change
            }
        }
    }
}
