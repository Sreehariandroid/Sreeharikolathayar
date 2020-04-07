package com.example.myapplication.loaddataapp.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.loaddataapp.model.ItemElement;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
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
        MutableLiveData<List<ItemElement>> itemElementList = mock(MutableLiveData.class);

        when(mainViewModel.getItemsList()).thenReturn(itemElementList);

        MutableLiveData<List<ItemElement>> actualElementList = mainViewModel.getItemsList();

        assertEquals(actualElementList, itemElementList);
    }
}