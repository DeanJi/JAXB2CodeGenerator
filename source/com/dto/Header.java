package com.dto;

public class Header {
	private String Version;
	private String CreateDateTime;
	private String SenderCompId;
	private String TargetCompId;

	private String ServiceName = "ServiceName";
	private String MsgLanguage = "MsgLanguage";

	public String getVersion() {
		return Version;
	}

	public void setVersion(String version) {
		Version = version;
	}

	public String getCreateDateTime() {
		return CreateDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		CreateDateTime = createDateTime;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public String getMsgLanguage() {
		return MsgLanguage;
	}

	public void setMsgLanguage(String msgLanguage) {
		MsgLanguage = msgLanguage;
	}

	public String getSenderCompId() {
		return SenderCompId;
	}

	public void setSenderCompId(String senderCompId) {
		SenderCompId = senderCompId;
	}

	public String getTargetCompId() {
		return TargetCompId;
	}

	public void setTargetCompId(String targetCompId) {
		TargetCompId = targetCompId;
	}

}
