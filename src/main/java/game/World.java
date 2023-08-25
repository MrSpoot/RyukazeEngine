package game;

import engine.graph.Model;
import engine.scene.Entity;
import engine.scene.ModelLoader;
import engine.scene.Scene;

import java.util.UUID;

public class World {

    private Entity cubeEntity;

    public void createWorld(Scene scene){
        Model cubeModel = ModelLoader.loadModel("cube-model","resources/models/cube/cube.obj",scene.getTextureCache());
        scene.addModel(cubeModel);

        cubeEntity = new Entity(UUID.randomUUID().toString(), cubeModel.getId());
        cubeEntity.setPosition(0, 0, 0);
        scene.addEntity(cubeEntity);

        cubeEntity = new Entity(UUID.randomUUID().toString(), cubeModel.getId());
        cubeEntity.setPosition(0, 1, 0);
        scene.addEntity(cubeEntity);
    }

}
