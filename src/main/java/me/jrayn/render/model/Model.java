package me.jrayn.render.model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a model in the game
 * stores a vao with the specified amount of vbos
 * used for rendering to opengl
 */
public class Model {
    private final int id;
    private List<Vbo> dataVbos = new ArrayList<>();
    private Vbo indexVbo;
    private int indexCount;
    private final static Set<Model> loadedModels = new HashSet<>();

    /**
     * this creates a new opengl vao instance
     *
     * @return new vao instance
     */
    public static Model create() {
        int id = GL30.glGenVertexArrays();
        return new Model(id);
    }

    public void setIndexCount(int count) {
        this.indexCount = count;
    }

    private Model(int id) {
        this.id = id;
        loadedModels.add(this);
    }

    /**
     * Deletes all of the loaded models, only should be called
     * when window closing otherwise you'll delete all of your game models and probably crash
     */
    public static void disposeAll() {
        for (Model model : loadedModels)
            model.delete();
    }

    public int getIndexCount() {
        return indexCount;
    }

    /**
     * Binds a vao and the specified vbo attributes
     *
     * @param attributes vbo id's
     */
    public void bind(int... attributes) {
        bind();
        for (int i : attributes) {
            GL20.glEnableVertexAttribArray(i);
        }
    }

    /**
     * Unbinds a vao and it's corresponding vbo id's
     *
     * @param attributes vbo id's
     */
    public void unbind(int... attributes) {
        for (int i : attributes) {
            GL20.glDisableVertexAttribArray(i);
        }
        unbind();
    }

    /**
     * Stores the indices for the vao into a opengl buffer
     *
     * @param indices the indices for the model
     */
    public void createIndexBuffer(int[] indices) {
        this.indexVbo = Vbo.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
        indexVbo.bind();
        indexVbo.storeData(indices);
        this.indexCount = indices.length;
    }

    /**
     * Stores the indices for the vao into a opengl buffer
     */
    public void createIndexBuffer(IntBuffer data) {
        this.indexVbo = Vbo.create(GL15.GL_ELEMENT_ARRAY_BUFFER);
        indexVbo.bind();
        indexVbo.storeData(data);
    }

    /**
     * Creates a vbo to store some specific data into. This is used for rendering
     *
     * @param attribute the attribute to bind to
     * @param data      the data to be put into the vbo
     * @param attrSize  the size 3 for 3d and 2 for 2d
     */
    public void createAttribute(int attribute, float[] data, int attrSize) {
        Vbo dataVbo = Vbo.create(GL15.GL_ARRAY_BUFFER);
        dataVbo.bind();
        dataVbo.storeData(data);
        GL20.glVertexAttribPointer(attribute, attrSize, GL11.GL_FLOAT, false, 0, 0);
        dataVbo.unbind();
        dataVbos.add(dataVbo);
    }

    /**
     * Creates a vbo to store some specific data into. This is used for rendering
     *
     * @param attribute the attribute to bind to
     * @param data      the data to be put into the vbo
     * @param attrSize  the size 3 for 3d and 2 for 2d
     */
    public void createAttribute(int attribute, FloatBuffer data, int attrSize) {
        Vbo dataVbo = Vbo.create(GL15.GL_ARRAY_BUFFER);
        dataVbo.bind();
        dataVbo.storeData(data);
        GL20.glVertexAttribPointer(attribute, attrSize, GL11.GL_FLOAT, false, 0, 0);
        dataVbo.unbind();
        dataVbos.add(dataVbo);
    }

    /**
     * Creates a vbo to store some specific data into. This is used for rendering
     *
     * @param attribute the attribute to bind to
     * @param data      the data to be put into the vbo
     * @param attrSize  the size 3 for 3d and 2 for 2d
     */
    public void createIntAttribute(int attribute, int[] data, int attrSize) {
        Vbo dataVbo = Vbo.create(GL15.GL_ARRAY_BUFFER);
        dataVbo.bind();
        dataVbo.storeData(data);
        GL30.glVertexAttribIPointer(attribute, attrSize, GL11.GL_INT, 0, 0);
        dataVbo.unbind();
        dataVbos.add(dataVbo);
    }

    /**
     * Creates a vbo to store some specific data into. This is used for rendering
     *
     * @param attribute the attribute to bind to
     * @param data      the data to be put into the vbo
     * @param attrSize  the size 3 for 3d and 2 for 2d
     */
    public void createIntAttribute(int attribute, IntBuffer data, int attrSize) {
        Vbo dataVbo = Vbo.create(GL15.GL_ARRAY_BUFFER);
        dataVbo.bind();
        dataVbo.storeData(data);
        GL30.glVertexAttribIPointer(attribute, attrSize, GL11.GL_INT, 0, 0);
        dataVbo.unbind();
        dataVbos.add(dataVbo);
    }

    /**
     * Unloads the vao from memory
     */
    public void delete() {
        GL30.glDeleteVertexArrays(id);
        for (Vbo vbo : dataVbos) {
            vbo.delete();
        }
        indexVbo.delete();
    }

    private void bind() {
        GL30.glBindVertexArray(id);
    }

    private void unbind() {
        GL30.glBindVertexArray(0);
    }

    private static class Vbo {
        private final int vboId;
        private final int type;

        private Vbo(int vboId, int type) {
            this.vboId = vboId;
            this.type = type;
        }

        /**
         * Creates a new vbo instance
         *
         * @param type
         * @return the new vbo
         */
        public static Vbo create(int type) {
            int id = GL15.glGenBuffers();
            return new Vbo(id, type);
        }

        /**
         * Bind the vbo for rendering
         */
        public void bind() {
            GL15.glBindBuffer(type, vboId);
        }

        /**
         * Unbind the vbo when done rendering
         */
        public void unbind() {
            GL15.glBindBuffer(type, 0);
        }

        /**
         * Stores the float data into this vbo
         *
         * @param data the data that is to be put into the vbo
         */
        public void storeData(float[] data) {
            FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
            buffer.put(data);
            buffer.flip();
            storeData(buffer);
        }

        /**
         * Stores the float data into this vbo
         *
         * @param data the data that is to be put into the vbo
         */
        public void storeData(int[] data) {
            IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
            buffer.put(data);
            buffer.flip();
            storeData(buffer);
        }

        /**
         * Stores the float data into this vbo
         *
         * @param data the data that is to be put into the vbo
         */
        public void storeData(IntBuffer data) {
            GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
        }

        public void storeData(FloatBuffer data) {
            GL15.glBufferData(type, data, GL15.GL_STATIC_DRAW);
        }

        public void delete() {
            GL15.glDeleteBuffers(vboId);
        }
    }
}
