package io.airbridge.appcrawler

import android.app.Instrumentation
import android.os.Environment
import android.support.test.uiautomator.UiDevice
import android.util.Log
import java.io.File

/**
 * Captures device screen and
 * saves the screenshot into specified path.
 *
 * @author Hyojun Kim
 */
class ScreenCapturer(instrumentation: Instrumentation) {
    val device = UiDevice.getInstance(instrumentation)
    val captureDir = "${Environment.getExternalStorageDirectory()}/crawler/${device.currentPackageName}"

    /**
     * Take a screenshot and save it into <external dir>/crawler/<package name>/capture.png
     */
    fun capture() = takeScreenshot("capture")

    /**
     * Used when capturing scrollable views.
     * @param index screenshot file name suffix.
     */
    fun scrollCapture(index: Int) = takeScreenshot("capture-$index")

    internal fun takeScreenshot(filename: String) {
        // make directory if not exists
        val dir = File(captureDir)
        if (!dir.exists()) dir.mkdirs()

        // capture
        val file = File("$captureDir/$filename.png")
        val succeed = device.takeScreenshot(file)
        if (!succeed) Log.e("AppCrawler", "Failed to take screenshot $filename!")
        else Log.e("AppCrawler", "Captured $filename")
    }
}
