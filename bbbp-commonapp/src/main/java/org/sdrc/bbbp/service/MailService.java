package org.sdrc.bbbp.service;

import javax.mail.MessagingException;

import org.sdrc.bbbp.models.MailModel;

public interface MailService {

	String sendMail(MailModel mail) throws MessagingException  ;
}
