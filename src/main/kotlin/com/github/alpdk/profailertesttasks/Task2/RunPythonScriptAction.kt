package com.github.alpdk.profailertesttasks.task2

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.editor.EditorFactory

import java.io.File

class RunPythonScriptAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        // Taking selected text
        val selectedText = getSelectedText()
        if (selectedText != null) {
            try {
                // Calculating lengths of strings
                val pythonOutput = runPythonScript(selectedText)
                Messages.showMessageDialog(
                    "Python Output:\n$pythonOutput",
                    "Python Script Result",
                    Messages.getInformationIcon()
                )
            } catch (e: Exception) {
                Messages.showErrorDialog("Error running Python script: ${e.message}", "Error")
            }
        } else {
            Messages.showMessageDialog("No text selected", "Warning", Messages.getWarningIcon())
        }
    }

    private fun getSelectedText(): String? {
        // Take text from selected field and return it
        val editor = EditorFactory.getInstance().allEditors.firstOrNull()
        return editor?.caretModel?.currentCaret?.selectedText
    }

    private fun runPythonScript(inputText: String): String {
        // Get the resource URL for the Python script inside the JAR
        val scriptUrl = this.javaClass.classLoader.getResource("script.py")
        if (scriptUrl == null) {
            throw IllegalArgumentException("Resource not found: script.py")
        }

        // Create a temporary file to store the extracted script
        val tempScriptFile = File.createTempFile("script", ".py")
        scriptUrl.openStream().use { input ->
            tempScriptFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        tempScriptFile.deleteOnExit()  // Ensure the temp file is deleted when the JVM exits

        // Get the absolute path of the extracted Python script
        val scriptPath = tempScriptFile.absolutePath

        // Use the script path in ProcessBuilder to run the Python script
        val processBuilder = ProcessBuilder("python3", scriptPath, inputText)
        processBuilder.redirectErrorStream(true)

        // Start the process and capture output
        val process = processBuilder.start()
        val output = process.inputStream.bufferedReader().use { it.readText() }
        val exitCode = process.waitFor()

        // Check if the Python script ran successfully
        if (exitCode != 0) {
            throw RuntimeException("Python script failed with exit code $exitCode")
        }

        return output.trim()
    }
}
