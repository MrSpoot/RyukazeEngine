package SimpleTestV2;

import org.spoot.ryukazev2.component.TransformComponent;
import org.spoot.ryukazev2.graphic.component.ui.UITextComponent;
import org.spoot.ryukazev2.graphic.core.Entity;
import org.spoot.ryukazev2.core.Statistics;
import org.spoot.ryukazev2.graphic.core.UIEntity;
import org.spoot.ryukazev2.core.interfaces.IUIScript;
import org.spoot.ryukazev2.graphic.manager.CameraManager;
import org.spoot.ryukazev2.utils.ServiceLocator;

import java.util.Map;

public class UIScript implements IUIScript {

    private UIEntity entity;

    @Override
    public void init(UIEntity entity) {
        this.entity = entity;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

        Map<String, UITextComponent> texts = this.entity.getComponents(UITextComponent.class);

        texts.get("fps").setText("FPS : " + Statistics.getStats("fps"));
        texts.get("ups").setText("UPS : " + Statistics.getStats("ups"));
        texts.get("frame").setText("Frame : " + Statistics.getStats("framesRendered"));

        Entity camera = ServiceLocator.getService(CameraManager.class).getActiveCamera();

        TransformComponent transform = camera.getComponent(TransformComponent.class);

        texts.get("position").setText("Position : { X : " + transform.getPosition().x + " | Y : " + transform.getPosition().y + " | Z : " + transform.getPosition().z + " }");

    }

    @Override
    public void cleanup() {

    }
}
