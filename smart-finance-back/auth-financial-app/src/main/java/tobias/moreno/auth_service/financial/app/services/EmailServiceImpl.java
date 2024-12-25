package tobias.moreno.auth_service.financial.app.services;

import tobias.moreno.auth_service.financial.app.dto.EmailTemplateName;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

	private final JavaMailSender mailSender;
	private final SpringTemplateEngine templateEngine;

	@Async
	public void sendEmail(String to, String username, EmailTemplateName emailTemplate,
						  String confirmationUrl, String activationCode, String Subject) throws MessagingException {

		String templateName;
		if (emailTemplate == null) {
			templateName = "confirm-email";
		}else{
			templateName = emailTemplate.getName();
		}
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());

		Map<String, Object> properties = new HashMap<>();
		properties.put("username", username);
		properties.put("activationCode", activationCode);
		properties.put("confirmationUrl", confirmationUrl);

		Context context = new Context();
		context.setVariables(properties);
		mimeMessageHelper.setFrom("tobiasmoreno.tm.21@gmail.com");
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(Subject);

		String template = templateEngine.process(templateName, context);
		mimeMessageHelper.setText(template, true);
		mailSender.send(mimeMessage);

	}

}
