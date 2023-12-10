package ryukaze.scene;

import lombok.Data;

import java.util.HashMap;

@Data
public class SceneManager {

    private Scene actualScene;
    private HashMap<Long, Scene> sceneHashMap;

    public SceneManager(){
        this.sceneHashMap = new HashMap<>();
    }

}
