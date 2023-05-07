package kt.kotlin.activity

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object RootUtils {
    fun isDeviceRooted(): Boolean {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3()
    }

    private fun checkRootMethod1(): Boolean {
        val buildTags = android.os.Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )

        for (path in paths) {
            if (File(path).exists()) {
                return true
            }
        }

        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null

        try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            return reader.readLine() != null
        } catch (t: Throwable) {
            return false
        } finally {
            process?.destroy()
        }
    }
}

object SystemPropertyUtils {
    fun isSystemPropertyModified(propertyName: String): Boolean {
        try {
            val process = Runtime.getRuntime().exec("getprop $propertyName")
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val output = reader.readLine()

            if (output != null) {
                return output.contains("[" + propertyName + "]" + " has been modified")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }
}