package ecs.Systems;

import ecs.Components.Gravity;
import ecs.Components.Movable;
import ecs.Components.Position;
import ecs.Components.Velocity;

public class Movement extends System {
    public Movement() {
        super(Movable.class, Position.class, Velocity.class);
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            applyGravity(entity, elapsedTime);
            moveEntity(entity, elapsedTime);
        }
    }

    private void applyGravity(ecs.Entities.Entity entity, double elapsedTime) {
        var gravity = entity.get(Gravity.class);
        var velocity = entity.get(Velocity.class);

        if (gravity != null && velocity != null) {
            velocity.yVelocity += gravity.acceleration * elapsedTime;
        }
    }

    private void moveEntity(ecs.Entities.Entity entity, double elapsedTime) {
        var movable = entity.get(Movable.class);
        var velocity = entity.get(Velocity.class);
        var position = entity.get(Position.class);

        float speed = 10f;
        switch (movable.facing) {
            case Left:
                velocity.xVelocity = -speed;
                break;
            case Right:
                velocity.xVelocity = speed;
                break;
            case Up:
                velocity.yVelocity = -speed;
                break;
            case Stopped:
                velocity.xVelocity = 0;
                break;
        }

        position.x += (float) (velocity.xVelocity * elapsedTime);
        position.y += (float) (velocity.yVelocity * elapsedTime);
    }
}
