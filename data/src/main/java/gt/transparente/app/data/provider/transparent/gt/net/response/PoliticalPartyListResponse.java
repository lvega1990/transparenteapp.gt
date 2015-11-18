package gt.transparente.app.data.provider.transparent.gt.net.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import gt.transparente.app.data.entity.PoliticalPartyEntity;

/**
 * Class that represents a Transparente.gt Response.
 */
public class PoliticalPartyListResponse extends Response {

    @SerializedName("_embedded")
    private Result mResult;

    public Result getResult() {
        return mResult;
    }

    public class Result {
        @SerializedName("partido_politico")
        private ArrayList<PoliticalPartyEntity> mPoliticalPartyEntities;

        public ArrayList<PoliticalPartyEntity> getPoliticalPartyEntities() {
            return mPoliticalPartyEntities;
        }
    }
}
