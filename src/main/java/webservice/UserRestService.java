package webservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by gol on 10.07.2017.
 */
@Path("/users")
public class UserRestService {

    @GET
    @Path("{name}")
    public Response getUserByName(@PathParam("name") String name) {

        return Response
                .status(200)
                .entity("getUserByName is called, name : " + name).build();
    }

    @GET
    @Path("{id : \\d+}")    //support digit only
    public Response getUserById(@PathParam("id") String id) {

        return Response
                .status(200)
                .entity("getUserById is called, id : " + id).build();

    }

    @GET
    @Path("/username/{username : [a-zA-Z][a-zA-Z_0-9]}")
    public Response getUserByUserName(@PathParam("username") String username) {

        return Response
                .status(200)
                .entity("getUserByUserName is called, username : " + username).build();

    }

    @GET
    @Path("/books/{isbn : \\d+}")
    public Response getUserBookByISBN(@PathParam("isbn") String isbn) {

        return Response
                .status(200)
                .entity("getUserBookByISBN is called, isbn : " + isbn)
                .build();

    }

    @GET
    @Path("{year}/{month}/{day}")
    public Response getUserHistor(

            @PathParam("year") int year,
            @PathParam("month") int month,
            @PathParam("day") int day) {

        String date = year + "/" + month + "/" + day;

        return Response
                .status(200)
                .entity("getUserHistory called, date : " + date)
                .build();

    }

    @GET
    @Path("/query")
    public Response getUsers(
            @QueryParam("from") int from,
            @QueryParam("to") int to,
            @QueryParam("orderBy") List<String> orderBy) {

        return Response
                .status(200)
                .entity("getUsers is called, from : " + from + ", to : " + to + ", orderBy" + orderBy.toString())
                .build();
    }

    @GET
    @Path("/quary")
    public Response getUsers(@Context UriInfo info) {

        String from = info.getQueryParameters().getFirst("from");
        String to = info.getQueryParameters().getFirst("to");
        List<String> orderBy = info.getQueryParameters().get("orderBy");

        return Response
                .status(200)
                .entity("getUsers is called, from : " + from + ", to : " + to + ", orderBy" + orderBy.toString())
                .build();
    }

}
