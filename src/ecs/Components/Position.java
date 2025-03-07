package ecs.Components;

import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class Position extends Component {
    public int x, y; // ✅ Now stores primary coordinates
    public List<Vector2i> segments = new ArrayList<>();

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        segments.add(new Vector2i(x, y)); // ✅ Ensure at least one segment
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

        // ✅ Update the first segment if it exists
        if (!segments.isEmpty()) {
            segments.get(0).set(x, y);
        } else {
            segments.add(new Vector2i(x, y));
        }
    }
}
