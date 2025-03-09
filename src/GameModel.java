import ecs.Entities.*;
import ecs.Systems.*;
import ecs.Systems.KeyboardInput;
import edu.usu.graphics.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private final int GRID_SIZE = 50;
    private final List<Entity> removeThese = new ArrayList<>();
    private final List<Entity> addThese = new ArrayList<>();

    private ecs.Systems.Renderer sysRenderer;
    private ecs.Systems.Collision sysCollision;
    private ecs.Systems.Movement sysMovement;
    private ecs.Systems.KeyboardInput sysKeyboardInput;

    public void initialize(Graphics2D graphics) {
        var texSquare = new Texture("resources/images/rocket.png");
        var blankTri = new Texture("resources/images/white.png");

        sysRenderer = new Renderer(graphics, 50);
        sysCollision = new Collision((Entity entity) -> {

        });
        sysMovement = new Movement();
        sysKeyboardInput = new KeyboardInput(graphics.getWindow());


        initializeRocket(texSquare);
        initializeTerrain(blankTri);
    }

    public void update(double elapsedTime) {
        // Because ECS framework, input processing is now part of the update
        sysKeyboardInput.update(elapsedTime);
        // Now do the normal update
        sysMovement.update(elapsedTime);
        sysCollision.update(elapsedTime);

        for (var entity : removeThese) {
            removeEntity(entity);
        }
        removeThese.clear();

        for (var entity : addThese) {
            addEntity(entity);
        }
        addThese.clear();

        // Because ECS framework, rendering is now part of the update
        sysRenderer.update(elapsedTime);
    }

    private void addEntity(Entity entity) {
        sysKeyboardInput.add(entity);
        sysMovement.add(entity);
        sysCollision.add(entity);
        sysRenderer.add(entity);
    }

    private void removeEntity(Entity entity) {
        sysKeyboardInput.remove(entity.getId());
        sysMovement.remove(entity.getId());
        sysCollision.remove(entity.getId());
        sysRenderer.remove(entity.getId());
    }

    private void initializeRocket(Texture square) {
        MyRandom rnd = new MyRandom();
        boolean done = false;

        while (!done) {
            int x = (int) rnd.nextRange(1, GRID_SIZE - 1);
            int y = (int) rnd.nextRange(1, GRID_SIZE - 1);
            var proposed = Rocket.create(square, x, y);
            if (!sysCollision.collidesWithAny(proposed)) {
                addEntity(proposed);
                done = true;
            }
        }
    }
    private void initializeTerrain(Texture triangle) {
        int iterations = 6;
        double initialRange = 40;
        double[] heights = generate(iterations, initialRange);

        List<Vector2f> terrainPoints = new ArrayList<>();
        int size = heights.length;

        for (int i = 0; i < size; i++) {
            float x = (float) i / (size - 1) * GRID_SIZE;
            float y = (float) (GRID_SIZE - heights[i]); // Invert height
            terrainPoints.add(new Vector2f(x, y));
        }

        // Generate terrain one segment at a time
        for (int i = 0; i < terrainPoints.size() - 1; i++) {
            Vector2f p1 = terrainPoints.get(i);       // Top-left
            Vector2f p2 = terrainPoints.get(i + 1);   // Top-right
            Vector2f p3 = new Vector2f(p1.x, GRID_SIZE); // Bottom-left
            Vector2f p4 = new Vector2f(p2.x, GRID_SIZE); // Bottom-right

            // Create two triangles per segment
            Triangle t1 = new Triangle(new Vector3f(p1.x, p1.y, 0), new Vector3f(p2.x, p2.y, 0), new Vector3f(p3.x, p3.y, 0));
            Triangle t2 = new Triangle(new Vector3f(p2.x, p2.y, 0), new Vector3f(p3.x, p3.y, 0), new Vector3f(p4.x, p4.y, 0));

            // Create entities for each triangle
            Entity terrainEntity1 = Terrain.create(triangle, t1);
            Entity terrainEntity2 = Terrain.create(triangle, t2);

            // Add each triangle as a separate entity
            addEntity(terrainEntity1);
            addEntity(terrainEntity2);
        }
    }






    public static double[] generate(int iterations, double initialRange) {
        int size = (int) Math.pow(2, iterations) + 1;
        double[] data = new double[size];
        Random random = new Random();

        // Initialize endpoints with random values
        data[0] = random.nextDouble() * initialRange;
        data[size - 1] = random.nextDouble() * initialRange;

        // Recursive displacement
        displace(data, 0, size - 1, initialRange, random);

        return data;
    }

    private static void displace(double[] data, int left, int right, double range, Random random) {
        if (right - left <= 1) {
            return; // Base case: interval too small
        }

        int mid = (left + right) / 2;
        data[mid] = (data[left] + data[right]) / 2 + (random.nextDouble() - 0.5) * range;

        // Recursive calls with reduced range
        displace(data, left, mid, range / 2, random);
        displace(data, mid, right, range / 2, random);
    }

}
