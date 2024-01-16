package Physics;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.spoot.SimpleTestV2.UIScript;
import org.spoot.ryukaze.graphics.Image;
import org.spoot.ryukazev2.Engine;
import org.spoot.ryukazev2.component.game.ScriptComponent;
import org.spoot.ryukazev2.component.ui.UICircleComponent;
import org.spoot.ryukazev2.component.ui.UIScriptComponent;
import org.spoot.ryukazev2.component.ui.UITextComponent;
import org.spoot.ryukazev2.core.Entity;
import org.spoot.ryukazev2.core.InputTouch;
import org.spoot.ryukazev2.core.UIEntity;
import org.spoot.ryukazev2.core.enumerations.Anchor;
import org.spoot.ryukazev2.entity.Camera;
import org.spoot.ryukazev2.entity.DirectionalLight;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class PhysicsApp {

    public static void main(String[] args) {

        Engine engine = new Engine().build();

        new InputTouch("exit_engine",GLFW_KEY_ESCAPE);

        new Camera();//.linkComponent(new ScriptComponent().linkScript(new Movement()).build());
        new Entity().linkComponent(new ScriptComponent().linkScript(new PhysicsScript()).build());

        new DirectionalLight();

        new UIEntity()
                .linkComponent(new UITextComponent("fps").setSize(10f).setFont("Retro").setPosition(new Vector2f(5,10)).build())
                .linkComponent(new UITextComponent("frame").setSize(10f).setFont("Retro").setPosition(new Vector2f(500,10)).build())
                .linkComponent(new UITextComponent("ups").setSize(10f).setFont("Retro").setPosition(new Vector2f(5,20)).build())
                .linkComponent(new UITextComponent("position").setSize(10f).setFont("Retro").setPosition(new Vector2f(5,30)).build())
                .linkComponent(new UICircleComponent("center-pointer")
                        .setPosition(new Vector2f(640,360))
                        .setAnchor(Anchor.CENTER)
                        .setscale(new Vector2f(0.02f,0.02f))
                        .setImage(new Image(new Vector4f(0f,0f,0f,1f)))
                        .build())
                .linkComponent(new UIScriptComponent().linkScript(new UIScript()).build());

        engine.run();

    }

}
