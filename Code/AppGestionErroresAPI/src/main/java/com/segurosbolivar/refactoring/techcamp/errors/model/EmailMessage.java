package com.segurosbolivar.refactoring.techcamp.errors.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class EmailMessage {
	
	private String to;
    private Long idError;
    

}
