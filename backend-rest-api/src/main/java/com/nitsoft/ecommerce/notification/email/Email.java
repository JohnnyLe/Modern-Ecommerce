package com.nitsoft.ecommerce.notification.email;

import java.util.Set;

public interface Email {

	String getFromAddress();
	
	Set<String> getToAddresses();
	
	Set<String> getCcAddresses();
	
	Set<String> getBccAddresses();
	
	Set<String> getAttachments();
	
	String getSubject();
	
	String getBody();
}