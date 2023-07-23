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

import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import static com.nimbusds.jwt.JWTClaimsSet.parse;
import com.nimbusds.jwt.SignedJWT;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import static net.minidev.json.parser.JSONParser.DEFAULT_PERMISSIVE_MODE;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author Gabriel Jijón gjijon@gmail.com
 */
@Path("/public")
@RequestScoped
public class MensajesJWTResource {

    @GET
    @Path("/autenticar/{nombre}/{grupo}")
    @Produces({MediaType.APPLICATION_JSON})
    public String autenticarUsuarioJWT(
            @PathParam("nombre") String name,
            @PathParam("grupo") String grupo) throws Exception {
        byte[] byteBuffer = new byte[16384];
        Thread.currentThread().getContextClassLoader()
                .getResource("/base.json")
                .openStream()
                .read(byteBuffer);

        JSONParser parser = new JSONParser(DEFAULT_PERMISSIVE_MODE);
        JSONObject jwtJson = (JSONObject) parser.parse(byteBuffer);

        long currentTimeInSecs = (System.currentTimeMillis() / 1000);
        long expirationTime = currentTimeInSecs + 90;

        jwtJson.put(Claims.iat.name(), currentTimeInSecs);
        jwtJson.put(Claims.auth_time.name(), currentTimeInSecs);
        jwtJson.put(Claims.exp.name(), expirationTime);
        jwtJson.put(Claims.sub.name(), name);
        //Usando jwtJson.put con Claims.groups.name() se pueden adicionar mas grupos
        jwtJson.put(Claims.groups.name(), grupo);
        
        SignedJWT signedJWT = new SignedJWT(new JWSHeader.Builder(RS256)
                .keyID("/privateKey.pem")
                .type(JWT)
                .build(), parse(jwtJson));

        signedJWT.sign(new RSASSASigner(readPrivateKey("privateKey.pem")));

        return signedJWT.serialize();
    }

    public static PrivateKey readPrivateKey(String resourceName) throws Exception {
        byte[] byteBuffer = new byte[16384];
        int length = Thread.currentThread().getContextClassLoader()
                .getResource(resourceName)
                .openStream()
                .read(byteBuffer);

        String key = new String(byteBuffer, 0, length).replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
    }
}
