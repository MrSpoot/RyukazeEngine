package ryukazev2.component.shape;

import org.lwjgl.assimp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ryukaze.objects.mesh.CustomMesh;

import java.nio.IntBuffer;
import java.util.*;

public class CustomShape implements IShape{

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomMesh.class);
    private float[] vertices;
    private int[] indices;

    private final List<MeshData> meshes;

    public CustomShape(String path){
        this.meshes = new LinkedList<>();
        try{
            loadModel(path);
            concatData();
        }catch (Exception e){
            LOGGER.warn("\033[1;33m[LOAD]\033[0m Unable to load mesh model | Path : ["+path+"] | Exception : "+e);
        }
    }

    public void concatData(){

        List<Float> _vertices = new ArrayList<>();
        List<Integer> _indices = new ArrayList<>();

        for(MeshData data : meshes){
            for(int i : data.indices){
                _indices.add(i + _vertices.size() / 8);
            }
            for(float f : data.vertices){
                _vertices.add(f);
            }
        }

        this.vertices = new float[_vertices.size()];
        this.indices = new int[_indices.size()];

        for(int i = 0; i < _vertices.size();i++){
            this.vertices[i] = _vertices.get(i);
        }
        for(int i = 0; i < _indices.size();i++){
            this.indices[i] = _indices.get(i);
        }


    }

    @Override
    public float[] getVertices() {
        return vertices;
    }

    @Override
    public int[] getIndices() {
        return indices;
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
            interleavedData.add(-1f * vertex.x());
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
        this.meshes.add(new MeshData(_vertices, _indices));
    }

    public static class MeshData{

        private float[] vertices;
        private int[] indices;

        public MeshData(float[] vertices, int[] indices) {
            this.vertices = vertices;
            this.indices = indices;
        }
    }
}
