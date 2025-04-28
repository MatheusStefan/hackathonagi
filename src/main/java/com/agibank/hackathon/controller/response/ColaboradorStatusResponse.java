package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.enums.statusColaborador;
import lombok.Data;

@Data
public class ColaboradorStatusResponse {
    private statusColaborador status;
}
