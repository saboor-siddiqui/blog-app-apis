package com.saboor.blog.payloads;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class ApiResponse {
    private String message;
    private Boolean success;

}
