package ecs.Components;

public class Movable extends Component {

    public enum Direction {
        Stopped,
        Up,
        Down,
        Left,
        Right,

        RotationLeft,

        RotationRight
    }

    public Direction facing;
    public double moveInterval; // seconds
    public double elapsedInterval;

    public Movable(Direction facing, double moveInterval) {
        this.facing = facing;
        this.moveInterval =moveInterval;
    }
}
