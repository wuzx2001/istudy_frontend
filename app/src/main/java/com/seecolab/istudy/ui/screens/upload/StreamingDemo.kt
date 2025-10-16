package com.seecolab.istudy.ui.screens.upload

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Demo composable showing how the ChatGPT-like streaming works
 * This can be used for testing the streaming functionality
 */
@Composable
fun StreamingDemoScreen() {
    var streamingText by remember { mutableStateOf("") }
    var isStreaming by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    
    val demoText = """
        Based on the analysis of your paper work, here are the detailed results:
        
        🎯 **Overall Score: 85/100**
        
        📊 **Question Analysis:**
        1. Question 1: ✅ Correct (Full marks)
        2. Question 2: ✅ Correct (Full marks) 
        3. Question 3: ❌ Incorrect - Minor calculation error
        4. Question 4: ✅ Correct (Full marks)
        5. Question 5: ⚠️ Partially correct - Right method, wrong final answer
        
        💡 **Suggestions for Improvement:**
        - Pay attention to arithmetic calculations in Question 3
        - Double-check your final answers before submitting
        - Your problem-solving approach is excellent, keep it up!
        
        📚 **Recommended Practice:**
        - Practice similar problems in Chapter 5
        - Review fraction simplification rules
        - Work on mental math exercises
        
        Keep up the good work! You're showing great progress in mathematics.
    """.trimIndent()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Streaming Demo",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    streamingText = ""
                    isStreaming = true
                    // Simulate streaming with proper coroutine scope
                    coroutineScope.launch {
                        demoText.forEachIndexed { index, char ->
                            streamingText = demoText.substring(0, index + 1)
                            delay(20) // Adjust speed here
                        }
                        isStreaming = false
                    }
                },
                enabled = !isStreaming
            ) {
                Text("Start Streaming")
            }
            
            OutlinedButton(
                onClick = {
                    streamingText = ""
                    isStreaming = false
                }
            ) {
                Text("Reset")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Show the streaming content card
        if (streamingText.isNotEmpty() || isStreaming) {
            EnhancedStreamingContentCard(
                content = streamingText,
                isStreaming = isStreaming
            )
        }
    }
}

/**
 * Enhanced StreamingContentCard with better performance and features
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancedStreamingContentCard(
    content: String,
    isStreaming: Boolean,
    modifier: Modifier = Modifier,
    onCopy: (() -> Unit)? = null,
    maxHeight: androidx.compose.ui.unit.Dp = 400.dp
) {
    val scrollState = rememberScrollState()
    
    // Auto-scroll to bottom when new content arrives
    LaunchedEffect(content.length) {
        if (content.isNotEmpty() && isStreaming) {
            scrollState.animateScrollTo(scrollState.maxValue)
        }
    }
    
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.SmartToy,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "AI Analysis",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Show typing indicator when streaming
                    if (isStreaming) {
                        TypingIndicator()
                    }
                    
                    // Copy button
                    onCopy?.let {
                        IconButton(
                            onClick = it,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ContentCopy,
                                contentDescription = "Copy content",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Content area with scroll and selection
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = maxHeight)
                    .verticalScroll(scrollState)
            ) {
                SelectionContainer {
                    Column {
                        Text(
                            text = content,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
                        )
                        
                        // Animated cursor when streaming
                        if (isStreaming) {
                            BlinkingCursor()
                        }
                    }
                }
            }
            
            // Status bar
            if (isStreaming || content.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isStreaming) "Generating..." else "Analysis complete",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "${content.length} characters",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Animated blinking cursor for streaming text
 */
@Composable
fun BlinkingCursor() {
    var visible by remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            visible = !visible
        }
    }
    
    Text(
        text = if (visible) "▌" else " ",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

/**
 * Simple typing indicator for demonstration
 */
@Composable
fun TypingIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            var alpha by remember { mutableStateOf(0.3f) }
            
            LaunchedEffect(Unit) {
                while (true) {
                    delay(index * 200L)
                    alpha = 1f
                    delay(600)
                    alpha = 0.3f
                    delay(600)
                }
            }
            
            Text(
                text = "•",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
            )
            
            if (index < 2) Spacer(modifier = Modifier.width(2.dp))
        }
    }
}