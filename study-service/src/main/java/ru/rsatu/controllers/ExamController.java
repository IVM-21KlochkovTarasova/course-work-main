package ru.rsatu.controllers;

import org.eclipse.microprofile.jwt.JsonWebToken;
import ru.rsatu.common.BaseResponse;
import ru.rsatu.dto.AnswerDTO;
import ru.rsatu.dto.ExamDTO;
import ru.rsatu.services.ExamService;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("study/exam")
@Produces("application/json")
@Consumes("application/json")
public class ExamController {

    @Inject
    ExamService examService;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed({"default-roles-prof"})
    public Response startTest(ExamDTO request){
        request.setUserId(jwt.getSubject());
        BaseResponse response = examService.startExam(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("question")
    @RolesAllowed({"default-roles-prof"})
    public Response getQuestion(@QueryParam("testId") Long testId){
        BaseResponse response = examService.getQuestion(testId);
        return Response.ok(response).build();
    }

    @POST
    @Path("answer")
    @RolesAllowed({"default-roles-prof"})
    public Response putAnswer(AnswerDTO request){
        BaseResponse response = examService.putAnswer(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("result")
    @RolesAllowed({"default-roles-prof"})
    public Response getTestResult(@QueryParam("testId") Long testId){
        BaseResponse response = examService.getTestResult(testId);
        return Response.ok(response).build();
    }
}
