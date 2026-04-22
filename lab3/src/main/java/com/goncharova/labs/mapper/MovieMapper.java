package com.goncharova.labs.mapper;

import com.goncharova.labs.dto.Movie;
import com.goncharova.labs.entity.MovieEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    Movie toDto(MovieEntity entity);

    List<Movie> toDtoList(List<MovieEntity> entityList);
}