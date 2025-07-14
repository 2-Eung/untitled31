package com.example.untitled31.controller;

import com.example.untitled31.Model.Movie;
import com.example.untitled31.dto.MovieDto;
import com.example.untitled31.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public Page<Movie> list(@RequestParam(defaultValue = "0") int page,         // 어디서부터
                            @RequestParam(defaultValue = "10") int size) {      // 몇개 (정렬순서는 우리가 주는거지 받을 필요 없다)
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
                                                                                // descending() : 정렬순서
        return movieService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Movie get(@PathVariable Long id) {
        return movieService.getById(id);
    }

    @PostMapping
    public Movie create(@Valid @RequestBody MovieDto movieDto) {
        Movie movie = new Movie();

        movie.setTitle(movieDto.getTitle());            // DTO 에서 받아온것들을 하나씩 넣어준다
        movie.setReleaseYear(movieDto.getReleaseYear());

        return movieService.create(movie);              // 서비스의 크리에이트에 무비데이터를 넣어준다.
    }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @Valid @RequestBody MovieDto movieDto) {
        Movie movie = new Movie();

        movie.setTitle(movieDto.getTitle());
        movie.setReleaseYear(movieDto.getReleaseYear());

        return movieService.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieService.delete(id);

        return ResponseEntity.noContent().build();      // 삭제해서 응답할 값이 사라져서 이렇게 처리
    }
}