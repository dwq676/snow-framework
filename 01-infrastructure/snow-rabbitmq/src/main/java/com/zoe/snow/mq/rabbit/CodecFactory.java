package com.zoe.snow.mq.rabbit;

import java.io.IOException;

/**
 * CodecFactory
 *
 * @author Dai Wenqing
 * @date 2016/6/21
 */
public interface CodecFactory {
    byte[] serialize(Object obj) throws IOException;

    Object deSerialize(byte[] in) throws IOException;
}
