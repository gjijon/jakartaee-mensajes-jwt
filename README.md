# jakartaee-mensajes-jwt
Ejemplo de código con Java 17 y Jakarta EE 10

El proyecto cuenta con 3 servicios:

1. Generador de JWT basado en RS256. Este servicio es público (sin autorización) en la siguiente URL: https://[ip]:[port]/jakartaee-mensajes-jwt/api/public/autenticar/[Subject]/[Role]
   1. Los roles de este sistema son AUT y REG
   2. En este proyecto se encuentran dos archivos de clave asimétrica en formato PEM.
   3. El servicio firma con la clave privada (privateKey.pem) y comprueba con la clave pública (publicKey.pem)
   4. Un token válido (aunque caducado) es:  eyJraWQiOiIvcHJpdmF0ZUtleS5wZW0iLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiJsb2NhbGhvc3QiLCJzdWIiOiJHYWJyaWVsIiwidXBuIjoicHJvZHVjdGlvbiIsImF1dGhfdGltZSI6MTY5MDEzMTE2NCwiaXNzIjoiand0LnBlcnNvbmFsIiwiZ3JvdXBzIjoiUkVHIiwiZXhwIjoxNjkwMTMxMjU0LCJpYXQiOjE2OTAxMzExNjQsImp0aSI6ImEtMTIzIn0.fdblQiSNTtBqyYR9opmSACgNZAM_bxfnFY6O9HeXUvLnyS0p5qEnMk2Lz0TYt0kaUfswRA65M95-r4A6W5AYBI6KS9NFcnPEG67CG02WWA3oJU2Ld7-ZIUyKBP5B50OHpxsJ2KxErhfsdaFPiikgv3QgnEJX5Mkx9quMfSq2SY4kt_J6FGsL9MFlUgbvnSl2w6AaPCO09zewpuIxb4GHw6wTV53glmGh4h5lm6lKSMZ8Uf56EDg9J1qkEzmz5B0tBzIwj45V0Mb19AzuH7_5u2mHe_3Q7IvyicJ8OtXFwoH_EzD_E38DMXpvXLrBqIm4_H-kW0kh9xAezyIjqwPGYw
2. Servicio GET para AUT en la URL http[s]://[ip]:[port]/jakartaee-mensajes-jwt/api/private/info/aut
3. Servicio GET para REG en la URL http[s]://[ip]:[port]/jakartaee-mensajes-jwt/api/private/info/reg
4. Un ejemplo de consumo es:
curl -H 'Accept: application/json' -H "Authorization: Bearer [TOKEN GENERADO CON PASO 1]" http[s]://[ip]:[port]/jakartaee-mensajes-jwt/api/private/info/aut

Documentación:
https://docs.payara.fish/community/docs/Technical%20Documentation/MicroProfile/JWT.html
https://jwt.io/
https://download.eclipse.org/microprofile/microprofile-jwt-auth-2.0/microprofile-jwt-auth-spec-2.0.html#_incompatible_changes
