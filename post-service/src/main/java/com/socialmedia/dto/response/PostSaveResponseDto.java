package com.socialmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostSaveResponseDto {

    @Builder.Default
    private String info = "Post Created!";

    private String photo;

    private String header;

    private String content;

    private String created;

}
