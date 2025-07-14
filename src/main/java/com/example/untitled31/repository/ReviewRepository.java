package com.example.untitled31.repository;

import com.example.untitled31.Model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findByMovieId(Long movieId, Pageable pageable);



// Page : 스프링 도베인의 페이지를 가져온다
//    List<Review> : 그냥 다 줘                                     infinite scroll
//    Page<Review> : 일부만 줘                                      pagenation
//    Pageable : 어디서부터 , 몇개까지 , 어떻게 정렬
}
