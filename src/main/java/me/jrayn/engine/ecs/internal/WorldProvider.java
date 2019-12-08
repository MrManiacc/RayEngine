package me.jrayn.engine.ecs.internal;

import com.artemis.*;
import com.artemis.managers.TagManager;
import me.jrayn.engine.ecs.IWorldProvider;

/**
 * This is a wrapper around the world, it provides useful features,
 * that are used throughout the engine
 */
public class WorldProvider extends IWorldProvider {
    private World world;

    public WorldProvider(BaseSystem... systems) {
        this.world = new World(createConfig(systems));
    }

    /**
     * Create the configuration with the given systems, priority will be in
     * the order that they're passed
     *
     * @param systems the entity systems
     * @return the world config
     */
    public WorldConfiguration createConfig(BaseSystem... systems) {
        WorldConfigurationBuilder setup = new WorldConfigurationBuilder();
        for (int i = 0; i < systems.length; i++) {
            setup.with(i, systems[i]);
        }
        return setup.build();
    }

    /**
     * Get a system by it's type auto casts to help out
     *
     * @param clazz the system class type
     * @param <T>   the system type
     * @return the system it's self
     */
    public <T extends BaseSystem> T getSystem(Class<T> clazz) {
        return world.getSystem(clazz);
    }


    /**
     * Create a new entity id reference which is automatically added to the world
     *
     * @return new entity id
     */
    public int createEntity() {
        return world.create();
    }

    /**
     * Deletes an entity with the given id
     *
     * @param id the deleted id
     * @return whether or not the entity was deleted
     */
    public boolean deleteEntity(int id) {
        if (getEntity(id) != null) {
            world.delete(id);
            return true;
        }
        return false;
    }

    /**
     * Simple method to get the actual entity reference from the di
     *
     * @param id the entity id
     * @return the entity instance
     */
    public Entity getEntity(int id) {
        return world.getEntity(id);
    }

    /**
     * This method will create a new component for the given entity
     * and given class type. This method will replace an old component
     * with the given type if it exists already
     *
     * @param id    the entity id
     * @param clazz the class type to create a component for
     * @param <T>   the actual component that was created, will return it allowing for you to set parameters then
     * @return the newly created component with the given type
     */
    public <T extends Component> T create(int id, Class<T> clazz) {
        return world.edit(id).create(clazz);
    }

    /**
     * Simply adds a component to the entity, if the component type exsists the old component
     * get's overridden and the new component take's it's spot
     *
     * @param id        the entity id
     * @param component the component to add
     */
    public void add(int id, Component component) {
        world.edit(id).add(component);
    }

    /**
     * Delets the given component type from an entity
     *
     * @param id    the entity id
     * @param clazz the class type to create a component for
     * @param <T>   the type of class to delete
     */
    public <T extends Component> void delete(int id, Class<T> clazz) {
        world.edit(id).remove(clazz);
    }

    /**
     * Process the world
     */
    public void process() {
        world.process();
    }

    /**
     * Gets an entity based on it's tag
     *
     * @param tag the entity tag
     * @return entity with given tag
     */
    public Entity getTagEntity(String tag) {
        return world.getSystem(TagManager.class).getEntity(tag);
    }

    /**
     * Register an entity with a given tag
     *
     * @param tag    the entity tag
     * @param entity the entity to register
     */
    public void registerTagEntity(String tag, Entity entity) {
        world.getSystem(TagManager.class).register(tag, entity);
    }

    /**
     * Unregister an entity with a given tag
     *
     * @param tag the entity tag
     */
    public void unregisterTagEntity(String tag) {
        world.getSystem(TagManager.class).unregister(tag);
    }

    /**
     * Get the raw world
     *
     * @return raw world
     */
    public World getWorld() {
        return world;
    }
}
