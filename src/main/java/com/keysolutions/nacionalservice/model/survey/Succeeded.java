package com.keysolutions.nacionalservice.model.survey;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Succeeded {
    private List<Contact> succeeded;
    private List<Contact> invalid;
    private List<Contact> existing;
}
