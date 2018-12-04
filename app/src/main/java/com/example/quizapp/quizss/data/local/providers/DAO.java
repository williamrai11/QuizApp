package com.example.quizapp.quizss.data.local.providers;

import com.example.quizapp.quizss.data.local.model.InsertOneCsv;
import com.example.quizapp.quizss.data.local.model.MarksModel;
import com.example.quizapp.quizss.data.local.model.ParentChildCategory;
import com.example.quizapp.quizss.data.local.model.QueryOneCsv;

import java.util.List;

public interface DAO {

     List<String> getAllCategory();
     List<ParentChildCategory> getAllCategoryFilter();
     List<QueryOneCsv> getAllDatas(String category,String difficulty,long part);
     void insert(InsertOneCsv insertOneCsv);
     boolean checkForDataInTrackerDb(String category);
     List<InsertOneCsv> getDatasFromTracker(String category);
     void updateTracker(InsertOneCsv insertOneCsv);
    void insert(MarksModel marksModel);
    void insert(List<QueryOneCsv> datas,String categoryName);
    List<QueryOneCsv> getQuestionData(int id,String categoryName);
    List<Integer> getDatasId(String categoryName);
    int getCountOfQuestions(String categoryName,String level,long part);
    int getPart(String categoryname,String level);
    boolean checkForQuestions(String categoryname,String level);
    List<MarksModel> getMarksData(String categoryName);
    long getTotalQuestionByCategory(String categoryName);


}
