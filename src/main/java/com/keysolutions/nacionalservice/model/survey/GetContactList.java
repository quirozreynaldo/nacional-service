package com.keysolutions.nacionalservice.model.survey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetContactList {
    private String id;
    private String name;
    private String href;
}
