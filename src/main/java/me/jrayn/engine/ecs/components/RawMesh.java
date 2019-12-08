package me.jrayn.engine.ecs.components;

import com.artemis.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents just raw vertices normals and texture coordinates, this will be generated in the appropriate time
 * into {@link RenderModel}
 */
public class RawMesh extends Component {
    public float[] vertices;
    public float[] normals;
    public float[] textureCoords;
    public float[] tangents;
    public float[] biTangents;
    public int[] indices;
    private final Logger logger = LogManager.getLogger();

    /**
     * Simple method to check if vertices are present, should always be true
     *
     * @return vertices status
     */
    public boolean hasVertices() {
        if (vertices != null) {
            return vertices.length > 0;
        }
        return false;
    }


    /**
     * Simple method to check if normals are present, should always be true
     *
     * @return normals status
     */
    public boolean hasNormals() {
        if (normals != null) {
            return normals.length > 0;
        }
        return false;
    }


    /**
     * Simple method to check if tangents are present, not always true
     *
     * @return tangents status
     */
    public boolean hasTangents() {
        if (tangents != null) {
            return tangents.length > 0;
        }
        return false;
    }


    /**
     * Simple method to check if biTangents are present, not always true
     *
     * @return biTangents status
     */
    public boolean hasBiTangents() {
        if (biTangents != null) {
            return biTangents.length > 0;
        }
        return false;
    }


    /**
     * Simple method to check if textureCoords are present, should always be true
     *
     * @return vertices status
     */
    public boolean hasTextureCoords() {
        if (textureCoords != null) {
            return textureCoords.length > 0;
        }
        return false;
    }

    /**
     * Check to see if model has indices, this is actual important because if it doesn't have indices then we're going to render with
     * glDrawArrays, while the default is glDrawElements
     *
     * @return indices status
     */
    public boolean hasIndices() {
        if (indices != null) {
            return indices.length > 0;
        }
        return false;
    }

    /**
     * Gets the total vertices
     *
     * @return vertices count
     */
    public int getVertexCount() {
        if (hasIndices())
            return indices.length;
        if (hasVertices())
            return vertices.length / 3;
        logger.warn("No indices or vertices set, vertex count returning 0!");
        return 0;
    }
}
