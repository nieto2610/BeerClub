package com.digitalHouse.beerClub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionDTO {

    private List<String> messages;


}
