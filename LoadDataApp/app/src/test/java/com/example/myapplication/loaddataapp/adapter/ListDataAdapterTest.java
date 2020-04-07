package com.example.myapplication.loaddataapp.adapter;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.loaddataapp.model.ItemElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class ListDataAdapterTest {
private ListDataAdapter adapter;

    @Before
    public void setUp() throws Exception {
        Context context = mock(Context.class);
        adapter = new ListDataAdapter(context);
    }

    @Test
    public void getItemCount_emptyList_shouldReturnZero() {
        assertEquals(0, adapter.getItemCount());
    }

    @Test
    public void getItemCount_setOneItem_shouldReturnCorrectSize() {
        ItemElement itemElement = new ItemElement();
        List<ItemElement> itemList = new ArrayList<>();
        itemList.add(itemElement);

        adapter.setListItems(itemList);

        assertEquals(1, adapter.getItemCount());
    }
}