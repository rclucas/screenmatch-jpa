package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Genre") String genero,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Poster") String poster,
                         @JsonAlias("Plot") String sinopse
//                         @JsonAlias("Year") String ano,
//                         @JsonAlias("Rated") String avaliada,
//                         @JsonAlias("Released") String lancamento,
//                         @JsonAlias("Runtime") String tempoExecucao,
//                         @JsonAlias("Director") String diretor,
//                         @JsonAlias("Writer") String escritor,
//                         @JsonAlias("Language") String linguagem,
//                         @JsonAlias("Country") String pais,
//                         @JsonAlias("Awards") String premios,
//                         @JsonAlias("Type") String tipo,
//                         @JsonAlias("imdbVotes") String votos
) {
}
