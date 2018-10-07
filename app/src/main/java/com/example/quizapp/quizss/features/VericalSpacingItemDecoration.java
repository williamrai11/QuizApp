package com.example.quizapp.quizss.features;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class VericalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VericalSpacingItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.d("count", "getItemOffsets: "+parent.getAdapter().getItemCount());

        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount()-1){
            outRect.bottom = verticalSpaceHeight;

        }
    }
}
