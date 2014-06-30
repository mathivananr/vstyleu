package com.vsu.notification.service;

import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Web Service interface to send notifications.
 * 
 * @author <a href="mailto:mathivanan18@gmail.com">Mathi</a>
 */
@WebService
@Path("/notifications")
public interface NotificationService {

	/**
	 * send E-Mail
	 * 
	 * @param recipients
	 * @param sender
	 * @param bodyText
	 * @param subject
	 * @return
	 */
	@POST
	@Path("/sendemail/{recipients}/{subject}/{bodyText}")
	@Produces("application/json")
	Map<String, Object> sendEmail(@PathParam("recipients") String recipients,
			@PathParam("subject") String subject,
			@PathParam("bodyText") String bodyText);

	/**
	 * send SMS
	 * 
	 * @param mobileNo
	 * @param message
	 * @return
	 */
	@POST
	@Path("/sendsms/{mobileNo}/{message}")
	@Produces("application/json")
	Map<String, Object> sendSMS(@PathParam("mobileNo") String mobileNo,
			@PathParam("message") String message);
}
