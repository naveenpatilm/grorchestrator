package org.kaddiya.grorchestrator.helpers

import groovy.json.JsonOutput
import org.kaddiya.grorchestrator.helpers.impl.HostConfigBuilderImpl
import org.kaddiya.grorchestrator.models.core.Host
import org.kaddiya.grorchestrator.models.core.Instance
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by Webonise on 13/07/16.
 */
class HostConfigBuilderImplSpec extends Specification {

    @Shared
    HostConfigBuilder fixture = new HostConfigBuilderImpl()

    def "getBinds should return the proper object of Binds of HostConfig as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        List<String> result = (fixture as HostConfigBuilderImpl).getBinds(instance)
        then:
        assert result.size() == 2
        assert result.get(0) == "1:2"
    }

    def "getPortBindings should return a proper volume mapping as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        Map<String, Map<String, String>> result = (fixture as HostConfigBuilderImpl).getPortBindings(instance)
        println(JsonOutput.toJson(result))
        then:
        assert "{\"22/tcp\":[{\"HostPort\":\"11022\"}]}" == JsonOutput.toJson(result)
    }

    def "getExtraHostsMapping should return a proper volume mapping as expected by the Docker remote api"() {
        given:
        Instance instance = getDummyInstance()
        when:
        List<String> result = (fixture as HostConfigBuilderImpl).getExtraHostsMapping(instance)

        then:
        assert result.size() == 2
        assert result.get(0) == "some.hostname-1.on.container:ip.for.host-1"
        assert result.get(1) == "some.hostname-2.on.container:ip.for.host-2"
    }


    Instance getDummyInstance() {
        return new Instance("redis.proof.com", "redis", "latest",
                new Host("127.0.0.1", "redis-vm-1", 2376),
                Collections.unmodifiableMap(["1": "2", "3": "4"]), Collections.unmodifiableMap([22: 11022]) as Map<Integer, Integer>,
                Collections.unmodifiableMap(["some.hostname-1.on.container": "ip.for.host-1", "some.hostname-2.on.container": "ip.for.host-2"])

        )
    }
}