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
	
	private String[] topics;
	
	private String caCertificate;
	
	private String certificate;
	
	private String privateKey;

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

	public String[] getTopics() {
		return topics;
	}

	public void setTopics(String[] topics) {
		this.topics = topics;
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

}
