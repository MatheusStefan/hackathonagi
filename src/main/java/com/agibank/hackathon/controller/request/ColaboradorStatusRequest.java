package com.agibank.hackathon.controller.request;

import com.agibank.hackathon.entities.enums.statusColaborador;
import lombok.Data;

@Data
public class ColaboradorStatusRequest {
    private statusColaborador status;
}
