package com.example.computer.noted;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class NotedActivityTest {

    @Rule
    public ActivityTestRule<NotedActivity> mActivityTestRule = new ActivityTestRule<>(NotedActivity.class);

    @Test
    public void notedActivityTest() {
    }

}
