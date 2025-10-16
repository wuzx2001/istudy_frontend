package com.seecolab.istudy.utils

/**
 * Test functions for debugging mathematical text processing
 */
object MathTextDebug {
    
    fun testMathProcessing() {
        val testInputs = listOf(
            "\$frac{2}{3}=frac{2×()}{3×()}=frac{6}{9}\$",
            "\$frac{6}{9}=frac{6÷()}{9÷()}=frac{2}{3}\$",
            "\$frac{12}{20}=frac{12÷()}{20÷()}=frac{()}{5}\$",
            "\$frac{7}{9}=frac{7×()}{9×()}=frac{()}{63}\$"
        )
        
        testInputs.forEach { input ->
            val processed = preprocessMathTextDebug(input)
            println("Input: $input")
            println("Output: $processed")
            println("---")
        }
    }
    
    private fun preprocessMathTextDebug(text: String): String {
        var processed = text
        
        // Convert $frac{a}{b}$ to \frac{a}{b} (server format)
        //processed = processed.replace(Regex("\\$frac\\{([^}]+)\\}\\{([^}]+)\\}\\$?"), "\\\\frac{$1}{$2}")
        
        // Convert frac{a}{b} to \frac{a}{b} if not already escaped
        //processed = processed.replace(Regex("(?<!\\\\)frac\\{"), "\\\\frac{")
        
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
}