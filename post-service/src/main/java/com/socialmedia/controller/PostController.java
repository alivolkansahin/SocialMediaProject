package com.socialmedia.controller;

import com.socialmedia.constant.EndPoint;
import com.socialmedia.dto.request.PostSaveRequestDto;
import com.socialmedia.dto.response.PostSaveResponseDto;
import com.socialmedia.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.socialmedia.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + POST)
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(SAVE)
    public ResponseEntity<PostSaveResponseDto> createPost(@RequestBody @Valid PostSaveRequestDto dto){
        return ResponseEntity.ok(postService.createPost(dto));
    }

}
