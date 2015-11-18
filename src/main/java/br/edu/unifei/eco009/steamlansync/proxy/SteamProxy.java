/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.proxy;

import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

/**
 *
 * @author bwowk
 */
public class SteamProxy {
    private HttpProxyServer server;
    private int port = 12321;
    private boolean activityTracker = false;
    
    public void start(){
            HttpProxyServerBootstrap boot = DefaultHttpProxyServer.bootstrap()
                .withPort(port)
                .withTransparent(true)
                .withFiltersSource(new SteamDepotFiltersSource());
            if (activityTracker)boot.plusActivityTracker(new SteamActivityTracker());
            server = boot.start();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public boolean getActivityTracker() {
        return activityTracker;
    }

    public void setActivityTracker(boolean activityTracker) {
        this.activityTracker = activityTracker;
    }
    
    
    
}
