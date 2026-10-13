package com.infy.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceRequest;
import com.infy.service.MobileService;
import com.infy.service.MobileServiceImpl;

public class MobileServiceTest {

    private MobileService mobileService= new MobileServiceImpl();
    @Test
	public void registerRequestInvalidBrandTest() {
		//your code goes here
    	ServiceRequest service = new ServiceRequest(
    	        "abc",
    	        Arrays.asList("Battery"),
    	        9876765487L,
    	        "John",
    	        1234567890123456L
    	    );

    	    MobileServiceException exception = assertThrows(
    	        MobileServiceException.class,
    	        () -> mobileService.registerRequest(service)
    	    );

    	    assertEquals(
    	        "Validator.INVALID_BRAND",
    	        exception.getMessage()
    	    );
	}
     
    @Test
	public void registerRequestInvalidContactNumberTest() {
		//your code goes here
    	ServiceRequest service = new ServiceRequest(
                "Twoplusone",
                Arrays.asList("Battery"),
                78757L,
                "John",
                1234567890123456L
        );

        MobileServiceException exception = assertThrows(
                MobileServiceException.class,
                () -> mobileService.registerRequest(service)
        );

        assertEquals(
                "Validator.INVALID_CONTACT_NUMBER",
                exception.getMessage()
        );
	}
    @Test
	public void registerRequestInvalidIssuesTest() {
		//your code goes here
		 ServiceRequest service = new ServiceRequest(
	                "Twoplusone",
	                Arrays.asList("Broken screen"),
	                9876765487L,
	                "John",
	                1234567890123456L
	        );

	        MobileServiceException exception = assertThrows(
	                MobileServiceException.class,
	                () -> mobileService.registerRequest(service)
	        );

	        assertEquals(
	                "Service.INVALID_ISSUES",
	                exception.getMessage()
	        );
	}

}
