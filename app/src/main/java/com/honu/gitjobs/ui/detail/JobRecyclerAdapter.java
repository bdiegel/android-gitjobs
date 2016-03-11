package com.honu.gitjobs.ui.detail;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.honu.gitjobs.R;
import com.honu.gitjobs.rest.Job;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Adapter to display jobs in recycler view
 */
public class JobRecyclerAdapter extends RecyclerView.Adapter<JobRecyclerAdapter.JobItemViewHolder> {

    static final String TAG = JobRecyclerAdapter.class.getSimpleName();

    JobClickListener mClickListener;

    ArrayList<Job> data = new ArrayList<>();

    public interface JobClickListener {
        public void onJobClick(View view, Job selection);
    }

    public JobRecyclerAdapter(JobClickListener listener) {
        mClickListener = listener;
    }

    public void setData(List<Job> data) {
        this.data.clear();
        this.data.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public JobItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.job_item, parent, false);
        return new JobItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(JobItemViewHolder holder, int position) {
        Job job = data.get(position);
        holder.titleTextView.setText(job.getTitle());
        holder.companyTextView.setText(job.getCompanyName());

        if (!TextUtils.isEmpty(job.getCompanyLogo())) {
            Picasso.with(holder.logoImageView.getContext())
                  .load(job.getCompanyLogo())
                  .into(holder.logoImageView);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class JobItemViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.job_title) TextView titleTextView;
        @Bind(R.id.company_name) TextView companyTextView;
        @Bind(R.id.company_logo) ImageView logoImageView;

        public JobItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.job_item)
        public void onClick() {
            Job job = data.get(getAdapterPosition());
            mClickListener.onJobClick(this.itemView, job);
        }
    }
}
