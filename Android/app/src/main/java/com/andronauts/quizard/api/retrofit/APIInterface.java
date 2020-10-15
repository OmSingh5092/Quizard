package com.andronauts.quizard.api.retrofit;

import com.andronauts.quizard.api.responseModels.faculty.FacultyGetResponse;
import com.andronauts.quizard.api.responseModels.faculty.FacultyUpdateResponse;
import com.andronauts.quizard.api.responseModels.quiz.QuizCreateResponse;
import com.andronauts.quizard.api.responseModels.quiz.QuizListGetResponse;
import com.andronauts.quizard.api.responseModels.signIn.GoogleSignInResponse;
import com.andronauts.quizard.api.responseModels.student.StudentGetResponse;
import com.andronauts.quizard.api.responseModels.student.StudentUpdateResponse;
import com.andronauts.quizard.api.responseModels.subject.SubjectGetResponse;
import com.andronauts.quizard.dataModels.Faculty;
import com.andronauts.quizard.dataModels.Quiz;
import com.andronauts.quizard.dataModels.Student;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface {
    //Google SignIn
    @POST("signin/student")
    Call<GoogleSignInResponse> studentGoogleSignIn(@Body Map<String, String> body);
    @POST("signin/faculty")
    Call<GoogleSignInResponse> facultyGoogleSignIn(@Body Map<String, String> body);

    @POST("student/update")
    Call<StudentUpdateResponse> studentUpdate(@Header("token") String token ,@Body Student student);
    @GET("student/get")
    Call<StudentGetResponse> studentGetProfile(@Header("token") String token);

    @POST("faculty/update")
    Call<FacultyUpdateResponse> facultyUpdate(@Header("token") String token, @Body Faculty faculty);
    @GET("faculty/get")
    Call<FacultyGetResponse> facultyGetProfile(@Header("token") String token);

    @GET("subject/get")
    Call<SubjectGetResponse> getSubject(@Header("id") String id);

    @POST("quiz/create")
    Call<QuizCreateResponse> createQuiz(@Header("token") String token, @Body Quiz quiz);
    @GET("quiz/get/faculty")
    Call<QuizListGetResponse> getQuizByFaculty(@Header("id") String id);
    @GET("quiz/get/subject")
    Call<QuizListGetResponse> getQuizBySubject(@Header("id") String id);

}
