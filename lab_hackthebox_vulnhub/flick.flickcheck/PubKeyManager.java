package com.flick.flickcheck;

import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import javax.net.ssl.X509TrustManager;

public final class PubKeyManager implements X509TrustManager {
    static final /* synthetic */ boolean $assertionsDisabled = (!PubKeyManager.class.desiredAssertionStatus());
    private static String PUB_KEY = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b7051e2040155a8e78903e325a8680bd680f0c9cbd164225422a6face762db4da9c7fa11687cc10fc1a20ea1e31260525145d5b18e2692e6e61e0b00d14e78fc62d031cafef90d9dc9599527beae644d1ce0af5b4ec21d405544a1c4a69fc39704e5897791c407f5e77c8bc195be7bcdb6fb30da1f2485d8853c9ce40ebc834e5d7c5c81f052ad03a57921aa940d6b928a0cee39979398e84d9cbf57565109f42f9634db46211f65b89fb9c7375e5a9810c0a89d10b7b6d9301eab716102e35ffe09ae29f764bc2527534e68381306fb7a984c208baa00090b65f4c44d0ace781cd9779130b9e4ea1a54c8bc3c1e9fa31855ebf57f72815775bba604ed6d41290203010001";

    @Override // javax.net.ssl.X509TrustManager
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (!$assertionsDisabled && chain == null) {
            throw new AssertionError();
        } else if (chain == null) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
        } else if (!$assertionsDisabled && chain.length <= 0) {
            throw new AssertionError();
        } else if (chain.length <= 0) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
        } else {
            String encoded = new BigInteger(1, ((RSAPublicKey) chain[0].getPublicKey()).getEncoded()).toString(16);
            boolean expected = PUB_KEY.equalsIgnoreCase(encoded);
            if (!$assertionsDisabled && !expected) {
                throw new AssertionError();
            } else if (!expected) {
                throw new CertificateException("checkServerTrusted: Expected public key: " + PUB_KEY + ", got public key:" + encoded);
            }
        }
    }

    @Override // javax.net.ssl.X509TrustManager
    public void checkClientTrusted(X509Certificate[] xcs, String string) {
    }

    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}