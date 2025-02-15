package com.cau.gdg.logmon.security.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OauthConstants {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Google {
        public static final String NAME = "name";
        public static final String PICTURE = "picture";
        public static final String EMAIL = "email";
    }
}
