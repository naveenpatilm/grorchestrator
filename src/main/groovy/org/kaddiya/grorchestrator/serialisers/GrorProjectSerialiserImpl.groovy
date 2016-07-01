package org.kaddiya.grorchestrator.serialisers

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.transform.CompileStatic
import org.kaddiya.grorchestrator.models.core.GrorProject

/**
 * Created by Webonise on 24/06/16.
 */
@CompileStatic
class GrorProjectSerialiserImpl implements GrorProjectSerialiser {

    @Override
    GrorProject constructGrorProject(File name) {
        if (!name)
            throw new IllegalStateException("Filename cant be null")
        if (!name.exists())
            throw new IllegalStateException("File $name doesnt exist")

        ObjectMapper mapper = new ObjectMapper();
        GrorProject grorProject = mapper.readValue(name.text, GrorProject.class);
        return grorProject;

    }


}
