package ecs.Systems;

import ecs.Components.Appearance;
import ecs.Components.TriangleComponent;
import ecs.Entities.Entity;
import edu.usu.graphics.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Renderer extends System {
    private final int GRID_SIZE;
    private final float CELL_SIZE;
    private final float OFFSET_X;
    private final float OFFSET_Y;

    private final Graphics2D graphics;

    public Renderer(Graphics2D graphics, int gridSize) {
        super(ecs.Components.Appearance.class,
                ecs.Components.Position.class);

        OFFSET_X = 0.1f;
        OFFSET_Y = 0.1f;
        CELL_SIZE = (1.0f - OFFSET_X * 2) / 20;
        GRID_SIZE = gridSize;
        this.graphics = graphics;
    }

    @Override
    public void update(double elapsedTime) {
//        Rectangle area = new Rectangle(-1.0f, -1.0f, 2.0f, 2.0f);
//        Texture texture = new Texture("resources/images/space.jpg");
//        graphics.draw(texture, area, Color.WHITE);

        // Draw each of the game entities!
        for (var entity : entities.values()) {
            renderEntity(entity);
        }
    }


    private void renderEntity(ecs.Entities.Entity entity) {
        var appearance = entity.get(ecs.Components.Appearance.class);
        var position = entity.get(ecs.Components.Position.class);

        Rectangle area = new Rectangle(0, 0, 0, 0);
        area.left = -0.5f + OFFSET_X + position.x * CELL_SIZE;
        area.top = -0.5f + OFFSET_Y + position.y * CELL_SIZE;
        area.width = CELL_SIZE;
        area.height = CELL_SIZE;

        if (entity.contains(TriangleComponent.class)) {
            var triangleComponent = entity.get(TriangleComponent.class);

            if (triangleComponent != null) {
                float scaleFactor = 2.0f / GRID_SIZE;
                Triangle triangle = new Triangle(
                        new Vector3f(triangleComponent.v1.x * scaleFactor - 1.0f, triangleComponent.v1.y * scaleFactor - 1.0f, 0),
                        new Vector3f(triangleComponent.v2.x * scaleFactor - 1.0f, triangleComponent.v2.y * scaleFactor - 1.0f, 0),
                        new Vector3f(triangleComponent.v3.x * scaleFactor - 1.0f, triangleComponent.v3.y * scaleFactor - 1.0f, 0)
                );
                graphics.draw(triangle, appearance.color);

                graphics.draw(triangle, appearance.color); // Render triangle
            }
        } else {
            graphics.draw(
                    appearance.image,
                    area,
                    position.angle,
                    new Vector2f(area.left + area.width / 2, area.top + area.height / 2), // rotation around center
                    appearance.color
            );
        }
    }


}

