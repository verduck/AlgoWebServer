package com.algo.algoweb.security;

import com.algo.algoweb.util.Base64Util;
import com.algo.algoweb.util.ResourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.*;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class JwtKeyProvider {
    private final ResourceUtil resourceUtil;
    private final Base64Util base64Util;

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @Autowired
    public JwtKeyProvider(final ResourceUtil resourceUtil, final Base64Util base64Util) {
        this.resourceUtil = resourceUtil;
        this.base64Util = base64Util;
    }

    @PostConstruct
    public void init() {
        privateKey = readKey(
          "classpath:keys/rsa-key.pkcs8.private",
          "PRIVATE",
          this::privateKeySpec,
          this::privateKeyGenerator
        );

        publicKey = readKey(
          "classpath:keys/rsa-key.x509.public",
          "PUBLIC",
          this::publicKeySpec,
          this::publicKeyGenerator
        );
    }
 
    private <T extends Key> T readKey(String resourcePath, String headerSpec, Function<String, EncodedKeySpec> keySpec, BiFunction<KeyFactory, EncodedKeySpec, T> keyGenerator) {
        try {
            String keyString = resourceUtil.asString(resourcePath);
            //TODO you can check the headers and throw an exception here if you want
 
            keyString = keyString.replace("-----BEGIN " + headerSpec + " KEY-----", "");
            keyString = keyString.replace("-----END " + headerSpec + " KEY-----", "");
            keyString = keyString.replaceAll("\\s+", "");
 
            return keyGenerator.apply(KeyFactory.getInstance("RSA"), keySpec.apply(keyString));
        } catch(NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("Something went wrong while reading private key!", e);
        }
    }
 
    private EncodedKeySpec privateKeySpec(String data) {
        return new PKCS8EncodedKeySpec(base64Util.decode(data));
    }
 
    private PrivateKey privateKeyGenerator(KeyFactory kf, EncodedKeySpec spec) {
        try {
            return kf.generatePrivate(spec);
        } catch(InvalidKeySpecException e) {
          throw new RuntimeException("Something went wrong while reading private key!", e);
        }
    }

    private EncodedKeySpec publicKeySpec(String data) {
      return new X509EncodedKeySpec(base64Util.decode(data));
    }

    private PublicKey publicKeyGenerator(KeyFactory kf, EncodedKeySpec spec) {
        try {
            return kf.generatePublic(spec);
        } catch(InvalidKeySpecException e) {
            throw new RuntimeException("Something went wrong while reading private key!", e);
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
