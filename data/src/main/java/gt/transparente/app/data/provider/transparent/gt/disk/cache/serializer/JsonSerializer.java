
package gt.transparente.app.data.provider.transparent.gt.disk.cache.serializer;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import gt.transparente.app.data.entity.PoliticalPartyEntity;

/**
 * Class Political Party as Serializer/Deserializer for Political Party entities.
 */
@Singleton
public class JsonSerializer {

    private final Gson mGson = new Gson();

    @Inject
    public JsonSerializer() {
    }

    /**
     * Serialize an object to Json.
     *
     * @param politicalPartyEntity {@link PoliticalPartyEntity} to serialize.
     */
    public String serialize(PoliticalPartyEntity politicalPartyEntity) {
        return mGson.toJson(politicalPartyEntity, PoliticalPartyEntity.class);
    }

    /**
     * Deserialize a json representation of an object.
     *
     * @param jsonString A json string to deserialize.
     * @return {@link PoliticalPartyEntity}
     */
    public PoliticalPartyEntity deserialize(String jsonString) {
        return mGson.fromJson(jsonString, PoliticalPartyEntity.class);
    }
}
