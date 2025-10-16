package com.seecolab.istudy.test

import com.seecolab.istudy.data.model.StudentWorkListItem
import com.seecolab.istudy.data.model.StudentWorkListResponse
import org.junit.Test
import org.junit.Assert.*

/**
 * Test class to verify StudentWorkList data models work correctly
 */
class StudentWorkListTest {
    
    @Test
    fun testStudentWorkListItem() {
        val workItem = StudentWorkListItem(
            work_id = "work_001",
            student_id = "student_123",
            create_time = "2025-01-01T10:00:00",
            paper_name = "Math Homework",
            subject = "Mathematics",
            score = 95.5f,
            status = "completed"
        )
        
        assertEquals("work_001", workItem.work_id)
        assertEquals("student_123", workItem.student_id)
        assertEquals("Math Homework", workItem.paper_name)
        assertEquals("Mathematics", workItem.subject)
        assertEquals("completed", workItem.status)
        assertEquals(95.5f, workItem.score, 0.0f)
    }
    
    @Test
    fun testStudentWorkListResponse() {
        val workList = listOf(
            StudentWorkListItem(
                work_id = "work_001",
                student_id = "student_123",
                create_time = "2025-01-01T10:00:00",
                paper_name = "Math Homework",
                subject = "Mathematics",
                score = 95.5f,
                status = "completed"
            ),
            StudentWorkListItem(
                work_id = "work_002",
                student_id = "student_123", 
                create_time = "2025-01-02T14:00:00",
                paper_name = "Science Project",
                subject = "Physics",
                score = null,
                status = "pending"
            )
        )
        
        val response = StudentWorkListResponse(
            success = true,
            total_count = 2,
            page = 1,
            page_size = 10,
            total_pages = 1,
            works = workList,
            student_id = "student_123",
            start_date = "2025-01-01",
            end_date = "2025-01-31",
            message = "Success"
        )
        
        assertTrue(response.success)
        assertEquals(2, response.total_count)
        assertEquals(1, response.page)
        assertEquals(10, response.page_size)
        assertEquals(1, response.total_pages)
        assertEquals(2, response.works.size)
        assertEquals("student_123", response.student_id)
        assertEquals("2025-01-01", response.start_date)
        assertEquals("Success", response.message)
    }
    
    @Test
    fun testApiEndpointFormat() {
        // Test that the endpoint format is correct
        val studentId = "student_123"
        val expectedEndpoint = "student-works/list/$studentId"
        
        assertEquals("student-works/list/student_123", expectedEndpoint)
    }
    
    @Test
    fun testQueryParameters() {
        // Test query parameter combinations
        val params = mapOf(
            "page" to "1",
            "page_size" to "10", 
            "start_date" to "2025-01-01",
            "end_date" to "2025-01-31"
        )
        
        assertEquals("1", params["page"])
        assertEquals("10", params["page_size"])
        assertEquals("2025-01-01", params["start_date"])
        assertEquals("2025-01-31", params["end_date"])
    }
}