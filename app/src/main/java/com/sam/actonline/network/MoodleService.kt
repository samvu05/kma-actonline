package com.sam.actonline.network

import com.sam.actonline.model.AuthResponse
import com.sam.actonline.model.BaseBadge
import com.sam.actonline.model.ItemCourse
import com.sam.actonline.model.coursedetail.ItemSection
import com.sam.actonline.model.Site
import com.sam.actonline.model.event.BaseEvent
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dinh Sam Vu on 4/14/2021.
 */
interface MoodleService {

    //    Authentication
    @GET("login/token.php?service=moodle_mobile_app")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<AuthResponse>

    //     Get site information, {mostly just for getting the userId}
    @GET("webservice/rest/server.php?wsfunction=core_webservice_get_site_info")
    suspend fun getSite(): Response<Site>

    //    Get all the user courses
    @GET("webservice/rest/server.php?wsfunction=core_enrol_get_users_courses")
    suspend fun getCourses(@Query("userid") id: Int): Response<List<ItemCourse>>

    //    Get course information
    @GET("webservice/rest/server.php?wsfunction=core_course_get_contents")
    suspend fun getCourseContent(@Query("courseid") courseID: Int): Response<List<ItemSection>>

    //    Get event by ID
    @GET("webservice/rest/server.php?wsfunction=core_calendar_get_calendar_event_by_id")
    suspend fun getEventByID(@Query("eventid") eventid: Int): Response<BaseEvent>

    //    Get upcoming events
    @GET("webservice/rest/server.php?wsfunction=core_calendar_get_calendar_upcoming_view")
    suspend fun getUpcomingEvent(): Response<BaseEvent>

    //    Get event by Day
    @GET("webservice/rest/server.php?wsfunction=core_calendar_get_calendar_day_view")
    suspend fun getEventDayView(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int
    ): Response<BaseEvent>

    //    Get event by month
    @GET("webservice/rest/server.php?wsfunction=core_calendar_get_calendar_monthly_view")
    suspend fun getEventMonthView(
        @Query("year") year: Int,
        @Query("month") month: Int,
    ): Response<BaseEvent>


    @GET("webservice/rest/server.php?wsfunction=core_badges_get_user_badges")
    suspend fun getListBadges(): Response<BaseBadge>

}