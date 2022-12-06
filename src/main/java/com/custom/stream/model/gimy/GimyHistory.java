package com.custom.stream.model.gimy;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.io.Serializable;
import java.sql.Timestamp;

@ToString
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GimyHistory implements Serializable {
    @Id
    private Timestamp watchTime;
    private String historyStr;
    private String channelUrl;
    private String videoUrl;
}
