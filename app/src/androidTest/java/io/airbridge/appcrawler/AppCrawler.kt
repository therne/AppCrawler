package io.airbridge.appcrawler

import android.app.Instrumentation
import android.os.Environment
import android.support.test.uiautomator.*
import android.util.Log
import android.widget.TextView
import org.json.JSONObject
import java.io.File
import java.util.regex.Pattern

/**
 * App Crawler - Gathers app information.
 * Copyright (C) 2016 AB180. All rights are reserved.
 *
 * @author Hyojun Kim
 */
class AppCrawler(instrumentation: Instrumentation) {
    val device = UiDevice.getInstance(instrumentation)
    val screenCap = ScreenCapturer(instrumentation)

    fun launch() {
        val scroller = UiScrollable(UiSelector().scrollable(true))
        scroller.scrollToBeginning(30)
        Thread.sleep(300)

        // get title and desc from top 2 textview
        var title = ""
        var desc = ""
        //        val texts = device.findObjects(By.clazz(Pattern.compile("[\\w]*TextView[\\w]*")))
        val texts = device.findObjects(By.clazz(TextView::class.java))
        for (text in texts) {
            if (text.resourceName.startsWith("com.android.systemui")) continue
            if (text.resourceName.contains("action_bar_title")) continue
            if (title.isEmpty()) title = text.text
            else if (desc.isEmpty()) desc = text.text
            else break
        }

        val metadata = ScreenMetadata(title, desc)

        // has scrollable?
        val hasScrollable = device.hasObject(By.scrollable(true))
        if (hasScrollable) crawlScrollables()
        else screenCap.capture() // first screenshot

        Log.e("AppCrawler", "Crawling completed : $metadata")

        device.dumpWindowHierarchy(File(
                "${Environment.getExternalStorageDirectory()}/crawler/${device.currentPackageName}/dump.txt"))

    }

    fun crawlScrollables() {
        val scroller = UiScrollable(UiSelector().scrollable(true))
        scroller.scrollToBeginning(30)
        Thread.sleep(500)

        var n = 1
        do {
            screenCap.scrollCapture(n++)
            Thread.sleep(400)

        } while (scroller.scrollForward())
    }
}