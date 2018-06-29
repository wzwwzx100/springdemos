package com.test.mqtt.emq.util;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class SslUtil {
	
	public static SSLSocketFactory getSocketFactory(final String caCrtFile, final String crtFile, final String keyFile,
	        final String password) throws Exception {
		
		// load CA certificate
		ByteArrayInputStream bin = new ByteArrayInputStream(Files.readAllBytes(Paths.get(caCrtFile)));
		X509Certificate caCert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(bin);
		bin.close();
		
		// load client certificate
		bin = new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtFile)));
		X509Certificate clientCert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(bin);
		bin.close();
		
		// load client private key
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(keyFile)));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate (keySpec);
		
		// CA certificate is used to authenticate server
		KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", caCert);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		tmf.init(caKs);
		
		// client key and certificates are sent to server so it can authenticate
		// us
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);
		ks.setCertificateEntry("certificate", clientCert);
		ks.setKeyEntry("private-key",privateKey, password.toCharArray(),
		        new java.security.cert.Certificate[] { clientCert });
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, password.toCharArray());
		
		// finally, create SSL socket factory
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		return context.getSocketFactory();
	}
	
}
