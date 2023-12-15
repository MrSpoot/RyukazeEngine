package ryukazev2.objects.controller;

import ryukazev2.core.Engine;
import ryukazev2.core.Transform;
import ryukazev2.objects.GameObject;

import java.util.HashMap;

public class CharacterController extends GameObject implements Controller {

    public CharacterController() {
        super(new Transform(),null,null);
        Engine.getScene().subscribeController(this);
    }

    @Override
    public final void _processMouseInput(float x, float y){
        processMouseInput(x,y);
    }
    public void processMouseInput(float x, float y){

    }


}
