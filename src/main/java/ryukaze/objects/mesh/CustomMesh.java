package ryukaze.objects.mesh;

import org.lwjgl.assimp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private final List<CustomMeshData> meshes;

    public CustomMesh(String path){
        this.meshes = new LinkedList<>();
        try{
            loadModel(path);
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
        AIVector3D.Buffer texCords = mesh.mTextureCoords(0);

        while (vertices.remaining() > 0){
            AIVector3D vertex = vertices.get();
            AIVector3D normal = normals.get();
            AIVector3D texCoord = texCords.get();

            // Ajouter les données de vertex
            interleavedData.add(vertex.x());
            interleavedData.add(vertex.y());
            interleavedData.add(vertex.z());

            // Ajouter les données de normale
            interleavedData.add(normal.x());
            interleavedData.add(normal.y());
            interleavedData.add(normal.z());

            if(texCords != mesh.mTextureCoords(0)){
                // Ajouter les données de texture
                interleavedData.add(texCoord.x());
                interleavedData.add(1 - texCoord.y());
            }else{
                interleavedData.add(0f);
                interleavedData.add(0f);
            }

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

        float[] _vertices = new float[interleavedData.size()];
        for (int i = 0; i < _vertices.length; i++) {
            _vertices[i] = interleavedData.get(i);
        }

        int[] _indices = new int[indices.size()];
        for (int i = 0; i < _indices.length; i++) {
            _indices[i] = indices.get(i);
        }
        this.meshes.add(new CustomMeshData(_vertices, _indices));
    }

    @Override
    public void render() {
        this.meshes.forEach(CustomMeshData::render);
    }

    public static class CustomMeshData{

        private final int vao;
        private int vbo;
        private int ibo;
        private float[] vertices;
        private int[] indices;

        public CustomMeshData(float[] vertices, int[] indices){
            this.vertices = vertices;
            this.indices = indices;

            this.vao = glGenVertexArrays();
            glBindVertexArray(this.vao);

            this.vbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
            glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0);
            glEnableVertexAttribArray(0);

            glVertexAttribPointer(1, 3, GL_FLOAT, false, 32, 12);
            glEnableVertexAttribArray(1);

            glVertexAttribPointer(2, 2, GL_FLOAT, false, 32, 24);
            glEnableVertexAttribArray(2);

            this.ibo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        }

        public void render() {
            glDisable(GL_CULL_FACE);
            glBindVertexArray(this.vao);
            glDrawElements(GL_TRIANGLES, this.indices.length, GL_UNSIGNED_INT, 0);
            glBindVertexArray(0);
            glEnable(GL_CULL_FACE);
        }

    }

}
