package ryukazev2.objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import ryukazev2.core.Engine;
import ryukazev2.core.Transform;
import ryukazev2.core.interfaces.IScript;
import ryukazev2.objects.mesh.Mesh;
import ryukazev2.physics.body.PhysicBody;
import ryukazev2.utils.UniqueIdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EqualsAndHashCode
@Data
public abstract class GameObject  {

    private String id;
    protected Transform transform;
    protected Mesh mesh;
    protected GameObject parent;
    protected HashMap<String,GameObject> children;
    protected PhysicBody physicBody;
    protected List<IScript> scripts;

    public GameObject(Transform transform, Mesh mesh, GameObject parent) {
        this.id = UniqueIdGenerator.generateUniqueID(15);
        this.transform = transform;
        this.mesh = mesh;
        this.parent = parent;
        this.children = new HashMap<>();
        this.scripts = new ArrayList<>();
        if(parent == null){
            Engine.getScene().subscribe(this);
        }
    }

    public final void _render(){
        children.values().forEach(GameObject::_render);
        scripts.forEach(IScript::render);
        Matrix4f modelMatrix = new Matrix4f()
                .translate(getGlobalTransform().getPosition())
                .rotateXYZ(getGlobalTransform().getRotation())
                .scale(getGlobalTransform().getScale());
        Engine.getScene().getShader().setUniform("model",modelMatrix);
        Engine.getScene().getShader().setUniform("normalMatrix",new Matrix3f(modelMatrix).invert(new Matrix3f()).transpose());
        if(mesh != null){
            mesh._render();
        }
        render();
    }

    public final void _update(){
        if(physicBody != null){
            physicBody._update(this);
        }
        children.values().forEach(GameObject::_update);
        scripts.forEach(IScript::update);
        update();
    }

    public void render() {

    }

    public void update() {

    }

    public final void addScript(IScript script){
        this.scripts.add(script);
    }

    public final void addChildren(GameObject object){
        object.setParent(this);
        this.children.put(object.id,object);
    }

    public final Transform getGlobalTransform(){
        if(parent == null){
            return this.transform;
        }else{
            return this.transform.combine(parent.getGlobalTransform());
        }
    }
}
