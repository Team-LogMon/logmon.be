package com.cau.gdg.logmon.logging.wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CachedServletOutputStream extends ServletOutputStream {

    private final ByteArrayOutputStream cachedBody;
    private final OutputStream originalOutputStream;

    public CachedServletOutputStream(ByteArrayOutputStream cachedBody, OutputStream originalOutputStream) {
        this.cachedBody = cachedBody;
        this.originalOutputStream = originalOutputStream;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void write(int b) throws IOException {
        cachedBody.write(b);  // 캐시된 바디에 저장
        originalOutputStream.write(b);  // 실제 응답 스트림에도 출력
    }
}
