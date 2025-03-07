package ecs.Entities;

import edu.usu.graphics.Color;
import edu.usu.graphics.Texture;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Terrain {
    public static Entity create(Texture square, int x, int y) {
        var rocket = new Entity();


        rocket.add(new ecs.Components.Position(x, y, 0f));
        rocket.add(new ecs.Components.Collision());

        return rocket;
    }
}
