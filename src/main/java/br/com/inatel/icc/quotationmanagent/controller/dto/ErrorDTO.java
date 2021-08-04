package br.com.inatel.icc.quotationmanagent.controller.dto;

public class ErrorDTO {

	private String field;
	private String message;

	public ErrorDTO(String field, String message) {
		this.field = field;
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}

}
