/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.cache;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.ws.Response;

/**
 *
 * @author bwowk
 */
public class HttpChunkContents implements Serializable {

    private byte[] bytes;
    Hashtable<String, String> headers;

    public HttpChunkContents(FullHttpResponse response) {
        bytes = new byte[response.content().capacity()];
        headers = new Hashtable<>();
        copyHeaders(response);
        response.content().getBytes(0, bytes);
    }
    
    private void copyHeaders(FullHttpResponse response){
        for (Entry<String,String> entry : response.headers().entries()){
            headers.putIfAbsent(entry.getKey(),entry.getValue());
        }
    }

    public FullHttpResponse getFullHttpResponse() {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.copiedBuffer(bytes));
        HttpHeaders head = response.headers();
        for (Entry<String, String> entry : headers.entrySet()) {
            head.add(entry.getKey(), entry.getValue());
        }
        return response;
    }

}
