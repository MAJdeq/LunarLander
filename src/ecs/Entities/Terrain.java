package ecs.Entities;

import edu.usu.graphics.Color;
import edu.usu.graphics.Texture;

import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Terrain {
    public static Entity create(Texture square, int x, int y) {
        var terrain = new Entity();
//        terrain.add(new ecs.Components.Appearance(Null, Color.WHITE));
        terrain.add(new ecs.Components.Position(x, y, 0f));
        terrain.add(new ecs.Components.Collision());

        return terrain;
    }
}
