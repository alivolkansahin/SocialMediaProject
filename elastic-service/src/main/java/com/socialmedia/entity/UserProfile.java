package com.socialmedia.entity;

import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document(indexName = "userprofiles")
public class UserProfile {

    @Id
    private String id;

//    private Long userProfileId;   // mongodb ekleyince zaten bizim eski kurgudaki id değeri ile userprofileid değeri aynı olucak diye gizledik

    private Long authId;

    private String username;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

    @Builder.Default
    private EStatus status = EStatus.PENDING;

}
