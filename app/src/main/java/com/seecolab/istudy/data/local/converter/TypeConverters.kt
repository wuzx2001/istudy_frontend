package com.seecolab.istudy.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.seecolab.istudy.data.model.Subject
import com.seecolab.istudy.data.model.AnalysisResult
import com.seecolab.istudy.data.model.DetectedQuestion
import com.seecolab.istudy.data.model.StudyPlan

class TypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSubjectList(subjects: List<Subject>): String {
        return gson.toJson(subjects)
    }

    @TypeConverter
    fun toSubjectList(data: String): List<Subject> {
        val listType = object : TypeToken<List<Subject>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromAnalysisResult(result: AnalysisResult?): String? {
        return result?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAnalysisResult(data: String?): AnalysisResult? {
        return data?.let { gson.fromJson(it, AnalysisResult::class.java) }
    }

    @TypeConverter
    fun fromDetectedQuestionList(questions: List<DetectedQuestion>): String {
        return gson.toJson(questions)
    }

    @TypeConverter
    fun toDetectedQuestionList(data: String): List<DetectedQuestion> {
        val listType = object : TypeToken<List<DetectedQuestion>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromStudyPlan(plan: StudyPlan): String {
        return gson.toJson(plan)
    }

    @TypeConverter
    fun toStudyPlan(data: String): StudyPlan {
        return gson.fromJson(data, StudyPlan::class.java)
    }

    @TypeConverter
    fun fromStringList(strings: List<String>): String {
        return gson.toJson(strings)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromLongList(longs: List<Long>): String {
        return gson.toJson(longs)
    }

    @TypeConverter
    fun toLongList(data: String): List<Long> {
        val listType = object : TypeToken<List<Long>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromStringStringMap(map: Map<String, String>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toStringStringMap(data: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(data, mapType)
    }

    @TypeConverter
    fun fromLongStringMap(map: Map<Long, String>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toLongStringMap(data: String): Map<Long, String> {
        val mapType = object : TypeToken<Map<Long, String>>() {}.type
        return gson.fromJson(data, mapType)
    }

    @TypeConverter
    fun fromSubjectFloatMap(map: Map<Subject, Float>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun toSubjectFloatMap(data: String): Map<Subject, Float> {
        val mapType = object : TypeToken<Map<Subject, Float>>() {}.type
        return gson.fromJson(data, mapType)
    }
}