package fr.ippon.tatami.service;

import fr.ippon.tatami.domain.Attachment;
import fr.ippon.tatami.domain.User;
import fr.ippon.tatami.repository.AttachmentRepository;
import fr.ippon.tatami.repository.UserAttachmentRepository;
import fr.ippon.tatami.security.AuthenticationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;

@Service
public class AttachmentService {

    private static final Log log = LogFactory.getLog(AttachmentService.class);

    @Inject
    private AttachmentRepository attachmentRepository;

    @Inject
    private UserAttachmentRepository userAttachmentRepository;

    @Inject
    private AuthenticationService authenticationService;

    public String createAttachment(Attachment attachment) {
        attachmentRepository.createAttachment(attachment);
        String attachmentId = attachment.getAttachmentId();
        userAttachmentRepository.addAttachementId(authenticationService.getCurrentUser().getLogin(),
                attachmentId);

        return attachmentId;
    }

    public Attachment getAttachementById(String attachmentId) {
        return attachmentRepository.findAttachmentById(attachmentId);
    }

    public Collection<String> getAttachmentIdsForCurrentUser() {
        Collection<String> attachmentIds = userAttachmentRepository.findAttachementIds(
                authenticationService.getCurrentUser().getLogin());

        return attachmentIds;
    }
}
