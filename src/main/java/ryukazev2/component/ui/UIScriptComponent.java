package ryukazev2.component.ui;

import ryukazev2.core.interfaces.IUIScript;

public class UIScriptComponent extends UIComponent{

    private IUIScript script;

    public UIScriptComponent() {
        super("");
    }

    public UIScriptComponent linkScript(IUIScript script){
        this.script = script;
        return this;
    }

    public UIScriptComponent build(){
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
