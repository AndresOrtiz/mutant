package com.mercado.mutant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Builder
@Document
@ToString
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class HumanCount {

    @Id
    private String id;
    private long total;
    private long mutant;

}
