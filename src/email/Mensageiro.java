package email;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


public class Mensageiro {
	public static void enviarEmail(String destinatario, String mensagemTexto) {
		 //Configurações da conta que vai enviar o e-mail
        String remetente = "projetospoo@gmail.com";  // Coloque aqui o e-mail que vai enviar
        String senha = "qmtj xczy bpsj yhrw";            // Coloque aqui a senha (ou app password, se for Gmail)

        //Configuração do servidor SMTP (exemplo: Gmail)
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        

        //Criando a sessão com autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        try {
            // Criando a mensagem
            Message mensagem = new MimeMessage(session);
            mensagem.setFrom(new InternetAddress(remetente));
            mensagem.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensagem.setSubject("Relatório de Tarefas");

            // Corpo do e-mail
            BodyPart corpo = new MimeBodyPart();
            corpo.setText(mensagemTexto);

            // Anexo (o PDF)
            MimeBodyPart anexo = new MimeBodyPart();
            String arquivo = "relatorio.pdf";  // Nome do PDF gerado anteriormente
            DataSource fonte = new FileDataSource(arquivo);
            anexo.setDataHandler(new DataHandler(fonte));
            anexo.setFileName(arquivo);

            // Montando a estrutura final com corpo + anexo
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(corpo);
            multipart.addBodyPart(anexo);

            mensagem.setContent(multipart);

            // Enviando a mensagem
            Transport.send(mensagem);

            System.out.println("E-mail enviado com sucesso!");

        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o e-mail: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
}