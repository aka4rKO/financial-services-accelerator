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

package org.wso2.financial.services.accelerator.identity.extensions.claims.policy;

import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.financial.services.accelerator.identity.extensions.util.IdentityCommonConstants;

import java.util.Map;

/**
 * Policy to update the "sub" claim with or without the tenant/user store domain
 */
public class UpdateSubJWTAccessTokenOIDCClaimsHandlerPolicy extends FSJWTAccessTokenOIDCClaimsHandlerPolicy {

    @Override
    public void handleCustomClaims(OAuthTokenReqMessageContext tokReqMsgCtx,
                                   Map<String, Object> userClaimsInOIDCDialect, Map<String, Object> propertyMap) {

        // TODO: get from config
        Boolean removeTenantDomain = true;
        Boolean removeUserStoreDomain = true;

        if (removeTenantDomain || removeUserStoreDomain) {
            String subClaim = tokReqMsgCtx.getAuthorizedUser()
                    .getUsernameAsSubjectIdentifier(!removeUserStoreDomain, !removeTenantDomain);
            userClaimsInOIDCDialect.put(IdentityCommonConstants.SUBJECT_CLAIM, subClaim);
        }
    }
}
