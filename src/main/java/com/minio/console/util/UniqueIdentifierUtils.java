package com.minio.console.util;

import java.util.UUID;

public class UniqueIdentifierUtils {
    public static synchronized String getUrl() {
        return UUID.randomUUID().toString();
    }
}
