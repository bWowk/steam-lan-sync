/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.cache;

import io.netty.handler.codec.http.FullHttpResponse;
import java.util.Hashtable;

/**
 *
 * @author bwowk
 */
public class SteamCache {

    private static Hashtable<String, FullHttpResponse> chunks = new Hashtable<>();

    public static FullHttpResponse getChunk(String chunkId) {
        return (FullHttpResponse) chunks.get(chunkId);
    }

    public static void putChunk(String chunkId, FullHttpResponse response) {
        chunks.put(chunkId, response);
    }
    
    public static boolean hasChunk(String chunkId){
        return chunks.containsKey(chunkId);
    }
}
