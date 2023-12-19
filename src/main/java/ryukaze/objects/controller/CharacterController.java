package ryukaze.objects.controller;

import ryukaze.core.Engine;
import ryukaze.core.Transform;
import ryukaze.objects.GameObject;

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
