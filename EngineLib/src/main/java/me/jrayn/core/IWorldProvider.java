package me.jrayn.core;

import com.artemis.*;

public interface IWorldProvider {
    /**
     * Create the world config
     *
     * @return the world configuration
     */
     WorldConfiguration createConfig(BaseSystem... systems);

    /**
     * Get a system by it's type
     *
     * @param clazz the system class type
     * @param <T>   the system type
     * @return the system it's self
     */
     <T extends BaseSystem> T getSystem(Class<T> clazz);

    /**
     * Create a new entity id reference which is automatically added to the world
     *
     * @return new entity id
     */
     int createEntity();

    /**
     * Deletes an entity with the given id
     *
     * @param id the deleted id
     * @return whether or not the entity was deleted
     */
     boolean deleteEntity(int id);

    /**
     * Simple method to get the actual entity reference from the di
     *
     * @param id the entity id
     * @return the entity instance
     */
     Entity getEntity(int id);

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
     <T extends Component> T create(int id, Class<T> clazz);

    /**
     * Simply adds a component to the entity, if the component type exsists the old component
     * get's overridden and the new component take's it's spot
     *
     * @param id        the entity id
     * @param component the component to add
     */
     void add(int id, Component component);

    /**
     * Delets the given component type from an entity
     *
     * @param id    the entity id
     * @param clazz the class type to create a component for
     * @param <T>   the type of class to delete
     */
     <T extends Component> void delete(int id, Class<T> clazz);

    /**
     * Process the world
     */
     void process();

    /**
     * Gets an entity based on it's tag
     *
     * @param tag the entity tag
     * @return entity with given tag
     */
     Entity getTagEntity(String tag);


    /**
     * Register an entity with a given tag
     *
     * @param tag    the entity tag
     * @param entity the entity to register
     */
     void registerTagEntity(String tag, Entity entity);

    /**
     * Unregister an entity with a given tag
     *
     * @param tag the entity tag
     */
     void unregisterTagEntity(String tag);

    /**
     * Get the raw world
     *
     * @return raw world
     */
     World getWorld();
}
