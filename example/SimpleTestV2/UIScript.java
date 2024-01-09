package SimpleTestV2;

import ryukazev2.component.game.TransformComponent;
import ryukazev2.component.ui.UITextComponent;
import ryukazev2.core.Entity;
import ryukazev2.core.Statistics;
import ryukazev2.core.UIEntity;
import ryukazev2.core.interfaces.IUIScript;
import ryukazev2.manager.CameraManager;
import ryukazev2.utils.ServiceLocator;

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
