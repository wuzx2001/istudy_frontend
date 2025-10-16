package com.seecolab.istudy.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

/**
 * KaTeX-based math renderer using WebView without external template files
 */
@Composable
fun KaTeXMathView(
    mathText: String,
    modifier: Modifier = Modifier,
    textColor: String = "#333333",
    fontSize: Int = 16
) {
    val context = LocalContext.current
    var webView by remember { mutableStateOf<WebView?>(null) }
    
    AndroidView(
        modifier = modifier.heightIn(min = 60.dp),
        factory = { ctx ->
            WebView(ctx).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        // Wait for KaTeX to load completely
                        view?.postDelayed({
                            renderMathWithKaTeX(view, mathText, textColor, fontSize)
                        }, 400)
                    }
                }
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = false
                    allowContentAccess = true
                    allowFileAccess = true
                }
                webView = this
            }
        },
        update = { view ->
            webView = view
            // Load KaTeX directly in HTML without external files
            view.loadDataWithBaseURL(
                null,
                createKaTeXHTML(textColor, fontSize),
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}

/**
 * Creates self-contained HTML with KaTeX from CDN
 */
private fun createKaTeXHTML(textColor: String, fontSize: Int): String {
    return """
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/katex@0.16.8/dist/katex.min.css">
    <script src="https://cdn.jsdelivr.net/npm/katex@0.16.8/dist/katex.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/katex@0.16.8/dist/contrib/auto-render.min.js"></script>
    <style>
        body {
            margin: 0;
            padding: 15px;
            background: transparent;
            color: $textColor;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            font-size: ${fontSize}px;
            line-height: 1.6;
            word-wrap: break-word;
            overflow-wrap: break-word;
        }
        .math-content {
            min-height: 30px;
        }
        .katex {
            font-size: 1em !important;
        }
        .katex-display {
            margin: 0.5em 0 !important;
        }
    </style>
</head>
<body>
    <div id="math-content" class="math-content">Loading...</div>
    <script>
        console.log('KaTeX HTML loaded');
        
        window.renderMath = function(mathText) {
            try {
                const element = document.getElementById('math-content');
                if (element && mathText) {
                    element.innerHTML = mathText;
                    
                    // Configure KaTeX auto-render
                    renderMathInElement(element, {
                        delimiters: [
                            {left: '\\\\(', right: '\\\\)', display: false},
                            {left: '\\\\[', right: '\\\\]', display: true},
                            {left: '$$', right: '$$', display: true},
                            {left: '$', right: '$', display: false}
                        ],
                        throwOnError: false,
                        errorColor: '#cc0000',
                        strict: false
                    });
                    console.log('KaTeX rendering completed');
                } else {
                    console.log('Element or mathText not found');
                }
            } catch (error) {
                console.error('KaTeX rendering error:', error);
                const element = document.getElementById('math-content');
                if (element) {
                    element.innerHTML = mathText || 'Math rendering failed';
                }
            }
        };
    </script>
</body>
</html>
    """.trimIndent()
}

/**
 * Renders math content using KaTeX
 */
private fun renderMathWithKaTeX(
    webView: WebView,
    mathText: String,
    textColor: String,
    fontSize: Int
) {
    // Preserve LaTeX format and escape only for JavaScript string literal
    val escapedText = mathText
        .replace("'", "\\'")
        .replace("\"", "\\\"")
        .replace("\n", "<br>")
        .replace("\r", "")
        // Don't double-escape backslashes - preserve the LaTeX format
    
    val javascript = """
        if (typeof renderMath === 'function') {
            renderMath('$escapedText');
        } else {
            console.error('renderMath function not available');
        }
    """.trimIndent()
    
    webView.evaluateJavascript(javascript) { result ->
        // Optional: handle result
    }
}

/**
 * Enhanced math display component with better styling
 */
@Composable
fun EnhancedKaTeXDisplay(
    text: String,
    modifier: Modifier = Modifier,
    isLightTheme: Boolean = true
) {
    val textColor = if (isLightTheme) "#333333" else "#ffffff"
    
    KaTeXMathView(
        mathText = text,
        modifier = modifier.fillMaxWidth(),
        textColor = textColor,
        fontSize = 16
    )
}