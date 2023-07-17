package com.homework.demo.image;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;
    public Image(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
