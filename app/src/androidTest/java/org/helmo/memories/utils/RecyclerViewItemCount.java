package org.helmo.memories.utils;


import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;

import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.is;

import org.helmo.memories.R;
import org.junit.Assert;

public class RecyclerViewItemCount implements ViewAssertion {

    private final Matcher<Integer> matcher;

    public RecyclerViewItemCount(int expectedCount) {
        this.matcher = is(expectedCount);
    }


    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if(noViewFoundException != null) {
            throw noViewFoundException;
        }

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        Assert.assertThat(adapter.getItemCount(), matcher);
    }
}
