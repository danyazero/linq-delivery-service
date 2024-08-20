package org.zero.npservice.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zero.npservice.exception.AuthException;

@Component
@RequiredArgsConstructor
public class SignatureVerification {
    @Value("${app.security.token}")
    private String secretToken;

    public void verify(String signature, Object object) {
        var compiledSignature = RequestBodyDecoder.compile(object, secretToken);
        System.out.println(signature + " " + compiledSignature);
        if (!signature.equals(compiledSignature)) throw new AuthException("Signature verification failed.");
    }
}
