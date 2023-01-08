package ru.rsatu.controllers;

import ru.rsatu.common.BaseResponse;
import ru.rsatu.dto.*;
import ru.rsatu.services.ProgramManageService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("manage")
@Produces("application/json")
@Consumes("application/json")
public class ProgramManageController {

    @Inject
    ProgramManageService programManageService;

    @POST
    @Path("studySection")
    @RolesAllowed({"admin"})
    public Response addStudySection(StudySectionDTO request) {
        BaseResponse response = programManageService.addSection(request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("studySection")
    @RolesAllowed({"admin"})
    public Response deleteStudySection(StudySectionDTO request)  {
        BaseResponse response = programManageService.deleteSection(request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("course")
    @RolesAllowed({"admin"})
    public Response deleteCourses(CourseDTO request)  {
        BaseResponse response = programManageService.deleteCourse(request);
        return Response.ok(response).build();
    }

    @PUT
    @Path("section")
    @RolesAllowed({"admin"})
    public Response editSection(StudySectionDTO request){
        BaseResponse response = programManageService.editSection(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("course")
    @RolesAllowed({"admin"})
    public Response addCourse(CourseDTO request){
        BaseResponse response = programManageService.addCourse(request);
        return Response.ok(response).build();
    }

    @PUT
    @Path("course")
    @RolesAllowed({"admin"})
    public Response editCourse(CourseDTO request){
        BaseResponse response = programManageService.editCourse(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("answers")
    @RolesAllowed({"admin", "razrab"})
    public Response getAnswers(@QueryParam("questionId") Long questionId){
        return Response.ok(programManageService.getAnswers(questionId)).build();
    }

    @POST
    @Path("program")
    @RolesAllowed({"admin"})
    public Response addProgram(StudyProgramDTO request){
        BaseResponse response = programManageService.addProgram(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("question")
    @RolesAllowed({"admin", "razrab"})
    public Response addQuestion(QuestionDTO request){
        BaseResponse response = programManageService.addQuestion(request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("question")
    @RolesAllowed({"admin", "razrab"})
    public Response deleteQuestion(QuestionDTO request){
        BaseResponse response = programManageService.deleteQuestion(request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("program")
    @RolesAllowed({"admin"})
    public Response deleteProgram(StudyProgramDTO request){
        BaseResponse response = programManageService.deleteProgram(request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("literature")
    @RolesAllowed({"admin", "razrab"})
    public Response deleteLiterature(StudyProgramLiteratureDTO request){
        BaseResponse response = programManageService.deleteLiterature(request);
        return Response.ok(response).build();
    }

    @PUT
    @Path("question")
    @RolesAllowed({"admin", "razrab"})
    public Response editQuestion(QuestionDTO request){
        BaseResponse response = programManageService.editQuestion(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("questions")
    @RolesAllowed({"admin", "razrab"})
    public Response getQuestions(@QueryParam("studyProgramId") Long studyProgramId){
        return Response.ok(programManageService.getQuestion(studyProgramId)).build();
    }

    @PUT
    @Path("program")
    @RolesAllowed({"admin", "razrab"})
    public Response editProgram(StudyProgramDTO request){
        BaseResponse response = programManageService.editPrograms(request);
        return Response.ok(response).build();
    }

    @POST
    @Path("literature")
    @RolesAllowed({"admin", "razrab"})
    public Response addLiterature(StudyProgramLiteratureDTO request){
        BaseResponse response = programManageService.addLiterature(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("statistics")
    @RolesAllowed({"admin", "razrab"})
    public Response getStatistics(@QueryParam("pageNumber") Integer pageNumber,
                                  @QueryParam("pageSize") Integer pageSize){
        return Response.ok(programManageService.getStatistic(pageNumber, pageSize)).build();
    }
}
