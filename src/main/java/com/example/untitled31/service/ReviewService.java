package com.example.untitled31.service;

import com.example.untitled31.Model.Movie;
import com.example.untitled31.Model.Review;
import com.example.untitled31.repository.MovieRepository;
import com.example.untitled31.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    public Page<Review> getByMovie(Long movieId, Pageable pageable) {   // movie 를 기준으로 get 하겠다 , review 도 페이지 형태로 하겠다.
        return reviewRepository.findByMovieId(movieId, pageable);
    }

    public Review create(Long movieId, Review review) {
        Movie movie = movieRepository.findById(movieId)                 // id 로 해도 되는데 reviewId 와의 혼동을 예방하기위해 movieId 로 함
                .orElseThrow(() -> new NoSuchElementException("영화가 없습니다: " + movieId));

        review.setMovie(movie);

        return reviewRepository.save(review);
    }

    public Review update(Long id, Review review) {
        Review existReview = reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("리뷰가 없습니다: " + id));

        existReview.setReviewer(review.getReviewer());
        existReview.setRating(review.getRating());
        existReview.setComment(review.getComment());

        return reviewRepository.save(existReview);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
}
