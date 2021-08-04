package br.com.inatel.icc.quotationmanagent.controller.dto;

import java.io.Serializable;

public class NotificationDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String host;
	private int port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
