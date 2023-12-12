package com.socialmedia.service;

import com.socialmedia.dto.request.PostSaveRequestDto;
import com.socialmedia.dto.response.PostSaveResponseDto;
import com.socialmedia.entity.Post;
import com.socialmedia.manager.IUserProfileManager;
import com.socialmedia.mapper.IPostMapper;
import com.socialmedia.repository.IPostRepository;
import com.socialmedia.utility.ServiceManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class PostService extends ServiceManager<Post, Long> {

    private final IPostRepository postRepository;
    private final IUserProfileManager iUserProfileManager;

    public PostService(IPostRepository postRepository, IUserProfileManager iUserProfileManager) {
        super(postRepository);
        this.postRepository = postRepository;
        this.iUserProfileManager = iUserProfileManager;
    }

    public PostSaveResponseDto createPost(PostSaveRequestDto dto) {
        System.out.println("Hata 1");
        Long id = iUserProfileManager.getUserIdByToken(dto.getToken());
        System.out.println("Hata 2");
        Post post = Post.builder()
                .photo(dto.getPhoto())
                .header(dto.getHeader())
                .content(dto.getContent())
                .userid(id)
                .build();
        save(post);
        return IPostMapper.INSTANCE.savePostToDto(post);
//        if(responseId.getStatusCode().value() != 200) return
    }
}
