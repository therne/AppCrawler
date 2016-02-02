package io.airbridge.appcrawler;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LaunchCrawler {

    @Before
    public void setUp() {
    }

    @Test
    public void launchCrawler() {
        try {
            AppCrawler crawler = new AppCrawler(InstrumentationRegistry.getInstrumentation());
            crawler.launch();
        } catch (Exception e) {
            Log.e("AppCrawler", "Error occurred while crawling", e);
        }
    }

    @After
    public void tearDown() {

    }
}