/*
 * Copyright 2023 Gabriel Jijón gjijon@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package online.topdoc.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.stream.Collectors;
import online.topdoc.dto.DTOMensaje;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author Gabriel Jijón gjijon@gmail.com
 */
@Path("/private")
@RequestScoped
public class MensajesPrivateResource {

    @Inject
    private JsonWebToken token;

    @GET
    @Path("/info/aut")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("AUT")
    public Response getAdminInfo() throws Exception {
        return Response
                .ok()
                .entity(
                        new DTOMensaje(
                        token.getSubject(), 
                        token.getGroups()
                                .stream()
                                .collect(
                                        Collectors.joining(",")
                                )
                        )
                )
                .build();
    }

    @GET
    @Path("/info/reg")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("REG")
    public Response getREGInfo() throws Exception {
        return Response
                .ok()
                .entity(
                        new DTOMensaje(
                        token.getSubject(), 
                        token.getGroups()
                                .stream()
                                .collect(
                                        Collectors.joining(",")
                                )
                        )
                )
                .build();
    }
}
