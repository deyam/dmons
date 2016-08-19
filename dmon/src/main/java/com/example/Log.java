package com.example;

import org.springframework.data.annotation.Id;

public class Log {

	@Id private String id;

	private String log1Msg;
	private String log2Msg;

	public String getLog1Msg() {
		return log1Msg;
	}

	public void setLog1Msg(String log1Msg) {
		this.log1Msg = log1Msg;
	}

	public String getLog2Msg() {
		return log2Msg;
	}

	public void setLastName(String log2Msg) {
		this.log2Msg = log2Msg;
	}
}
