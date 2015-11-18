/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.proxy;

import br.edu.unifei.eco009.steamlansync.utils.UriParser;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLSession;
import org.littleshoot.proxy.ActivityTracker;
import org.littleshoot.proxy.FlowContext;
import org.littleshoot.proxy.FullFlowContext;

/**
 *
 * @author bwowk
 */
public class SteamActivityTracker implements ActivityTracker{
    public void responseSentToClient(FlowContext flowContext, HttpResponse httpResponse) {
			// TODO Auto-generated method stub

        }

        public void responseReceivedFromServer(FullFlowContext flowContext, HttpResponse httpResponse) {
//            if (httpResponse.headers().get("content-type").equals("application/x-steam-chunk")) {
//                System.out.println(httpResponse);
//            }

        }

        public void requestSentToServer(FullFlowContext flowContext, HttpRequest httpRequest) {
            if (httpRequest.getMethod().equals(HttpMethod.GET) && httpRequest.getUri().contains("steampowered.com/depot/")) {
                System.out.println(httpRequest.getUri());
                System.out.println(UriParser.getAppId(httpRequest));
            }

        }

        public void requestReceivedFromClient(FlowContext flowContext, HttpRequest httpRequest) {
			// TODO Auto-generated method stub

        }

        public void clientSSLHandshakeSucceeded(InetSocketAddress clientAddress, SSLSession sslSession) {
			// TODO Auto-generated method stub

        }

        public void clientDisconnected(InetSocketAddress clientAddress, SSLSession sslSession) {
			// TODO Auto-generated method stub

        }

        public void clientConnected(InetSocketAddress clientAddress) {
			// TODO Auto-generated method stub

        }

        public void bytesSentToServer(FullFlowContext flowContext, int numberOfBytes) {
			// TODO Auto-generated method stub

        }

        public void bytesSentToClient(FlowContext flowContext, int numberOfBytes) {
			// TODO Auto-generated method stub

        }

        public void bytesReceivedFromServer(FullFlowContext flowContext, int numberOfBytes) {
			// TODO Auto-generated method stub

        }

        public void bytesReceivedFromClient(FlowContext flowContext, int numberOfBytes) {
			// TODO Auto-generated method stub

        }
}
