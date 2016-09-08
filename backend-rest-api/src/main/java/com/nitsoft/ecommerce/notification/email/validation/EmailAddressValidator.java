package com.nitsoft.ecommerce.notification.email.validation;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailAddressValidator {

	/**
	 * Checks if an e-mail address is valid.
	 * 
	 * @param emailAddress
	 *            The address to validate.
	 * @return <b>true</b> if the address is valid, <b>false</b> otherwise.
	 */
	public boolean validate(String emailAddress) {
		if (emailAddress == null) {
			return false;
		}

		try {
			// Validate using Java Mail API
			// Don't know if this is the best way, but it's good enough for now
			new InternetAddress(emailAddress, true);
		} catch (AddressException e) {
			return false;
		}

		return true;
	}
}
