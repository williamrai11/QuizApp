package com.example.quizapp.quizss.features;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.ui.ClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CatSelectionViewHolde> {

    private Context context;
    private LayoutInflater inflater;
    private List<String> csvEntries;
    private ClickListener clickListener;

    private int icons [] = {
            R.drawable.general,
            R.drawable.history,
            R.drawable.culture,
            R.drawable.nature,
            R.drawable.sc
            };

    private String [] descriptions;

    public RecyclerAdapter(Context context, List<String> csvEntries, String[] descriptions){
        inflater = LayoutInflater.from(context);
        this.csvEntries = csvEntries;
        this.descriptions = descriptions;
        this.context = context;
    }

    public void setClickListener(ClickListener listener){
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public CatSelectionViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_items_list,parent,false);
        return new CatSelectionViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatSelectionViewHolde holder, int position) {
        final String data = csvEntries.get(position);
        holder.catName.setText(data);
        holder.catImage.setImageResource(icons[position]);
        holder.catDescp.setText(descriptions[position]);

    }

    @Override
    public int getItemCount() {
        return csvEntries.size();
    }

    class CatSelectionViewHolde extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView catImage;
        private TextView catName,catLevel,catDescp;
        private Button catBtn;
        private TextView view;

        public CatSelectionViewHolde(View itemView) {
            super(itemView);

            catImage = itemView.findViewById(R.id.category_image);
            catName = itemView.findViewById(R.id.category_name);
            catDescp = itemView.findViewById(R.id.category_descp);
            catBtn = itemView.findViewById(R.id.btn_start_quiz);
            view = itemView.findViewById(R.id.category_line);
            catBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener!=null){
                clickListener.itemClicked(getLayoutPosition(),csvEntries.get(getAdapterPosition()));
            }
        }
    }
}
