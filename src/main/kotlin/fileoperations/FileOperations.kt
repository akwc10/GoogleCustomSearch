package fileoperations

import java.io.File

fun writeToFile(pathName: String, fileContent: String) {
    File(pathName).bufferedWriter().use { out -> out.write(fileContent) }
}

fun openFileInChrome(pathName: String) {
    Runtime.getRuntime().exec(
        arrayOf(
            "/usr/bin/open",
            "-a",
            "/Applications/Google Chrome.app",
            pathName
        )
    )
}