package com.agibank.hackathon.controller.response;

import com.agibank.hackathon.entities.enums.StatusColaborador;
import lombok.Data;

@Data
public class ColaboradorStatusResponse {
    private StatusColaborador status;
}
