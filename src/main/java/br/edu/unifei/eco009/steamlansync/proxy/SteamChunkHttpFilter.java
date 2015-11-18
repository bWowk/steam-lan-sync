/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.proxy;

import br.edu.unifei.eco009.steamlansync.cache.HttpChunkContents;
import br.edu.unifei.eco009.steamlansync.cache.SteamCache;
import br.edu.unifei.eco009.steamlansync.utils.UriParser;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;

/**
 *
 * @author bwowk
 */
public class SteamChunkHttpFilter extends HttpFiltersAdapter {

    public SteamChunkHttpFilter(HttpRequest originalRequest) {
        super(originalRequest);
    }

    boolean store = false;
    String chunkId = UriParser.getChunkId(originalRequest);
    String appId = UriParser.getAppId(originalRequest);

    HttpChunkContents cachedChunk;

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
//                        return chunks.get(getChunkId(originalRequest)).copy().retain();
        cachedChunk = SteamCache.getChunk(chunkId, appId);
//      cache MISS:
        System.out.println("cache MISS, chunk "+chunkId);
        if (cachedChunk == null) {
            store = true;
            return super.clientToProxyRequest(httpObject);
        }
//      cache HIT:
        System.out.println("cache HIT, chunk "+chunkId);
        return cachedChunk.getFullHttpResponse().retain();

    }

    @Override
    public HttpObject proxyToClientResponse(HttpObject httpObject) {
        FullHttpResponse resp = (FullHttpResponse) super.proxyToClientResponse(httpObject);
//                        chunks.put(getChunkId(originalRequest), resp.copy());
        if (store) {
            SteamCache.putChunk(chunkId, appId, new HttpChunkContents(resp));
        }
        return resp;
    }

}
