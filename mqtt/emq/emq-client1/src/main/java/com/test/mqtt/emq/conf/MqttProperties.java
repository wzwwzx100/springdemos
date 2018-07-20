package com.test.mqtt.emq.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "com.mqtt")
public class MqttProperties {

	private String[] hosts;

	private String clientIdSend;

	private String clientIdRecive;

	private boolean cleanSession;

	private String defaultTopic;

	private boolean async;

	private String username;

	private String password;

	private int connectionTimeout;

	private int keepalive;

	private int completionTimeout;

	private String[] sendTopics;

	private String[] reciveTopics;

	private String caCertificate;

	private String certificate;

	private String privateKey;

	private String keyPass;

	private String protocol;
	
	private String keyStore;
	
	private String keyStorePassword;
	
	private String keyStoreType;
	
	private String trustStore;
	
	private String trustStorePassword;
	
	private String trustStoreType;

	public String[] getHosts() {
		return hosts;
	}

	public void setHosts(String[] hosts) {
		this.hosts = hosts;
	}

	public String getClientIdSend() {
		return clientIdSend;
	}

	public void setClientIdSend(String clientIdSend) {
		this.clientIdSend = clientIdSend;
	}

	public String getClientIdRecive() {
		return clientIdRecive;
	}

	public void setClientIdRecive(String clientIdRecive) {
		this.clientIdRecive = clientIdRecive;
	}

	public boolean isCleanSession() {
		return cleanSession;
	}

	public void setCleanSession(boolean cleanSession) {
		this.cleanSession = cleanSession;
	}

	public String getDefaultTopic() {
		return defaultTopic;
	}

	public void setDefaultTopic(String defaultTopic) {
		this.defaultTopic = defaultTopic;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getKeepalive() {
		return keepalive;
	}

	public void setKeepalive(int keepalive) {
		this.keepalive = keepalive;
	}

	public int getCompletionTimeout() {
		return completionTimeout;
	}

	public void setCompletionTimeout(int completionTimeout) {
		this.completionTimeout = completionTimeout;
	}

	public String[] getSendTopics() {
		return sendTopics;
	}

	public void setSendTopics(String[] sendTopics) {
		this.sendTopics = sendTopics;
	}

	public String[] getReciveTopics() {
		return reciveTopics;
	}

	public void setReciveTopics(String[] reciveTopics) {
		this.reciveTopics = reciveTopics;
	}

	public String getCaCertificate() {
		return caCertificate;
	}

	public void setCaCertificate(String caCertificate) {
		this.caCertificate = caCertificate;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getKeyPass() {
		return keyPass;
	}

	public void setKeyPass(String keyPass) {
		this.keyPass = keyPass;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getKeyStore() {
		return keyStore;
	}

	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyStoreType() {
		return keyStoreType;
	}

	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	public String getTrustStore() {
		return trustStore;
	}

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public String getTrustStorePassword() {
		return trustStorePassword;
	}

	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	public String getTrustStoreType() {
		return trustStoreType;
	}

	public void setTrustStoreType(String trustStoreType) {
		this.trustStoreType = trustStoreType;
	}

}
