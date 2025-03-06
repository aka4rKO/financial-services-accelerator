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

import org.apache.commons.lang3.StringUtils;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.financial.services.accelerator.identity.extensions.util.IdentityCommonConstants;

import java.util.Arrays;
import java.util.Map;

/**
 * Policy to append consent ID JWT claim to access token (specifically for user access tokens)
 */
public class AppendConsentIdJWTAccessTokenOIDCClaimsHandlerPolicy extends FSJWTAccessTokenOIDCClaimsHandlerPolicy {

    @Override
    public void handleCustomClaims(OAuthTokenReqMessageContext tokReqMsgCtx,
                                   Map<String, Object> userClaimsInOIDCDialect, Map<String, Object> propertyMap) {

        // TODO: use config
        String consentIdClaimName = "consent_id";

        String consentID = Arrays.stream(tokReqMsgCtx.getScope())
                .filter(scope -> scope.contains(IdentityCommonConstants.FS_PREFIX)).findFirst().orElse(null);
        if (StringUtils.isEmpty(consentID)) {
            consentID = Arrays.stream(tokReqMsgCtx.getScope())
                    .filter(scope -> scope.contains(consentIdClaimName))
                    .findFirst().orElse(StringUtils.EMPTY)
                    .replaceAll(consentIdClaimName, StringUtils.EMPTY);
        } else {
            consentID = consentID.replace(IdentityCommonConstants.FS_PREFIX, StringUtils.EMPTY);
        }

        if (StringUtils.isNotEmpty(consentID)) {
            userClaimsInOIDCDialect.put(consentIdClaimName, consentID);
        }
    }
}
