package ryukaze.objects.mesh;

import org.lwjgl.assimp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

            loadModel(path);

            vao = glGenVertexArrays();
            glBindVertexArray(vao);

            vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 24, 0);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, 3, GL_FLOAT, false, 24, 12);
            glEnableVertexAttribArray(1);

            ibo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);

        }catch (Exception e){
            LOGGER.warn("Unable to load mesh model | Path : ["+path+"] | Exception : "+e);
        }
    }

    public void loadModel(String path) {
        AIScene scene = Assimp.aiImportFile(path,
                Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate);

        if (scene == null) {
            throw new RuntimeException("Failed to load model: " + path);
        }

        processNode(Objects.requireNonNull(scene.mRootNode()), scene);
        Assimp.aiReleaseImport(scene);
    }

    private void processNode(AINode node, AIScene scene) {
        for (int i = 0; i < node.mNumMeshes(); i++) {
            AIMesh mesh = AIMesh.create(Objects.requireNonNull(scene.mMeshes()).get(Objects.requireNonNull(node.mMeshes()).get(i)));
            processMesh(mesh, scene);
        }

        for (int i = 0; i < node.mNumChildren(); i++) {
            processNode(AINode.create(Objects.requireNonNull(node.mChildren()).get(i)), scene);
        }
    }

    private void processMesh(AIMesh mesh, AIScene scene) {

        List<Float> interleavedData = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        AIVector3D.Buffer vertices = mesh.mVertices();
        AIVector3D.Buffer normals = mesh.mNormals();

        while (vertices.remaining() > 0){
            AIVector3D vertex = vertices.get();
            AIVector3D normal = normals.get();

            // Ajouter les données de vertex
            interleavedData.add(vertex.x());
            interleavedData.add(vertex.y());
            interleavedData.add(vertex.z());

            // Ajouter les données de normale
            interleavedData.add(normal.x());
            interleavedData.add(normal.y());
            interleavedData.add(normal.z());

        }

        // Indices
        for (int i = 0; i < mesh.mNumFaces(); i++) {
            AIFace face = mesh.mFaces().get(i);
            IntBuffer indicesBuffer = face.mIndices();
            while (indicesBuffer.remaining() > 0) {
                int index = indicesBuffer.get();
                indices.add(index);
            }
        }

        this.vertices = new float[interleavedData.size()];
        for (int i = 0; i < this.vertices.length; i++) {
            this.vertices[i] = interleavedData.get(i);
        }

        this.indices = new int[indices.size()];
        for(int i =0; i< this.indices.length; i++){
            this.indices[i] = indices.get(i);
        }

    }

    @Override
    public void render() {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, this.indices.length, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

}
