package fileoperations

import java.io.File

fun String.writeToFile(pathName: String): String {
    File(pathName).bufferedWriter().use { out -> out.write(this) }
    return pathName
}

fun String.openFileInChromeTab() {
    Runtime.getRuntime().exec(
        arrayOf(
            "/usr/bin/open",
            "-a",
            "/Applications/Google Chrome.app",
            this
        )
    )
}