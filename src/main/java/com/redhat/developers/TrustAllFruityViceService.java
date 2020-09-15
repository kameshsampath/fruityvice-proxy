package com.redhat.developers;

import java.net.Socket;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.ws.rs.Produces;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

@ApplicationScoped
public class TrustAllFruityViceService {

  @Produces
  @Named("trust-all")
  public FruityViceService fruityViceService() {
    FruityViceService fruityViceService = RestClientBuilder.newBuilder()
        .baseUri(URI.create("https://www.fruityvice.com/"))
        .hostnameVerifier(NoopHostnameVerifier.INSTANCE)
        .sslContext(trustEverything())
        .build(FruityViceService.class);
    return fruityViceService;
  }

  private static SSLContext trustEverything() {

    try {
      SSLContext sc = SSLContext.getInstance("SSL");
      sc.init(null, trustAllCerts(), new java.security.SecureRandom());
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      return sc;
    } catch (KeyManagementException | NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
  }

  private static TrustManager[] trustAllCerts() {
    return new TrustManager[] {
        new X509ExtendedTrustManager() {

          @Override
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }

          @Override
          public void checkServerTrusted(X509Certificate[] chain,
              String authType)
              throws CertificateException {}

          @Override
          public void checkClientTrusted(X509Certificate[] chain,
              String authType)
              throws CertificateException {}

          @Override
          public void checkServerTrusted(X509Certificate[] chain,
              String authType,
              SSLEngine sslEngine)
              throws CertificateException {}

          @Override
          public void checkServerTrusted(X509Certificate[] chain,
              String authType,
              Socket socket)
              throws CertificateException {}

          @Override
          public void checkClientTrusted(X509Certificate[] chain,
              String authType,
              SSLEngine sslEngine)
              throws CertificateException {}

          @Override
          public void checkClientTrusted(X509Certificate[] chain,
              String authType,
              Socket socket)
              throws CertificateException {}
        }
    };
  }
}
