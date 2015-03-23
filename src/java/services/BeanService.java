/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
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
package services;

import beans.LoggingBean;
import beans.LoginBean;
import beans.MOTDBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@Path("/beans")
@SessionScoped  // The Web Service must be Sessionable or everything else fails
public class BeanService implements Serializable {

    @Inject
    LoginBean login;

    @Inject
    LoggingBean log;

    @Inject
    MOTDBean motd;

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response doPost(JsonObject json) {
        log.log("Name: " + json.toString());
        login.setName(json.getString("name"));
        return Response.ok().build();
    }

    @POST
    @Path("motd")
    @Consumes("application/json")
    @Produces("application/json")
    public Response doPostMotd(JsonObject json) {
        log.log("MOTD: " + json.toString());
        motd.setMotd(json.getString("motd"));
        return Response.ok().build();
    }

    @GET
    @Produces("application/json")
    public Response doGet() {
        JsonObject json = Json.createObjectBuilder()
                .add("name", login.getName())
                .add("motd", motd.getMotd()).build();
        // Cache-Control settings to make sure IE doesn't cache AJAX calls
        CacheControl cc = new CacheControl();
        cc.setMaxAge(0);
        cc.setNoCache(true);
        return Response.ok(json).cacheControl(cc).build();
    }
}
