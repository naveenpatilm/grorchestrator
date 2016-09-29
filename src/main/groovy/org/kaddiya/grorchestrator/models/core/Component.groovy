package org.kaddiya.grorchestrator.models.core

import groovy.transform.Canonical

/**
 * Created by Webonise on 19/03/16.
 */
@Canonical
class Component {
    String name
    List<Instance> instances
}