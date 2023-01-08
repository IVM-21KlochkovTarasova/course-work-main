package ru.rsatu.services;

import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import ru.rsatu.common.RequestJWTHeaderFactory;
import ru.rsatu.dto.OrderDTO;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/contract")
@RegisterRestClient
@RegisterClientHeaders(RequestJWTHeaderFactory.class)
public interface OrderServiceExtension {

    @GET
    OrderDTO getForStartExam(@QueryParam("userId") String userId,
                             @QueryParam("studyProgramId") Long studyProgramId,
                             @QueryParam("isComplete") Boolean isComplete);

    @GET
    @Path("user_contracts")
    List<OrderDTO> getByUserId(@QueryParam("userId") String userId);

    @GET
    @Path("all")
    List<OrderDTO> getAllContracts(@QueryParam("pageNumber") Integer pageNumber,
                                   @QueryParam("pageSize") Integer pageSize);

    @PUT
    @Path("complete")
    Response setComplete(OrderDTO orderDTO);

}
