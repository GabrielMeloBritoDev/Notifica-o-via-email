package com.notificacao.ViaEmail.infrastructure.excepitions;

public class EmailExceptions extends RuntimeException {
    public EmailExceptions(String mensagem) {
        super(mensagem);
    }

    public EmailExceptions(String mensagem, Throwable throwable){
        super(mensagem,throwable);
    }
}
