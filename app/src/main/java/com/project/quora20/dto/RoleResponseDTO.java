package com.project.quora20.dto;

import com.google.gson.annotations.SerializedName;

public class RoleResponseDTO{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"RoleResponseDTO{" + 
			"success = '" + success + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}