/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.utils;

import io.netty.handler.codec.http.HttpRequest;

/**
 *
 * @author bwowk
 */
public class UriParser {
    public static String getChunkId(HttpRequest req) {
        String uri = req.getUri();
        return uri.split("chunk\\/")[1].split("\\?")[0];
    }
    public static String getAppId(HttpRequest req) {
        String uri = req.getUri();
        return uri.split("depot\\/")[1].split("\\/")[0];
    }
}
