package ecs.Components;

import edu.usu.graphics.Triangle;
import org.joml.Vector3f;

public class TriangleComponent extends Component {
    public Triangle triangle;
    public Vector3f v1;
    public Vector3f v2;
    public Vector3f v3;

    public TriangleComponent(Vector3f v1, Vector3f v2, Vector3f v3) {
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.triangle = new Triangle(v1, v2, v3);
    }

    public Triangle getTriangle() {
        return triangle;
    }
}
