package com.infy.validator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceRequest;
public class Validator {
	private static final Logger LOGGER =
	        LogManager.getLogger(Validator.class);

	public void validate(ServiceRequest service) throws MobileServiceException{	
		//your code goes here
		try {
		if(!isValidBrand(service.getBrand())) {
			throw new MobileServiceException("Validator.INVALID_BRAND");
		}
		if(!isValidIssues(service.getIssues())) {
			throw new MobileServiceException("Validator.INVALID_NO_OF_ISSUES");
		}
		if(!isValidIMEINumber(service.getiMEINumber())) {
			throw new MobileServiceException("Validator.INVALID_IMEI_NUMBER");
		}

		if(!isValidContactNumber(service.getContactNumber())) {
			throw new MobileServiceException("Validator.INVALID_CONTACT_NUMBER");
		}
		if(!isValidCustomerName(service.getCustomerName())) {
			throw new MobileServiceException("Validator.INVALID_CUSTOMER_NAME");
		}
	  }catch(MobileServiceException e){
		  LOGGER.error(e.getMessage(),e);
		  throw e;
	  }
		
	}	

	
	// validates the brand
	// brand should always start with a upper case alphabet 
	// and can be followed by one or more alphabets (lower case or upper case) 
	public Boolean isValidBrand(String brand){
		 return brand != null
	                && brand.matches("[A-Z][a-zA-Z]+");
	}
	
	
	// validates the list of issues
	// checks if the list is null or empty
	public Boolean isValidIssues(List<String> issues) {
		return issues != null && !issues.isEmpty();
	}

	// validates the IMEINumber
	// it should be a 16 digit number 
	public Boolean isValidIMEINumber(Long iMEINumber) {
		return iMEINumber != null
                && iMEINumber.toString().matches("\\d{16}");
	}
	
	// validates the contact number
	// should contain 10 numeric characters and should not contain 10 repetitive characters
	public Boolean isValidContactNumber(Long contactNumber) {
		if (contactNumber == null) {
            return false;
        }

        String number = contactNumber.toString();

        return number.matches("\\d{10}")
                && !number.matches("(.)\\1{9}");
	}
	
	
	// validates the customer name
	// should contain at least one word and each word separated by a single space should contain at least one letter.
	// the first letter of every word should be an upper case character 
	public Boolean isValidCustomerName(String customerName) {
		
		 return customerName != null
	                && customerName.matches(
	                    "[A-Z][a-z]+( [A-Z][a-z]+)*");
	}
}
