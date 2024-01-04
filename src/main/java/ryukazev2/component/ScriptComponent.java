package ryukazev2.component;

import ryukazev2.core.IScript;

public class ScriptComponent extends Component{

    private IScript script;

    public ScriptComponent(){

    }

    public ScriptComponent linkScript(IScript script){
        this.script = script;
        return this;
    }

    public ScriptComponent build(){
        return this;
    }

    public void init() {
       this.script.init(this.getEntity());
    }

    public void update(float deltaTime) {
        this.script.update(deltaTime);
    }

    public void render() {
        this.script.render();
    }

    public void cleanup() {
        this.script.cleanup();
    }
}
