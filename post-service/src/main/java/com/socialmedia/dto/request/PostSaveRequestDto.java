package com.socialmedia.dto.request;

import com.socialmedia.constant.ErrorStaticMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.socialmedia.constant.ErrorStaticMessage.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostSaveRequestDto {

    @NotBlank(message = BLANK_TOKEN)
    private String token;

    private String photo;

    @NotBlank(message = BLANK_HEADER)
    private String header;

    @NotBlank(message = BLANK_CONTENT)
    private String content;

}
