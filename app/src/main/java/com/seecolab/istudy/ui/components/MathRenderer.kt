package com.seecolab.istudy.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

/**
 * A composable that renders mathematical expressions using MathJax in a WebView
 */
@Composable
fun MathRenderer(
    mathText: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 16
) {
    val context = LocalContext.current
    
    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
            }
        },
        update = { webView ->
            val htmlContent = createMathJaxHtml(mathText, fontSize)
            webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null)
        }
    )
}

/**
 * Creates HTML content with MathJax for rendering mathematical expressions
 */
private fun createMathJaxHtml(mathText: String, fontSize: Int): String {
    // Convert LaTeX-like syntax to MathJax format
    val processedText = processMathText(mathText)
    
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <script type="text/javascript" async
                src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/3.2.2/es5/tex-mml-chtml.js">
            </script>
            <script type="text/javascript">
                window.MathJax = {
                    tex: {
                        inlineMath: [['$', '$'], ['\\(', '\\)']],
                        displayMath: [['$$', '$$'], ['\\[', '\\]']]
                    },
                    chtml: {
                        scale: 1.0,
                        minScale: 0.5,
                        matchFontHeight: false
                    }
                };
            </script>
            <style>
                body {
                    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                    font-size: ${fontSize}px;
                    line-height: 1.5;
                    margin: 8px;
                    padding: 0;
                    background: transparent;
                    color: #333;
                }
                .math-content {
                    word-wrap: break-word;
                    overflow-wrap: break-word;
                }
            </style>
        </head>
        <body>
            <div class="math-content">$processedText</div>
        </body>
        </html>
    """.trimIndent()
}

/**
 * Processes math text to convert various formats to MathJax-compatible LaTeX
 */
private fun processMathText(text: String): String {
    var processedText = text
    
    // Convert frac{a}{b} to \frac{a}{b} if not already escaped
    processedText = processedText.replace(Regex("(?<!\\\\)frac\\{"), "\\\\frac{")
    
    // Wrap mathematical expressions in $ delimiters for inline math
    // Look for patterns that contain mathematical symbols
    if (processedText.contains("\\frac") || 
        processedText.contains("×") || 
        processedText.contains("÷") ||
        processedText.contains("²") ||
        processedText.contains("³") ||
        processedText.matches(Regex(".*\\d+/\\d+.*"))) {
        
        // Split by common separators and process each part
        val parts = processedText.split(Regex("([，。；,;.])"))
        processedText = parts.joinToString("") { part ->
            val separator = if (parts.indexOf(part) < parts.size - 1) {
                val separatorMatch = Regex("[，。；,;.]").find(processedText, processedText.indexOf(part) + part.length)
                separatorMatch?.value ?: ""
            } else ""
            
            if (part.trim().isNotEmpty() && containsMath(part)) {
                "$${part.trim()}$" + separator
            } else {
                part + separator
            }
        }
    }
    
    // Replace Chinese mathematical symbols with LaTeX equivalents
    processedText = processedText.replace("×", "\\times")
    processedText = processedText.replace("÷", "\\div")
    
    // Handle superscripts
    processedText = processedText.replace("²", "^2")
    processedText = processedText.replace("³", "^3")
    
    return processedText
}

/**
 * Checks if text contains mathematical expressions
 */
private fun containsMath(text: String): Boolean {
    return text.contains("\\frac") || 
           text.contains("×") || 
           text.contains("÷") ||
           text.contains("²") ||
           text.contains("³") ||
           text.matches(Regex(".*\\d+/\\d+.*")) ||
           text.matches(Regex(".*\\d+\\s*[=<>+\\-*/]\\s*\\d+.*"))
}