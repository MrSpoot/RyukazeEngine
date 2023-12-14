package ryukazev2.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joml.Matrix4f;
import ryukazev2.core.Engine;
import ryukazev2.core.Transform;
import ryukazev2.objects.mesh.Mesh;

import java.util.HashMap;

@EqualsAndHashCode
@Data
public abstract class GameObject  {

    protected Transform transform;
    protected Mesh mesh;
    protected GameObject parent;
    protected HashMap<String,GameObject> children;

    public GameObject(Transform transform, Mesh mesh, GameObject parent, HashMap<String, GameObject> children) {
        this.transform = transform;
        this.mesh = mesh;
        this.parent = parent;
        this.children = children;
    }

    public final void _render(){
        children.values().forEach(GameObject::_render);
        Engine.getScene().getShader().setUniform("model",new Matrix4f().translate(getGlobalTransform().getPosition()));
        if(mesh != null){
            mesh.render();
        }
        render();
    }

    public final void _update(){
        children.values().forEach(GameObject::_update);
        update();
    }

    public void render() {

    }

    public void update() {

    }

    public final void addChildren(GameObject object){
        object.setParent(this);
        this.children.put("test",object);
    }

    public final Transform getGlobalTransform(){
        if(parent == null){
            return this.transform;
        }else{
            return this.transform.combine(parent.getGlobalTransform());
        }
    }
}
