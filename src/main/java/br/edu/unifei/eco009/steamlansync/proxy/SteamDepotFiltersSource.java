/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.proxy;

import br.edu.unifei.eco009.steamlansync.cache.HttpChunkContents;
import br.edu.unifei.eco009.steamlansync.cache.SteamCache;
import br.edu.unifei.eco009.steamlansync.utils.UriParser;
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
public class SteamDepotFiltersSource implements HttpFiltersSource {

    public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
//      Se for uma requisição GET para um servidor CDN da steam:
        if (originalRequest.getMethod().equals(HttpMethod.GET) && originalRequest.getUri().contains("steampowered.com/depot/")) {
//          Se for um chunk  
            if (originalRequest.getUri().contains("chunk")) {
                return new SteamChunkHttpFilter(originalRequest);
            }
//          Se for um manifest  
            if (originalRequest.getUri().contains("manifest")) System.out.println(originalRequest.getUri());
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
