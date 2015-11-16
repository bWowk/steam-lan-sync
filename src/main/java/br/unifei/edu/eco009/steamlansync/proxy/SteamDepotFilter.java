/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.proxy;

import static br.edu.unifei.eco009.steamlansync.App.chunks;
import br.unifei.edu.eco009.steamlansync.cache.SteamCache;
import br.unifei.edu.eco009.steamlansync.utils.UriParser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import java.util.Hashtable;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSource;

/**
 *
 * @author bwowk
 */
public class SteamDepotFilter implements HttpFiltersSource {

    public static Hashtable<String, HttpResponseContent> chunks = new Hashtable<>();

    @Override
    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {

        if (originalRequest.getMethod().equals(HttpMethod.GET)
                && originalRequest.getUri().contains("steampowered.com/depot/")
                && originalRequest.getUri().contains("chunk")) {
            String appId = UriParser.getAppId(originalRequest);
            String chunkId = UriParser.getChunkId(originalRequest);
//            if (SteamDepotFilter.chunks.contains(chunkId)) {
            if (SteamCache.cacheHasChunk(appId, chunkId)) { // se o chunk está em cache,
                // alterar o precedimento da
                // request:
                System.out.println("cache HIT");
                return new HttpFiltersAdapter(originalRequest) {
                    String appId = UriParser.getAppId(originalRequest);
                    String chunkId = UriParser.getChunkId(originalRequest);

                    @Override
                    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
//                        return SteamDepotFilter.chunks.get(chunkId).getFullResponse();
                        return SteamCache.getChunk(appId, chunkId);
                    }

                };
            } else {
                System.out.println("cache MISS");
                return new HttpFiltersAdapter(originalRequest) {
                    String appId = UriParser.getAppId(originalRequest);
                    String chunkId = UriParser.getChunkId(originalRequest);
                    @Override
                    public HttpObject proxyToClientResponse(HttpObject httpObject) {
                        FullHttpResponse resp = (FullHttpResponse) super.proxyToClientResponse(httpObject);
//                        SteamDepotFilter.chunks.put(chunkId, new HttpResponseContent(resp));
                        SteamCache.putChunk(appId, chunkId, resp);
                        return resp;
                    }

                };
            }
        } else {
            return null;
        }
    }

    @Override
    public int getMaximumRequestBufferSizeInBytes() {
        return 10000000;
    }

    @Override
    public int getMaximumResponseBufferSizeInBytes() {
        return 10000000;
    }

}
