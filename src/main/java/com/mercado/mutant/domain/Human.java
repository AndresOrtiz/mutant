package com.mercado.mutant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Builder
@Document
@ToString
@Generated
@NoArgsConstructor
@AllArgsConstructor
public class Human {

    @Id
    private String id;
    @Indexed(name = "dna_sequence_index")
    private List<String> dnaSequence;
    private boolean isMutant;

}
