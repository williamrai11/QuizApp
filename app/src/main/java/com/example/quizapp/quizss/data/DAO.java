package com.example.quizapp.quizss.data;

import com.example.quizapp.quizss.data.model.Datas;
import com.example.quizapp.quizss.data.model.InsertOneCsv;
import com.example.quizapp.quizss.data.model.MarksModel;
import com.example.quizapp.quizss.data.model.QueryOneCsv;

import java.util.List;

public interface DAO {

    public List<String> getAllCategory();
    public List<QueryOneCsv> getAllDatas(String category);
    public void insert(InsertOneCsv insertOneCsv);
    public boolean checkForDataInTrackerDb(String category);
    public List<InsertOneCsv> getDatasFromTracker(String category);
    public void updateTracker(InsertOneCsv insertOneCsv);
    void insert(MarksModel marksModel);
    void insert(List<QueryOneCsv> datas,String categoryName);
    List<QueryOneCsv> getQuestionData(int id,String categoryName);
    List<Integer> getDatasId(String categoryName);
}
