
package gt.transparente.app.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import gt.transparente.app.presentation.R;
import gt.transparente.app.presentation.model.PoliticalPartyModel;

import java.util.Collection;
import java.util.List;

/**
 * Adaptar that manages a collection of {@link PoliticalPartyModel}.
 */
public class PoliticalPartyAdapter extends RecyclerView.Adapter<PoliticalPartyAdapter.PoliticalPartyViewHolder> {

    public interface OnItemClickListener {
        void onPoliticalPartyItemClicked(PoliticalPartyModel politicalPartyModel);
    }

    private List<PoliticalPartyModel> mPoliticalPartyCollection;
    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public PoliticalPartyAdapter(Context context, Collection<PoliticalPartyModel> politicalPartyCollection) {
        this.validatePoliticalPartyCollection(politicalPartyCollection);
        this.mLayoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPoliticalPartyCollection = (List<PoliticalPartyModel>) politicalPartyCollection;
    }

    @Override
    public int getItemCount() {
        return (this.mPoliticalPartyCollection != null) ? this.mPoliticalPartyCollection.size() : 0;
    }

    @Override
    public PoliticalPartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = this.mLayoutInflater.inflate(R.layout.row_political_party, parent, false);
        return new PoliticalPartyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PoliticalPartyViewHolder holder, final int position) {
        final PoliticalPartyModel politicalPartyModel = this.mPoliticalPartyCollection.get(position);
        holder.textViewTitle.setText(politicalPartyModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PoliticalPartyAdapter.this.mOnItemClickListener != null) {
                    PoliticalPartyAdapter.this.mOnItemClickListener.onPoliticalPartyItemClicked(politicalPartyModel);
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setPoliticalPartyCollection(Collection<PoliticalPartyModel> politicalPartyCollection) {
        this.validatePoliticalPartyCollection(politicalPartyCollection);
        this.mPoliticalPartyCollection = (List<PoliticalPartyModel>) politicalPartyCollection;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    private void validatePoliticalPartyCollection(Collection<PoliticalPartyModel> politicalPartyModelCollection) {
        if (politicalPartyModelCollection == null) {
            throw new IllegalArgumentException("The list cannot be null");
        }
    }

    static class PoliticalPartyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView textViewTitle;

        public PoliticalPartyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
