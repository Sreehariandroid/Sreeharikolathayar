package com.example.myapplication.loaddataapp.adapter;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.*;

public class ListDataAdapterTest {
private ListDataAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new ListDataAdapter();
    }

    @Test
    public void getItemCount_emptyList_returnZero() {
       // adapter.setListItems(Collections.emptyList());
        assertTrue(adapter.getItemCount() == 0);
    }

    @Test
    public void onBindViewHolder() {
    }

    @Test
    public void getItemCount() {
    }

    @Test
    public void setRowItems() {
    }
}