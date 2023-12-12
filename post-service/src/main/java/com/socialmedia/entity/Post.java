package com.socialmedia.entity;

import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
@Table(name = "tblposts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private String photo;

    private String header;

    private String content;

    @Builder.Default
    private LocalDateTime created = LocalDateTime.now();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EStatus status = EStatus.SHOWN;
}
