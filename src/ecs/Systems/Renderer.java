package ecs.Systems;

import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import edu.usu.graphics.Texture;
import org.joml.Vector2f;

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
        CELL_SIZE = (1.0f - OFFSET_X * 2) / gridSize;
        GRID_SIZE = gridSize;
        this.graphics = graphics;
    }

    @Override
    public void update(double elapsedTime) {
        Rectangle area = new Rectangle(-0.5f + OFFSET_X, -0.5f + OFFSET_Y, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        Texture texture = new Texture("resources/images/space.jpg");
        graphics.draw(texture, area, Color.WHITE);

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

        graphics.draw(
                appearance.image,
                area,
                position.angle,
                new Vector2f(area.left + area.width / 2, area.top + area.height / 2), // rotation around center
                appearance.color
        );
    }

}
