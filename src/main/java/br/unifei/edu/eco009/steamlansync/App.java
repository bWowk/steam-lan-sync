package br.edu.unifei.eco009.steamlansync;

import br.unifei.edu.eco009.steamlansync.cache.SteamCache;
import br.unifei.edu.eco009.steamlansync.proxy.SteamProxy;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        SteamCache.bootstrap();
        SteamProxy proxyServer = new SteamProxy();
        proxyServer.start();
    }
    


}
