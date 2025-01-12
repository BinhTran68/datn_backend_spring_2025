package com.poly.app.domain.response.size;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeResponse {
    Integer id;
    String code;
    String sizeName;
    Long updateAt;
    Status status;
}
