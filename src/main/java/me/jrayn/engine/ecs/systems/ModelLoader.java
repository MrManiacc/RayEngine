package me.jrayn.engine.ecs.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Exclude;
import com.artemis.systems.IteratingSystem;
import me.jrayn.core.IGameEngine;
import me.jrayn.engine.ecs.components.RawMesh;
import me.jrayn.engine.ecs.components.RenderModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * This system will iterate all of the entities that have a RawMesh and load them into
 * we only want to select the entities that have a raw model and not a render model
 */
@All(RawMesh.class)
@Exclude(RenderModel.class)
public class ModelLoader extends IteratingSystem {
    protected ComponentMapper<RawMesh> rawModels;
    private final IGameEngine engine;
    private final Logger logger = LogManager.getLogger();

    /**
     * We don't want the model loader running until the opengl context is loaded
     */
    public ModelLoader(IGameEngine gameEngine) {
        this.engine = gameEngine;
    }

    /**
     * Converts all of the rawModels into renderModels
     *
     * @param entityId the entity to convert for
     */
    protected void process(int entityId) {
        RawMesh rawMesh = rawModels.get(entityId);
        RenderModel renderModel = engine.getWorld().create(entityId, RenderModel.class);
        renderModel.addMesh(rawMesh);
        logger.debug("Generated renderModel for entity({}).", entityId);
        engine.getWorld().delete(entityId, RawMesh.class);
    }

}
