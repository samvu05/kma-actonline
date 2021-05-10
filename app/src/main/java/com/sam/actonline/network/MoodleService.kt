package com.sam.actonline.network

import com.sam.actonline.model.AuthResponse
import com.sam.actonline.model.Site
import com.sam.actonline.utils.Constant
import retrofit2.Call
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


//    /**
//     * Get all the user courses
//     */
//    @GET("webservice/rest/server.php?wsfunction=core_enrol_get_users_courses")
//    fun getCourses(@Query("userid") id: Int): Single<List<Course>>
//
//    /**
//     * Get course information
//     * param
//     */
//    @GET("webservice/rest/server.php?wsfunction=core_course_get_contents")
//    fun getCourseContent(@Query("courseid") courseID: Int): Single<List<Section>>

}