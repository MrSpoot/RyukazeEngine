package ryukazev2.objects.mesh;

import org.joml.Vector3f;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class CustomMesh extends Mesh{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMesh.class);
    private int vao;
    private int vbo;
    private int ibo;

    private float[] vertices;
    private int[] indices;

    public CustomMesh(String path){
        try{
            loadOBJModel(path);

            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 12, 0);
            glEnableVertexAttribArray(0);

            ibo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);

        }catch (Exception e){
            LOGGER.warn("Unable to load mesh model | Path : ["+path+"] | Exception : "+e);
        }
    }

    private void loadOBJModel(String filePath) throws Exception {

        List<Vector3f> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Vector3f> verticesNormal = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("ALLOOO");
                String[] tokens = line.split(" ");
                switch (tokens[0]) {
                    case "v":
                        // Lecture des sommets
                        Vector3f vertex = new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3]));
                        vertices.add(vertex);
                        break;
                    case "f":
                        // Lecture des faces (indices)
                        String[] vertexIndices = tokens[1].split("/");
                        indices.add(Integer.parseInt(vertexIndices[0]) - 1);
                        vertexIndices = tokens[2].split("/");
                        indices.add(Integer.parseInt(vertexIndices[0]) - 1);
                        vertexIndices = tokens[3].split("/");
                        indices.add(Integer.parseInt(vertexIndices[0]) - 1);
                        break;
                    case "vn":
                        Vector3f normal = new Vector3f(
                                Float.parseFloat(tokens[1]),
                                Float.parseFloat(tokens[2]),
                                Float.parseFloat(tokens[3]));
                        verticesNormal.add(normal);
                        break;

                }
            }
            this.vertices = new float[vertices.size() * 6];
            this.indices = new int[indices.size()];

            int verticesIndex = 0;

            for(Vector3f v : vertices){
                this.vertices[verticesIndex] = v.x;
                verticesIndex++;
                this.vertices[verticesIndex] = v.y;
                verticesIndex++;
                this.vertices[verticesIndex] = v.z;
                verticesIndex++;
            }

            int indicesIndex = 0;

            for(Integer i : indices){
                this.indices[indicesIndex] = i;
                indicesIndex++;
            }

        }
    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

}
