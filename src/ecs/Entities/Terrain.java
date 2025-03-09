package ecs.Entities;

import ecs.Components.Appearance;
import ecs.Components.Position;
import ecs.Components.Collision;
import ecs.Components.TriangleComponent;
import edu.usu.graphics.Color;
import edu.usu.graphics.Texture;
import edu.usu.graphics.Triangle;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    public static Entity create(Texture texture, Triangle triangle) {
        System.out.println("Creating terrain segment...");

        Entity terrain = new Entity();

        terrain.add(new Appearance(texture, Color.BLUE));
        terrain.add(new Position(0, 0, 0f)); // Position can be adjusted later
        terrain.add(new Collision()); // If needed for physics
        terrain.add(new TriangleComponent(triangle.pt1, triangle.pt2, triangle.pt3));

        return terrain;
    }




}
