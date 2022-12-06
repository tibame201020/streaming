package com.custom.stream.model.gimy;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TempPagesData implements Serializable {
    @Id
    private long page;
    @Lob
    private GimyVideo[] gimyVideos;
}
