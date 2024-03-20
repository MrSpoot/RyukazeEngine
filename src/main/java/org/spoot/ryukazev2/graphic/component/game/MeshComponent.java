package org.spoot.ryukazev2.graphic.component.game;

import lombok.Getter;
import org.spoot.ryukazev2.graphic.component.game.shape.IShape;
import org.spoot.ryukazev2.graphic.core.Cache;
import org.spoot.ryukazev2.graphic.core.MeshOpenGLData;
import org.spoot.ryukazev2.graphic.graphics.Material;

import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class MeshComponent extends Component{

    @Getter
    private MeshOpenGLData data;
    private float[] vertices;
    private int[] indices;
    @Getter
    private Material material;

    public MeshComponent(){
        this.vertices = new float[0];
        this.indices = new int[0];
        this.data = new MeshOpenGLData();
    }

    public MeshComponent applyShape(IShape shape){
        this.vertices = shape.getVertices();
        this.indices = shape.getIndices();
        return this;
    }

    public MeshComponent setMaterial(Material material){
        this.material = material;
        return this;
    }

    public MeshComponent setVertices(float[] vertices){
        this.vertices = vertices;
        return this;
    }

    public MeshComponent setIndices(int[] indices){
        this.indices = indices;
        return this;
    }

    public MeshComponent build(){

        if(this.material == null){
            this.material = new Material();
        }

        HashMap<String,Object> attributes = new HashMap<>();

        attributes.put("vertices",this.vertices);
        attributes.put("indices",this.indices);
        attributes.put("material",this.material);

        if(Cache.isObjectCached(attributes)){
            this.data = Cache.getCacheObject(attributes);
        }else{

            this.data.setVao(glGenVertexArrays());
            int vbo = glGenBuffers();
            int ibo = glGenBuffers();

            glBindVertexArray(this.data.getVao());

            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);


            glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, 3, GL_FLOAT, false, 32, 12);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, 2, GL_FLOAT, false, 32, 24);
            glEnableVertexAttribArray(2);

            int instanceVbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, instanceVbo);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);

            this.data.setMaterial(this.material);
            this.data.setVbo(instanceVbo);

            Cache.putCacheObject(attributes,this.data);
        }

        return this;
    }

    public int getIndicesCount(){
        return this.indices.length;
    }



}
