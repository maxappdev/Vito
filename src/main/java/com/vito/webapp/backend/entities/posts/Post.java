package com.vito.webapp.backend.entities.posts;

import com.vito.webapp.backend.entities.BasicEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
public abstract class Post extends BasicEntity {

    @NotBlank
    @Column(length = 32000)
    private String text;

    @NotNull
    private LocalDateTime publishAt;

}

