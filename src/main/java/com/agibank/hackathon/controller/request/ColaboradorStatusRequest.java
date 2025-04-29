package com.agibank.hackathon.controller.request;

import com.agibank.hackathon.entities.enums.StatusColaborador;
import lombok.Data;

@Data
public class ColaboradorStatusRequest {
    private StatusColaborador status;
}
