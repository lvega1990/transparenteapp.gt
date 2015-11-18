
package gt.transparente.app.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Employee Entity used in the data layer.
 */
public class PoliticalPartyEntity {

    @SerializedName("id")
    private int mId;

    @SerializedName("nombre")
    private String mName;

    @SerializedName("iniciales")
    private String mAbbreviation;

    public PoliticalPartyEntity() {
        //empty
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAbbreviation() {
        return mAbbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.mAbbreviation = abbreviation;
    }
}
