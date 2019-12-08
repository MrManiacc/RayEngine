package me.jrayn.engine.ecs.components;

import com.artemis.Component;
import me.jrayn.render.model.Model;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Represents a model that has been loaded from opengl
 */
public class RenderModel extends Component {
    public List<Model> models = new ArrayList<>();
    private RawMesh rawMesh;
    private Logger logger = LogManager.getLogger();

    /**
     * This method will create the model from a raw model, but it must be called
     * after an opengl context has been created!!!
     *
     * @param rawMesh the raw model to create a loadedModel from
     */
    public void addMesh(RawMesh rawMesh) {
        this.rawMesh = rawMesh;
        Model model = Model.create();
        int[] binds = getBindsArray(rawMesh);
        model.bind(binds); //Vertices, normals, texture coords
        if (rawMesh.hasVertices())
            model.createAttribute(0, rawMesh.vertices, 3);
        if (rawMesh.hasNormals())
            model.createAttribute(1, rawMesh.normals, 3);
        if (rawMesh.hasTextureCoords())
            model.createAttribute(2, rawMesh.textureCoords, 2);
        if (rawMesh.hasTangents())
            model.createAttribute(3, rawMesh.tangents, 3);
        if (rawMesh.hasBiTangents())
            model.createAttribute(4, rawMesh.biTangents, 4);
        if (rawMesh.hasIndices())
            model.createIndexBuffer(rawMesh.indices);
        else
            model.setIndexCount(rawMesh.getVertexCount());
        model.unbind(binds);
        models.add(model);
    }

    /**
     * Simple helper to get the binds as an int[]
     *
     * @return int[] of binds
     */
    private int[] getBindsArray(RawMesh rawMesh) {
        List<Integer> bindsArray = new ArrayList<>();
        if (rawMesh.hasVertices())
            bindsArray.add(0);
        if (rawMesh.hasNormals())
            bindsArray.add(1);
        if (rawMesh.hasTextureCoords())
            bindsArray.add(2);
        if (rawMesh.hasTangents())
            bindsArray.add(3);
        if (rawMesh.hasBiTangents())
            bindsArray.add(4);
        return bindsArray.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Render the actual model
     * this model doesnt have to be used but can
     * as a helper
     */
    public void render() {
        for (Model model : models) {
            model.bindAll();
            if (rawMesh.hasIndices())
                glDrawElements(GL_TRIANGLES, model.getIndexCount(), GL_UNSIGNED_INT, 0);
            else
                glDrawArrays(GL_TRIANGLES, 0, model.getIndexCount());
            model.unbindAll();
        }
    }


}
