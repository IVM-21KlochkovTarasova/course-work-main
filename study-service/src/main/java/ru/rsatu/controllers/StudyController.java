package ru.rsatu.controllers;


import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import ru.rsatu.common.BaseResponse;
import ru.rsatu.dto.LkProgramDTO;
import ru.rsatu.models.StudyProgram;
import ru.rsatu.services.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("study")
@Produces("application/json")
@Consumes("application/json")
public class StudyController {

    @Inject
    StudyService studyService;

    @Inject
    JsonWebToken jwt;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StudyController.class);

    @GET
    @Path("programs")
    public Response getProgramsByCourseId(@QueryParam("courseId") Long courseId){
        LOGGER.info("fddfgdfg");
        List<StudyProgram> studyPrograms = studyService.getProgramsByCourseId(courseId);
        return Response.ok(studyPrograms).build();
    }

    @GET
    @Path("courses")
    public Response getCourses(@QueryParam("studySectionId") Long studySectionId){
        BaseResponse response = studyService.getCourse(studySectionId);
        return Response.ok(response).build();
    }


    @GET
    @Path("section")
    @Counted(name = "helloCount", description = "Количество запросов к ресурсу hello")
    @Timed(name = "helloTimer", description = "Вычисляется, как долго выполнялся метод.", unit = MetricUnits.MILLISECONDS)
    public Response getStudySections(){
        BaseResponse response = studyService.getSections();
        return Response.ok(response).build();
//        System.out.println("Username: " + keycloakSecurityContext.getPrincipal().getName() + " ; Roles: ");
//        keycloakSecurityContext.getRoles().forEach(System.out::println);
//        LOG.info("Some useful log message");
    }

    @GET
    @Path("user_programs")
    @RolesAllowed({"default-roles-prof"})
    public Response getUserPrograms(){
        List<LkProgramDTO> studyPrograms = studyService.getProgramsByUserId(jwt.getSubject());
        return Response.ok(studyPrograms).build();
    }

    @GET
    @Path("literature")
    @RolesAllowed({"default-roles-prof"})
    public Response getLiterature(@QueryParam("studyProgramId") Long studyProgramId, @QueryParam("filter") String filter){
        return Response.ok(studyService.getLiterature(studyProgramId, filter)).build();
    }

    @GET
    @Path("question")
    @RolesAllowed({"default-roles-prof"})
    public Response getQuestion(@QueryParam("studyProgramId") Long studyProgramId){
        return Response.ok(studyService.getQuestion(studyProgramId)).build();
    }

    @GET
    @Path("answers")
    @RolesAllowed({"default-roles-prof"})
    public Response getAnswers(@QueryParam("questionId") Long questionId){
        return Response.ok(studyService.getAnswers(questionId)).build();
    }
}
