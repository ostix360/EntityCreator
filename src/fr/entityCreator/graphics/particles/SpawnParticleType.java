package fr.entityCreator.graphics.particles;


import fr.entityCreator.graphics.particles.particleSpawn.*;

public enum SpawnParticleType {
    CIRCLE(new Circle(), "Circle spawn"),
    LINE(new Line(), "Line spawn"),
    POINT(new Point(), "Point spawn"),
    SPHERE(new Sphere(), "Sphere spawn");

    private final ParticleSpawn spawn;
    private final String name;

    SpawnParticleType(ParticleSpawn spawn, String description) {
        this.spawn = spawn;
        this.name = description;
    }

    public ParticleSpawn getSpawn() {
        return spawn;
    }

}
