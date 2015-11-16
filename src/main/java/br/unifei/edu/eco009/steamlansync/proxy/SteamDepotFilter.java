/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.proxy;

import br.unifei.edu.eco009.steamlansync.cache.HttpChunkContents;
import br.unifei.edu.eco009.steamlansync.cache.SteamCache;
import br.unifei.edu.eco009.steamlansync.utils.UriParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSource;

/**
 *
 * @author bwowk
 */
public class SteamDepotFilter implements HttpFiltersSource {

    private String chunkId;

    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
        if (originalRequest.getMethod().equals(HttpMethod.GET)
                && originalRequest.getUri().contains("steampowered.com/depot/")
                && originalRequest.getUri().contains("chunk")) {

            // alterar o precedimento da
            // request:
            return new HttpFiltersAdapter(originalRequest) {
                boolean store = false;
                String chunkId = UriParser.getChunkId(originalRequest);
                HttpChunkContents cachedChunk;

                @Override
                public HttpResponse clientToProxyRequest(HttpObject httpObject) {
//                        return chunks.get(getChunkId(originalRequest)).copy().retain();
                    cachedChunk = SteamCache.getChunk(chunkId);
                    if (cachedChunk == null) {
                        store = true;
                        System.out.println("cache MISS");
                        return super.clientToProxyRequest(httpObject);
                    }
                    System.out.println("cache HIT");
                    return cachedChunk.getFullHttpResponse().retain();

                }

                @Override
                public HttpObject proxyToClientResponse(HttpObject httpObject) {
                    FullHttpResponse resp = (FullHttpResponse) super.proxyToClientResponse(httpObject);
//                        chunks.put(getChunkId(originalRequest), resp.copy());
                    if (store) {
                        SteamCache.putChunk(chunkId, new HttpChunkContents(resp));
                    }
                    return resp;
                }

            };
        }
        return null;
    }

public int getMaximumRequestBufferSizeInBytes() {
        // TODO Auto-generated method stub
        return 10000000;
    }
    
    public int getMaximumResponseBufferSizeInBytes() {
        // TODO Auto-generated method stub
        return 10000000;
    }
    
}
