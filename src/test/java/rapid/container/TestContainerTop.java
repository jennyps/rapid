package rapid.container;

import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by hakan on 17/02/2017.
 */
public class TestContainerTop extends ContainerConfig {

    @Test
    public void inspectContainer() {
        final Response listContainers = getResponse(target("containers").path("json"));
        final JsonArray containers = listContainers.readEntity(JsonArray.class);
        final JsonObject container = (JsonObject) containers.get(0);
        final JsonString expectedId = container.getJsonString("Id");

        Response top = getResponse(target("containers").path(expectedId.getString()).path("top"));
        assertEquals(200, top.getStatus());

        final JsonObject actualContainer = top.readEntity(JsonObject.class);
        int expectedProcessSize = 1;
        assertEquals(expectedProcessSize, actualContainer.getJsonArray("Processes").size());
    }
}
