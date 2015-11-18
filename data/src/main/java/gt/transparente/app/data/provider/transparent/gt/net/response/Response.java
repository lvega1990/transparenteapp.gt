package gt.transparente.app.data.provider.transparent.gt.net.response;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represents a Transparente.gt Response.
 */
public class Response<T> {

    @SerializedName("page_count")
    private int mPageCount;

    @SerializedName("page_size")
    private int mPageSize;

    @SerializedName("total_items")
    private int mTotalItem;

    @SerializedName("page")
    private int mPage;

    public Response() {
        //empty
    }

    public int getPageCount() {
        return mPageCount;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public int getTotalItem() {
        return mTotalItem;
    }

    public int getPage() {
        return mPage;
    }
}
