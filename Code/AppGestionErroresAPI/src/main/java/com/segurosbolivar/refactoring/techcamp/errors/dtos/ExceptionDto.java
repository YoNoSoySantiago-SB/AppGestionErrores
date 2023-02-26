package com.segurosbolivar.refactoring.techcamp.errors.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
	private Exception exception;
    private String applicationName;
}
