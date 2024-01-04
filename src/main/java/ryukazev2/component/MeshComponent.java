package ryukazev2.component;

import lombok.Getter;
import ryukazev2.component.shape.IShape;
import ryukazev2.graphics.Material;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class MeshComponent extends Component{

    @Getter
    private int vao;
    private int vbo;
    private int ibo;
    private float[] vertices;
    private int[] indices;
    @Getter
    private Material material;

    public MeshComponent(){
        this.vao = 0;
        this.vbo = 0;
        this.ibo = 0;
        this.vertices = new float[0];
        this.indices = new int[0];
        this.material = new Material();
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

        this.vao = glGenVertexArrays();
        this.vbo = glGenBuffers();
        this.ibo = glGenBuffers();

        glBindVertexArray(vao);

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

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        return this;
    }

    public int getIndicesCount(){
        return this.indices.length;
    }



}