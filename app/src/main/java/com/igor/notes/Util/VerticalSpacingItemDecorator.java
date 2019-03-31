package com.igor.notes.Util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpacingHeight;

    public VerticalSpacingItemDecorator(int verticalSpacingHeight) {
        this.verticalSpacingHeight = verticalSpacingHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = verticalSpacingHeight;
    }
}
