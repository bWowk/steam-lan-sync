package br.edu.unifei.eco009.steamlansync;

import br.unifei.edu.eco009.steamlansync.cache.SteamCache;
import br.unifei.edu.eco009.steamlansync.proxy.SteamProxy;
import java.net.InetSocketAddress;
import java.util.Hashtable;

import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.text.translate.AggregateTranslator;
import org.littleshoot.proxy.ActivityTracker;
import org.littleshoot.proxy.FlowContext;
import org.littleshoot.proxy.FullFlowContext;
import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersAdapter;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        SteamProxy proxyServer = new SteamProxy();
//        proxyServer.setActivityTracker(true);
        proxyServer.start();
    }

    public static Hashtable<String, FullHttpResponse> chunks = new Hashtable<String, FullHttpResponse>();

    


}
