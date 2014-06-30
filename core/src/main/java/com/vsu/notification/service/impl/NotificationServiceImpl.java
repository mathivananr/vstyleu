package com.vsu.notification.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestException;
import com.vsu.notification.service.NotificationService;
import com.vsu.service.MailEngine;
import com.vsu.service.SMSEngine;
import com.twilio.sdk.resource.instance.Message;

/**
 * Implementation of notification interface.
 *
 * @author <a href="mailto:mathivanan18@gmail.com">Mathi</a>
 */

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {

	protected final Log log = LogFactory
			.getLog(com.vsu.notification.service.impl.NotificationServiceImpl.class);

	private SMSEngine smsEngine;

	private MailEngine mailEngine;

	@Autowired(required = false)
	public void setMailEngine(MailEngine mailEngine) {
		this.mailEngine = mailEngine;
	}

	@Autowired(required = false)
	public void setSmsEngine(final SMSEngine smsEngine) {
		this.smsEngine = smsEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> sendEmail(String recipients, String subject,
			String bodyText) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			if (null != recipients) {
				mailEngine.sendMessage(recipients.split(","), null, subject,
						bodyText);
				response.put("status", "success");
				response.put("message", "Mail sent.");
			} else {
				response.put("status", "error");
				response.put("message", "Problem in sending mail.");
			}
		} catch (MessagingException e) {
			log.error(e.getMessage(), e);
			response.put("status", "error");
			response.put("message", "Problem in sending mail, ");
		}
		return response;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> sendSMS(String mobileNo, String message) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			Message sms = smsEngine.sendTwilioSms(mobileNo, message);
			if (null != sms) {
				response.put("status", "success");
				response.put("message", "Message sent.");
			} else {
				response.put("status", "error");
				response.put("message", "Problem in sending mail.");
			}
		} catch (TwilioRestException e) {
			log.error(e.getMessage(), e);
			response.put("status", "error");
			response.put("message", "Problem in sending message.");
		}
		return response;
	}

}
