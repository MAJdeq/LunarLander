package ecs.Systems;

import ecs.Components.*;

public class Movement extends System {
    public Movement() {
        super(Movable.class, Position.class, Velocity.class);
    }

    @Override
    public void update(double elapsedTime) {
        for (var entity : entities.values()) {
            applyGravity(entity, elapsedTime);
            applyAcceleration(entity, elapsedTime);
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


    private void applyAcceleration(ecs.Entities.Entity entity, double elapsedTime) {
        var acceleration = entity.get(Acceleration.class);
        var velocity = entity.get(Velocity.class);

        if (acceleration != null && velocity != null) {
            velocity.xVelocity += acceleration.xAcceleration * elapsedTime;
            velocity.yVelocity += acceleration.yAcceleration * elapsedTime;
        }
    }
    private void moveEntity(ecs.Entities.Entity entity, double elapsedTime) {
        var position = entity.get(Position.class);
        var velocity = entity.get(Velocity.class);

        float stopThreshold = 0.05f;
        if (Math.abs(velocity.xVelocity) < stopThreshold) velocity.xVelocity = 0;
        if (Math.abs(velocity.yVelocity) < stopThreshold) velocity.yVelocity = 0;

        // Apply damping to gradually reduce velocity when no acceleration
        float dampingFactor = 0.98f; // Adjust between 0.9 - 1 for different levels of friction
        velocity.xVelocity *= dampingFactor;
        velocity.yVelocity *= dampingFactor;

        // Update position based on velocity
        position.x += velocity.xVelocity * elapsedTime;
        position.y += velocity.yVelocity * elapsedTime;
    }

}
