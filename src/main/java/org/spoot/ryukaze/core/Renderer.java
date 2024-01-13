package org.spoot.ryukaze.core;

import lombok.Getter;
import lombok.Setter;
import org.spoot.ryukaze.graphics.Camera;

import java.util.Comparator;
import java.util.LinkedList;

public class Renderer {

    @Getter @Setter
    private Camera camera;
    public final LinkedList<Renderable> renderables;

    public Renderer(){
        this.renderables = new LinkedList<>();
    }

    public void subscribe(Renderable object) {
        renderables.add(object);
    }

    public void sortRenderQueue() {
        renderables.sort(new Comparator<Renderable>() {
            @Override
            public int compare(Renderable o1, Renderable o2) {
                if (o1.isTransparent() && !o2.isTransparent()) return 1;
                if (!o1.isTransparent() && o2.isTransparent()) return -1;
                return Float.compare(o2.getDepth(), o1.getDepth());
            }
        });
    }

    public void render(){
        if(this.camera != null){
            //System.out.println("Camera : "+this.camera.getGlobalTransform().position.toString());
            this.renderables.forEach((r) -> {
                r.setDepth(this.camera.getGlobalTransform().position.distance(r.getGameObject().getGlobalTransform().position));
            });
            sortRenderQueue();
            this.renderables.forEach(Renderable::render);
        }
    }

    public void update(){
        if(this.camera != null){
            this.renderables.forEach(Renderable::update);
        }
    }


}
