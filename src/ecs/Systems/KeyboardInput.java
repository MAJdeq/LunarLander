package ecs.Systems;

import ecs.Components.KeyboardControlled;
import ecs.Components.Movable;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput extends System {

    private final long window;

    public KeyboardInput(long window) {
        super(ecs.Components.KeyboardControlled.class, ecs.Components.Movable.class);
        this.window = window;
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            var movable = entity.get(ecs.Components.Movable.class);
            var input = entity.get(ecs.Components.KeyboardControlled.class);

            movable.facing = ecs.Components.Movable.Direction.Stopped; // Reset direction each update

            if (glfwGetKey(window, input.lookup.get(Movable.Direction.Up)) == GLFW_PRESS) {
                movable.facing = ecs.Components.Movable.Direction.Up;
            } else if (glfwGetKey(window, input.lookup.get(Movable.Direction.Down)) == GLFW_PRESS) {
                movable.facing = Movable.Direction.Down;
            } else if (glfwGetKey(window, input.lookup.get(Movable.Direction.Left)) == GLFW_PRESS) {
                movable.facing = Movable.Direction.Left;
            } else if (glfwGetKey(window, input.lookup.get(Movable.Direction.Right)) == GLFW_PRESS) {
                movable.facing = Movable.Direction.Right;
            } else {
                movable.facing = Movable.Direction.Stopped;
            }
        }
    }
}
