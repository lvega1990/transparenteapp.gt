package gt.transparente.app.domain;

/**
 * Political Party used in the domain layer.
 */
public class PoliticalParty {


    private final int mId;

    public PoliticalParty(int id) {
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
