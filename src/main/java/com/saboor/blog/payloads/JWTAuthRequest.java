package com.saboor.blog.payloads;

import lombok.Data;

@Data
public class JWTAuthRequest {

    private String email;
    private String password;
}
