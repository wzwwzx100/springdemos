package com.test.mqtt.emq.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.commons.codec.binary.Base64;

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
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

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
		ks.setKeyEntry("private-key", privateKey, password.toCharArray(),
		        new java.security.cert.Certificate[] { clientCert });
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		kmf.init(ks, password.toCharArray());

		// finally, create SSL socket factory
		SSLContext context = SSLContext.getInstance("TLSv1.2");
		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		return context.getSocketFactory();
	}

	// ---------------------------------------------------
	public static SSLSocketFactory getSSLSocktet(String caPath, String crtPath, String keyPath, String password)
	        throws Exception {
		// CA certificate is used to authenticate server
		CertificateFactory cAf = CertificateFactory.getInstance("X.509");
		FileInputStream caIn = new FileInputStream(caPath);
		X509Certificate ca = (X509Certificate) cAf.generateCertificate(caIn);
		KeyStore caKs = KeyStore.getInstance("JKS");
		caKs.load(null, null);
		caKs.setCertificateEntry("ca-certificate", ca);
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
		tmf.init(caKs);

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream crtIn = new FileInputStream(crtPath);
		X509Certificate caCert = (X509Certificate) cf.generateCertificate(crtIn);

		crtIn.close();
		// client key and certificates are sent to server so it can authenticate
		// us
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null, null);
		ks.setCertificateEntry("certificate", caCert);
//		ks.setKeyEntry("private-key", getPrivateKey(keyPath), password.toCharArray(),
//		        new java.security.cert.Certificate[] { caCert });
		
		ByteArrayInputStream bin = new ByteArrayInputStream(Files.readAllBytes(Paths.get(crtPath)));
		X509Certificate clientCert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(bin);
		bin.close();
		
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(keyPath)));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		ks.setKeyEntry("private-key", privateKey, password.toCharArray(),
		        new java.security.cert.Certificate[] { clientCert });
		
		
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
		kmf.init(ks, password.toCharArray());

		// finally, create SSL socket factory
		SSLContext context = SSLContext.getInstance("TLSv1");

		context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());

		return context.getSocketFactory();
	}

	private static String getPem(String path) throws Exception {
		FileInputStream fin = new FileInputStream(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(fin));
		String readLine = null;
		StringBuilder sb = new StringBuilder();
		while ((readLine = br.readLine()) != null) {
			if (readLine.charAt(0) == '-') {
				continue;
			} else {
				sb.append(readLine);
				sb.append('\r');
			}
		}
		fin.close();
		return sb.toString();
	}

	public static PrivateKey getPrivateKey(String path) throws Exception {
		org.apache.commons.codec.binary.Base64 base64 = new Base64();
		byte[] buffer = base64.decode(getPem(path));

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
	}

}
