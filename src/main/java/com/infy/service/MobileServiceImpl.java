package com.infy.service;

import java.time.LocalDateTime;
import java.util.List;
import  java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.infy.dao.MobileServiceDAO;
import com.infy.dao.MobileServiceDAOImpl;
import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceReport;
import com.infy.model.ServiceRequest;
import com.infy.model.Status;
import com.infy.validator.Validator;

public class MobileServiceImpl implements MobileService{
	private static final Logger LOGGER=
			LogManager.getLogger(MobileServiceImpl.class);
	private MobileServiceDAO dao=new MobileServiceDAOImpl();
	
	private Validator validator=new Validator();
	
	@Override
	public ServiceRequest registerRequest(ServiceRequest service) throws MobileServiceException {
		try {
		validator.validate(service);
		float fee=calculateEstimateCost(service.getIssues());
		if(fee<=0) {
			throw new MobileServiceException("Service.INVALID_ISSUES");
		}
		service.setServiceFee(fee);
		service.setStatus(Status.ACCEPTED);
		service.setTimeOfRequest(LocalDateTime.now());
		return dao.registerRequest(service);
		
		}
		catch(MobileServiceException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Float calculateEstimateCost(List<String> issues) throws MobileServiceException {
		Float total = 0.0f;
	    if (issues == null) {
	        return total;
	    }
	    for (String issue : issues) {
	        if (issue == null) {
	            continue;
	        }
	        if (issue.equalsIgnoreCase("Battery")) {
	            total += 10.0f;
	        } else if (issue.equalsIgnoreCase("Camera")) {
	            total += 5.0f;
	        } else if (issue.equalsIgnoreCase("Screen")) {
	            total += 15.0f;
	        }
	       
	      }
	    return total;
	}

	@Override
	public List<ServiceReport> getServices(Status status) throws MobileServiceException {
		try {
		List<ServiceReport>reports=dao.getServices().stream()
				.filter(request->request.getStatus()==status)
				.map(request->
					new ServiceReport(
							request.getServiceId(),
							request.getBrand(),
							request.getIssues(),
							request.getServiceFee()
							)
				).collect(Collectors.toList());
	    if(reports.isEmpty()) {
	    	   throw new MobileServiceException("Service.NO_RECORDS_FOUND");
	    }
		return reports;
	   }catch(MobileServiceException e) {
		    LOGGER.error(e.getMessage(), e);
		    throw e;
	   }
	}

}
