package org.spoot.ryukazev2.physic.component.body;

import org.spoot.ryukazev2.component.Component;
import org.spoot.ryukazev2.physic.manager.BodyManager;
import org.spoot.ryukazev2.utils.ServiceLocator;

public class Body extends Component {

    public Body() {
        ServiceLocator.getService(BodyManager.class).subscribe(this);
    }
}
