package com.example.untitled31.controller;


import com.example.untitled31.Model.Review;
import com.example.untitled31.dto.ReviewDto;
import com.example.untitled31.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private  final ReviewService reviewService;

    @GetMapping("/api/movies/{movieId}/reviews")
    public Page<Review> listByMovie(@PathVariable Long movieId,
                                    @RequestParam(defaultValue = "0") int page,     // 페이지 로 받겠다 했으니 필요함
                                    @RequestParam(defaultValue = "10") int size) {  // 페이지 로 받겠다 했으니 필요함
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

        return reviewService.getByMovie(movieId, pageable);
    }

    @PostMapping("/api/movies/{movieId}/reviews")       // 어떤 movie 에 대한 review 이기때문에 주소를 연관지어 정한다.
    public Review add(@PathVariable Long movieId,
                      @Valid @RequestBody ReviewDto reviewDto) {
        Review review = new Review();
        review.setReviewer(reviewDto.getReviewer());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        return reviewService.create(movieId, review);
    }

    @PostMapping("/api/reviews/{id}")             // 얘는 그냥 자기자신 만 수정하면 되서 movie 관련이 필요 없다.
    public Review update(@PathVariable Long id,
                         @Valid @RequestBody ReviewDto reviewDto) {
        Review review = new Review();
        review.setReviewer(reviewDto.getReviewer());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());

        return reviewService.update(id, review);
    }

    @DeleteMapping("/api/reviews/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);

        return ResponseEntity.noContent().build();      // 삭제해서 응답할 값이 사라져서 이렇게 처리
    }
}