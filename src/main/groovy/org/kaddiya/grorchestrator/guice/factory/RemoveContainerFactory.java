package org.kaddiya.grorchestrator.guice.factory;

import org.kaddiya.grorchestrator.managers.interfaces.RemoveContainer;
import org.kaddiya.grorchestrator.models.core.latest.Host;
import org.kaddiya.grorchestrator.models.core.latest.Instance;

/**
 * Created by Webonise on 14/07/16.
 */
public interface RemoveContainerFactory {
    RemoveContainer create(Instance instance,Host host);
}
