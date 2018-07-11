package com.artenesnogueira.bakingapp.views.steps;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.artenesnogueira.bakingapp.R;
import com.artenesnogueira.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter to display a list of steps.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private List<Step> mSteps = new ArrayList<>(0);
    private final OnStepClicked mOnStepClickedListener;

    public StepsAdapter(OnStepClicked onStepClickedListener) {
        mOnStepClickedListener = onStepClickedListener;
    }

    public void setData(@NonNull List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.step_card, null);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
        Step step = mSteps.get(position);
        holder.bind(step);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView description;

        StepsViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.txv_description);
        }

        void bind(Step step) {
            description.setText(step.getShortDescription());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnStepClickedListener.onStepClicked(mSteps.get(getAdapterPosition()), getAdapterPosition());
        }

    }

    public interface OnStepClicked {
        void onStepClicked(Step step, int index);
    }

}
