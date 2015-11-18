
package gt.transparente.app.presentation.model;

/**
 * Political Party used in the presentation layer.
 */
public class PoliticalPartyModel {

    private final int mId;

    public PoliticalPartyModel(int id) {
        this.mId = id;
    }

    private String mName;

    private String mAbbreviation;

    public int getId() {
        return mId;
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
