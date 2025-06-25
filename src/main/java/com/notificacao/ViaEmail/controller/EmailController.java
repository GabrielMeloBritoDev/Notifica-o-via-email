package com.notificacao.ViaEmail.controller;

import com.notificacao.ViaEmail.business.Dto.TarefaDto;
import com.notificacao.ViaEmail.business.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefaDto tarefaDto){
        emailService.enviaEmail(tarefaDto);
        return ResponseEntity.ok().build();
    }
}
