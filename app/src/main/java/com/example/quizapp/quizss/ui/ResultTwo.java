package com.example.quizapp.quizss.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizapp.quizss.R;
import com.example.quizapp.quizss.data.local.model.MarksModel;
import com.example.quizapp.quizss.data.local.model.ParentChildCategory;
import com.example.quizapp.quizss.data.local.providers.DbDao;
import com.example.quizapp.quizss.util.LogUtil;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ResultTwo extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private DbDao dbDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorecard);

        recyclerView = findViewById(R.id.scorecard_recycler_view);
        dbDao = new DbDao(this);


        recyclerAdapter = new RecyclerAdapter(this, dbDao.getAllCategoryFilter(), this, dbDao);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);
    }
}


class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    static List<ParentChildCategory> categoryNameList;
    private ResultTwo activity;
    private static final int PARENT = 0;
    private static final int CHILD = 1;
    private DbDao dbDao;
    private long totalQuestions;



    RecyclerAdapter(Context context, List<ParentChildCategory> categoryNames, ResultTwo activity, DbDao dbDao) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.categoryNameList = categoryNames;
        this.activity = activity;
        this.dbDao = dbDao;
    }


    //Score Dialog


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.activity_scorecard_two, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        totalQuestions = dbDao.getTotalQuestionByCategory(categoryNameList.get(position).getCategoryName());

        holder.categoryNames.setText(categoryNameList.get(position).getCategoryName());
        holder.categoryNames.setOnClickListener(v -> {
            holder.createDialog(activity, dbDao, categoryNameList.get(position).getCategoryName(),totalQuestions);
        });


    }

    @Override
    public int getItemCount() {
        return categoryNameList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private Button categoryNames;
        private TextView easyScore;
        private TextView hardScore;
        private TextView mediumScore;
        private TextView category;
        private TextView totalQuestions;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNames = itemView.findViewById(R.id.categorynames);
        }


        private void createDialog(Activity activity, DbDao dbDao, String categoryName, long totalQ) {


            final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            View view = activity.getLayoutInflater().inflate(R.layout.child_scorecard, null);
            easyScore = view.findViewById(R.id.easy_score);
            mediumScore = view.findViewById(R.id.medium_score);
            hardScore = view.findViewById(R.id.hard_score);
            category = view.findViewById(R.id.category_score);
            totalQuestions = view.findViewById(R.id.total_question_score);

            category.setText(categoryName);
            totalQuestions.setText(String.valueOf(totalQ));

            List<MarksModel> marksData;
            marksData = dbDao.getMarksData(categoryName);
            Log.d("test", "createDialog: " + marksData.size() + " " + categoryName);

            for (int i = 0; i < marksData.size(); i++) {
                setData(marksData.get(i).getDifficulty(), marksData.get(i).getScore());
            }

            DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            builder.setView(view);
            final AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.getWindow().setLayout((6 * width) / 7, (2 * height) / 5);


        }

        private void setData(String categoryName, int score) {
            Log.d("wtf", "setData: " + categoryName);
            String easy = "easy";
            String medium = "medium";
            String hard = "hard";


            switch (categoryName) {
                case "easy":
                    easyScore.setText(String.valueOf(score));

                    break;

                case "medium":
                    mediumScore.setText(String.valueOf(score));

                    break;

                case "hard":
                    hardScore.setText(String.valueOf(score));

                    break;

                default:
                    easyScore.setText("0");
                    mediumScore.setText("0");
                    hardScore.setText("0");


            }


        }

    }

}