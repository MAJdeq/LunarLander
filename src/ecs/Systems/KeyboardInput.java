package ecs.Systems;

import ecs.Components.*;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends System {

    private final long window;
    private final float ROTATION_SPEED = 5f; // degrees per second
    private final float THRUST = 1.5f; // thrust acceleration

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
            var velocity = entity.get(Velocity.class);
            var input = entity.get(KeyboardControlled.class);

            // Reset acceleration every frame
            acceleration.xAcceleration = 0f;
            acceleration.yAcceleration = 0f;
            movable.facing = Movable.Direction.Stopped;

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.Up)) == GLFW_PRESS) {
                float rad = (float) Math.toRadians(position.angle - 90);
                acceleration.xAcceleration += (float) Math.cos(rad) * THRUST;
                acceleration.yAcceleration += (float) Math.sin(rad) * THRUST;
            } else {
                acceleration.xAcceleration = 0;
                acceleration.yAcceleration = 0;
            }
            if (glfwGetKey(window, input.lookup.get(Movable.Direction.RotationLeft)) == GLFW_PRESS) {
                position.angle -= ROTATION_SPEED * elapsedTime; // Only rotate angle, no velocity change
            }

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.RotationRight)) == GLFW_PRESS) {
                position.angle += ROTATION_SPEED * elapsedTime; // Only rotate angle, no velocity change
            }

            java.lang.System.out.println("Angle: " + position.angle);
            java.lang.System.out.println("Acceleration X: " + acceleration.xAcceleration + " Y: " + acceleration.yAcceleration);
            java.lang.System.out.println("Velocity X: " + velocity.xVelocity + " Y: " + velocity.yVelocity);
            java.lang.System.out.println("Position X: " + position.x + " Y: " + position.y);

        }
    }
}
