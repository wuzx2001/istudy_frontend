package com.seecolab.istudy.utils

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

/**
 * Utility for processing and displaying mathematical text content
 */
object MathTextProcessor {
    
    /**
     * Processes mathematical text and returns formatted AnnotatedString
     */
    @Composable
    fun processText(text: String): AnnotatedString {
        return buildAnnotatedString {
            var currentIndex = 0
            val processedText = preprocessMathText(text)
            
            // Find and format mathematical expressions
            val mathPattern = Regex("\\\\frac\\{([^}]+)\\}\\{([^}]+)\\}")
            val matches = mathPattern.findAll(processedText).toList()
            
            if (matches.isEmpty()) {
                // No math expressions, just format as regular text with some enhancements
                append(formatRegularText(processedText))
            } else {
                // Process text with math expressions
                for (match in matches) {
                    // Add text before the math expression
                    if (match.range.first > currentIndex) {
                        append(formatRegularText(processedText.substring(currentIndex, match.range.first)))
                    }
                    
                    // Add the fraction in a more readable format
                    val numerator = match.groupValues[1]
                    val denominator = match.groupValues[2]
                    
                    // Format fraction with enhanced styling
                    append("(") 
                    pushStyle(SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ))
                    append(numerator)
                    pop()
                    
                    append("/")
                    
                    pushStyle(SpanStyle(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ))
                    append(denominator)
                    pop()
                    append(")")
                    
                    currentIndex = match.range.last + 1
                }
                
                // Add remaining text
                if (currentIndex < processedText.length) {
                    append(formatRegularText(processedText.substring(currentIndex)))
                }
            }
        }
    }
    
    /**
     * Preprocesses mathematical text to normalize formatting
     */
    private fun preprocessMathText(text: String): String {
        var processed = text
        
        // Convert $frac{a}{b}$ to \frac{a}{b} (server format)
        processed = processed.replace(Regex("\\\$frac\\{([^}]+)\\}\\{([^}]+)\\}\\\$?"), "\\\\frac{$1}{$2}")
        
        // Convert frac{a}{b} to \frac{a}{b} if not already escaped
        processed = processed.replace(Regex("(?<!\\\\)frac\\{"), "\\\\frac{")
        
        // Replace mathematical symbols with more readable equivalents
        processed = processed.replace("×", "×")
        processed = processed.replace("÷", "÷")
        
        // Handle parentheses in mathematical expressions
        processed = processed.replace("（", "(")
        processed = processed.replace("）", ")")
        
        // Handle Chinese punctuation
        processed = processed.replace("，", ", ")
        processed = processed.replace("。", ". ")
        
        return processed
    }
    
    /**
     * Formats regular text with some basic styling for mathematical content
     */
    @Composable
    private fun formatRegularText(text: String): AnnotatedString {
        return buildAnnotatedString {
            var currentIndex = 0
            val content = text
            
            // Highlight numbers and mathematical operators
            val numberPattern = Regex("\\d+")
            val operatorPattern = Regex("[+\\-×÷=()\\[\\]]")
            
            // Find all numbers and operators
            val numberMatches = numberPattern.findAll(content).toList()
            val operatorMatches = operatorPattern.findAll(content).toList()
            val allMatches = (numberMatches + operatorMatches).sortedBy { it.range.first }
            
            for (match in allMatches) {
                // Add text before the match
                if (match.range.first > currentIndex) {
                    append(content.substring(currentIndex, match.range.first))
                }
                
                // Style the match
                when {
                    match.value.matches(Regex("\\d+")) -> {
                        // Style numbers
                        pushStyle(SpanStyle(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        ))
                        append(match.value)
                        pop()
                    }
                    match.value.matches(Regex("[+\\-×÷=]")) -> {
                        // Style operators
                        pushStyle(SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        ))
                        append(" ${match.value} ")
                        pop()
                    }
                    else -> {
                        // Style parentheses and brackets
                        pushStyle(SpanStyle(
                            fontWeight = FontWeight.Medium
                        ))
                        append(match.value)
                        pop()
                    }
                }
                
                currentIndex = match.range.last + 1
            }
            
            // Add remaining text
            if (currentIndex < content.length) {
                append(content.substring(currentIndex))
            }
        }
    }
}

/**
 * Composable for displaying mathematical text with enhanced formatting
 */
@Composable
fun MathText(
    text: String,
    modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier
) {
    SelectionContainer {
        Text(
            text = MathTextProcessor.processText(text),
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier,
            lineHeight = 22.sp
        )
    }
}