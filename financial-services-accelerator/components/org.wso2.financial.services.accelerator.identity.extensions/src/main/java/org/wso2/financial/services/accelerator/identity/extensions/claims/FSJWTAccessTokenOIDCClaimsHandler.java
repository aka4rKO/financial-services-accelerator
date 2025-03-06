/**
 * Copyright (c) 2024, WSO2 LLC. (https://www.wso2.com).
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

package org.wso2.financial.services.accelerator.identity.extensions.claims;

import com.nimbusds.jwt.JWTClaimsSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.RequestObjectException;
import org.wso2.carbon.identity.oauth2.token.OAuthTokenReqMessageContext;
import org.wso2.carbon.identity.openidconnect.JWTAccessTokenOIDCClaimsHandler;
import org.wso2.financial.services.accelerator.common.util.FinancialServicesUtils;
import org.wso2.financial.services.accelerator.common.util.Generated;
import org.wso2.financial.services.accelerator.identity.extensions.claims.policy.AppendConsentIdJWTAccessTokenOIDCClaimsHandlerPolicy;
import org.wso2.financial.services.accelerator.identity.extensions.claims.policy.FSJWTAccessTokenOIDCClaimsHandlerPolicy;
import org.wso2.financial.services.accelerator.identity.extensions.claims.policy.UpdateSubJWTAccessTokenOIDCClaimsHandlerPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This handler adds FS specific additional claims to self-contained JWT access token.
 */
public class FSJWTAccessTokenOIDCClaimsHandler extends JWTAccessTokenOIDCClaimsHandler {

    private static Log log = LogFactory.getLog(FSJWTAccessTokenOIDCClaimsHandler.class);

    @Override
    public JWTClaimsSet handleCustomClaims(JWTClaimsSet.Builder jwtClaimsSetBuilder, OAuthTokenReqMessageContext
            tokenReqMessageContext) throws IdentityOAuth2Exception {

        try {
            if (FinancialServicesUtils.isRegulatoryApp(tokenReqMessageContext.getOauth2AccessTokenReqDTO()
                    .getClientId())) {

                Map<String, Object> userClaimsInOIDCDialect = new HashMap<>();
                JWTClaimsSet jwtClaimsSet = getJwtClaimsFromSuperClass(jwtClaimsSetBuilder, tokenReqMessageContext);
                if (jwtClaimsSet != null) {
                    userClaimsInOIDCDialect.putAll(jwtClaimsSet.getClaims());
                }

                List<FSJWTAccessTokenOIDCClaimsHandlerPolicy> policies = new ArrayList<>();

                policies.add(new AppendConsentIdJWTAccessTokenOIDCClaimsHandlerPolicy());
                policies.add(new UpdateSubJWTAccessTokenOIDCClaimsHandlerPolicy());

                for (FSJWTAccessTokenOIDCClaimsHandlerPolicy policy : policies) {
                    policy.handleCustomClaims(tokenReqMessageContext, userClaimsInOIDCDialect, policy.getPropertyMap());
                }

                for (Map.Entry<String, Object> claimEntry : userClaimsInOIDCDialect.entrySet()) {
                    jwtClaimsSetBuilder.claim(claimEntry.getKey(), claimEntry.getValue());
                }
                return jwtClaimsSetBuilder.build();
            }
        } catch (RequestObjectException e) {
            log.error("Error while handling custom claims", e);
            throw new IdentityOAuth2Exception(e.getMessage(), e);
        }
        return super.handleCustomClaims(jwtClaimsSetBuilder, tokenReqMessageContext);
    }

    @Generated(message = "Excluding from code coverage since it makes is used to return claims from the super class")
    public JWTClaimsSet getJwtClaimsFromSuperClass(JWTClaimsSet.Builder jwtClaimsSetBuilder,
                                                   OAuthTokenReqMessageContext tokenReqMessageContext)
            throws IdentityOAuth2Exception {

        return super.handleCustomClaims(jwtClaimsSetBuilder, tokenReqMessageContext);
    }
}
