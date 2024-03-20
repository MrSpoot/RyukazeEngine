package org.spoot.ryukazev2.graphic.component.game;

import org.spoot.ryukazev2.graphic.core.interfaces.IScript;

public class ScriptComponent extends Component{

    private IScript script;

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

    public void update() {
        this.script.update();
    }

    public void render() {
        this.script.render();
    }

    public void cleanup() {
        this.script.cleanup();
    }
}
