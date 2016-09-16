package org.kaddiya.grorchestrator.managers.impl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.transform.CompileStatic
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import org.kaddiya.grorchestrator.guice.factory.DockerContainerCreatorFactory
import org.kaddiya.grorchestrator.helpers.HostConfigBuilder
import org.kaddiya.grorchestrator.managers.DockerContainerCreator
import org.kaddiya.grorchestrator.managers.DockerContainerRunnerManager
import org.kaddiya.grorchestrator.managers.DockerRemoteAPI
import org.kaddiya.grorchestrator.models.core.Instance
import org.kaddiya.grorchestrator.models.remotedocker.requests.HostConfig
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerContainerCreationResponse
import org.kaddiya.grorchestrator.models.remotedocker.responses.DockerRemoteGenericOKResponse

/**
 * Created by Webonise on 11/07/16.
 */
@CompileStatic
class DockerContainerRunnerManagerImpl extends DockerRemoteAPI<DockerRemoteGenericOKResponse> implements DockerContainerRunnerManager {

    final DockerContainerCreator containerCreator
    final HostConfigBuilder hostConfigBuilder

    @Inject
    DockerContainerRunnerManagerImpl(
            @Assisted Instance instance, DockerContainerCreatorFactory creatorFactory, HostConfigBuilder hostConfigBuilder) {
        super(instance)
        containerCreator = creatorFactory.create(this.instance)
        this.hostConfigBuilder = hostConfigBuilder
    }

    @Override
    void    runContainer() {
        DockerContainerCreationResponse containerCreationResponse = containerCreator.createContainer()
        if (!containerCreationResponse) {
            throw new IllegalStateException("Something has gone wrong in the creating the container")
        }
        doWork()
    }

    @Override
    Request constructRequest() {
        String request
        HostConfig config = hostConfigBuilder.constructHostConfig(instance)
        if(instance.host.dockerVersion == '1.18'){
            request = new JsonBuilder(config)
        }else{
            request = ""
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        return new Request.Builder()
                .url("$baseUrl/containers/$instance.name/start")
                .post(RequestBody.create(JSON, request))
                .build();
    }


}

