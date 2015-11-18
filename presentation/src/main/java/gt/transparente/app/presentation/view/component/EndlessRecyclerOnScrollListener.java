package gt.transparente.app.presentation.view.component;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private boolean mLoading = false; // True if we are still waiting for the last set of data to load

    private int previousItemCount = 0; // The total number of items in the dataset after the last load

    private int mTotalEntries; // The total number of entries in the server
    private int current_page = 1; // Always start at Page 1

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    // Concrete classes should implement the Loading of more data entries
    public abstract void onLoadMore(int current_page);

    public void setTotalEntries(int totalEntries) {
        mTotalEntries = totalEntries;
    }

    // when you're RecyclerView supports refreshing, also refresh the count
    public void refresh() {
        current_page = 1;
        previousItemCount = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (mLoading) {
            int diffCurrentFromPrevious = totalItemCount - previousItemCount;

            // check if current total is greater than previous (diff should be greater than 1, for considering placeholder)
            // and if current total is equal to the total in server
            if ((diffCurrentFromPrevious > 1) ||
                    totalItemCount >= mTotalEntries) {
                mLoading = false;
                previousItemCount = totalItemCount;
            }
        } else {

            if (totalItemCount >= mTotalEntries) {
                // do nothing, we've reached the end of the list
            } else {
                // check if the we've reached the end of the list,
                // and if the total items is less than the total items in the server
                if ((firstVisibleItem + visibleItemCount) >= totalItemCount &&
                        totalItemCount < mTotalEntries) {
                    onLoadMore(++current_page);

                    mLoading = true;
                    previousItemCount = totalItemCount;
                }
            }
        }
    }

}