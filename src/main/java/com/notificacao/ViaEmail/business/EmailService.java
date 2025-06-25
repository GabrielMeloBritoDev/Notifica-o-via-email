package com.notificacao.ViaEmail.business;


import com.notificacao.ViaEmail.business.Dto.TarefaDto;
import com.notificacao.ViaEmail.infrastructure.excepitions.EmailExceptions;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${envio.email.remetente}")
    public String remetente;

    @Value("${envio.email.nomeRemetente}")
    private  String nomeRemetente;

    public  void enviaEmail(TarefaDto tarefaDTo){

        try{
            MimeMessage  mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mensagem,true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(tarefaDTo.getEmail()));
            mimeMessageHelper.setSubject("Notificação de tarefa");

            Context context =  new Context();
            context.setVariable("nomeTarefa", tarefaDTo.getNomeTarefa());
            context.setVariable("dataEvento", tarefaDTo.getDataEvento());
            context.setVariable("descricao", tarefaDTo.getDescricao());
            String notificacao = templateEngine.process("notificacao", context);
            mimeMessageHelper.setText(notificacao, true);
            javaMailSender.send(mensagem);


        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new EmailExceptions("Erro ao enviar ", e.getCause());
        }
    }


}
