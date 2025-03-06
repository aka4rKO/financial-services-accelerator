/**
 * Copyright (c) 2025, WSO2 LLC. (https://www.wso2.com).
 * <p>
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 *     http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api;

import org.json.JSONObject;
import org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api.model.Attribute;
import org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api.model.Operation;
import org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api.model.PreIssueTokenResponse;
import org.wso2.financial.services.accelerator.identity.service.extensions.endpoint.api.utils.ConsentUtils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/pre-issue-access-token")
public class PreIssueAccessTokenEndpoint {

    /**
     * Pre issue access token.
     */
    @POST
    @Consumes({"application/json; charset=utf-8"})
    @Produces({"application/json; charset=utf-8"})
    public Response managePost(@Context HttpServletRequest httpServletRequest,
                               @Context HttpServletResponse httpServletResponse,
                               @Context UriInfo uriInfo) {

        // TODO: make the endpoint to use https for the action

        JSONObject requestPayload = (JSONObject) ConsentUtils.getPayload(httpServletRequest);

//        JSONObject event = (JSONObject) requestPayload.get("event");
//        JSONObject request = (JSONObject) event.get("request");
//        String grantType = request.getString("grantType");

        PreIssueTokenResponse response = new PreIssueTokenResponse();
        response.setActionStatus("SUCCESS");
        List<Operation> operationList = new ArrayList<>();

        Operation operation = new Operation();
        operation.setOp("add");
        operation.setPath("/accessToken/claims/");

        Attribute attribute = new Attribute();
        attribute.setName("customSID");
        attribute.setValue("12345");
        operation.setAttribute(attribute);

        operationList.add(operation);
        response.setOperations(operationList);


        String responsePayload = "{\n" +
                "  \"actionStatus\": \"SUCCESS\",\n" +
                "  \"operations\": [\n" +
                "    {\n" +
                "      \"op\": \"add\",\n" +
                "      \"path\": \"/accessToken/claims/-\",\n" +
                "      \"value\": {\n" +
                "        \"name\": \"customSID\",\n" +
                "        \"value\": \"12345\"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        return Response.status(200).entity(responsePayload).build();
    }

}
