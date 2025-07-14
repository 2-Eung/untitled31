package com.example.untitled31.service;

import com.example.untitled31.Model.Movie;
import com.example.untitled31.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional                   // 단위작업을 보장 (입금 출금 이 한세트 같은 느낌)
public class MovieService {
    private final MovieRepository movieRepository;

    public Page<Movie> getAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie); //  save 를 실행해서 무비 객체를 전달한다.
    }

    public Movie getById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("영화가 없습니다: " + id)); // id 받아왔는데 사용자가 없는 id 를 입력했을경우
    }           // 단 , 이경우 500 번대 에러로 표시되는데 표시되는 에러를 404 에러로 바꿔주면 좋다. (사용자가 잘못입력한경우이기 때문)

    public Movie update(Long id, Movie movie) {             // 결과를 던져줘야하기때문에 무비를 반환한다.
        Movie existMovie = getById(id);                     // 무비가 존재하는지를 getById 로 확인하고 (에러 처리는 getById 에서 이미해서 필요없음)

        existMovie.setTitle(movie.getTitle());              // 존재 하면 받아오고
        existMovie.setReleaseYear(movie.getReleaseYear());

        return movieRepository.save(existMovie);            // 받아온것을 save 에 던진다
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }
}
