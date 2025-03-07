package ecs.Entities;

import edu.usu.graphics.Color;
import edu.usu.graphics.Texture;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Rocket {
    public static Entity create(Texture square, int x, int y) {
        final double MOVE_INTERVAL = 0; // seconds

        var rocket = new Entity();

        rocket.add(new ecs.Components.Appearance(square, Color.WHITE));
        rocket.add(new ecs.Components.Position(x, y, 0));
        rocket.add(new ecs.Components.Collision());
        rocket.add(new ecs.Components.Gravity());
        rocket.add(new ecs.Components.Velocity());
        rocket.add(new ecs.Components.Acceleration());
        rocket.add(new ecs.Components.Movable(ecs.Components.Movable.Direction.Stopped, MOVE_INTERVAL));
        rocket.add(new ecs.Components.KeyboardControlled(
                Map.of(
                        GLFW_KEY_UP, ecs.Components.Movable.Direction.Up,
                        GLFW_KEY_DOWN, ecs.Components.Movable.Direction.Down,
                        GLFW_KEY_LEFT, ecs.Components.Movable.Direction.Left,
                        GLFW_KEY_RIGHT, ecs.Components.Movable.Direction.Right
                )));

        return rocket;
    }
}
