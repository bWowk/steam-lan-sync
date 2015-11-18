/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.proxy;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import java.io.Serializable;

/**
 *
 * @author bwowk
 */
public class HttpResponseContent implements Serializable {

    private byte[] bytes;
    private String length;
    private String steam_sid;
    private String crc;
    public HttpResponseContent(FullHttpResponse originalResponse) {
        bytes = new byte[originalResponse.content().capacity()];
        originalResponse.content().getBytes(0,bytes);
        HttpHeaders headers = originalResponse.headers();
        length = headers.get("Content-Length");
        steam_sid = headers.get("x-steam-sid");
        crc = headers.get("x-content-crc");
        
    }

    public FullHttpResponse getFullResponse() {
        ByteBuf buffer = Unpooled.copiedBuffer(bytes);
        FullHttpResponse newResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buffer);
        if (length != null)newResponse.headers().add("Content-Length", length);
        if (steam_sid != null)newResponse.headers().add("x-steam-sid", steam_sid);
        if (crc != null) newResponse.headers().add("x-content-crc",crc);
        newResponse.headers().add("content-type","application/x-steam-chunk");
        return newResponse.retain();
    }
    
    

}
