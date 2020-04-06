package com.example.myapplication.loaddataapp.viewmodel;

import com.example.myapplication.loaddataapp.model.ItemElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MainViewModelTest {
     @Mock
     private MainViewModel mainViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getTitle_returnCorrectString() {
        String expectedTitle = "test title";
        when(mainViewModel.getTitle()).thenReturn(expectedTitle);

        String actualTitle = mainViewModel.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void getItemList_returnCorrectItemsList() {
        ItemElement itemElement = new ItemElement();
        List<ItemElement> itemList = new ArrayList<>();
        itemList.add(itemElement);

        when(mainViewModel.getItemList()).thenReturn(itemList);

        List actualList = mainViewModel.getItemList();

        assertEquals(actualList, itemList);
    }
}