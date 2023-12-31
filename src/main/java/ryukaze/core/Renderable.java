package ryukaze.core;

import lombok.Data;
import ryukaze.objects.GameObject;
import ryukaze.objects.material.Texture;

@Data
public class Renderable {

    float depth;
    boolean isTransparent;
    private GameObject gameObject;

    public Renderable(float depth,GameObject gameObject){
        this.depth = depth;
        this.gameObject = gameObject;
        isTransparent = false;
    }

    public void render(){
        if(gameObject.getMesh() != null){
            Texture texture = gameObject.getMesh().getMaterial().getTextures().get("diffuse");
            if(texture != null){
                this.isTransparent = texture.isHasTransparency();
            }
        }
        this.gameObject._render();
    }

    public void update(){
        this.gameObject._update();
    }

}
