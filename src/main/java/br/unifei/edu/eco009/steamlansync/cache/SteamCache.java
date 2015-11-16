/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.cache;

import io.netty.handler.codec.http.FullHttpResponse;
import java.util.Hashtable;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;

/**
 *
 * @author bwowk
 */
public class SteamCache {

    private static Hashtable<String, HttpChunkContents> chunks;
    private static CacheAccess<String, HttpChunkContents> cache;

    public static void bootstrap() {
        chunks = new Hashtable<String, HttpChunkContents>();
        cache = JCS.getInstance("chunks");
    }

    public static boolean cacheHasChunk(String chunkId) {
//        return (chunks.containsKey(chunkId));
        return !(cache.get(chunkId) == null);
    }

    public static HttpChunkContents getChunk(String chunkId) {
//        return chunks.get(chunkId);
        return cache.get(chunkId);
    }

    public static void putChunk(String chunkId, HttpChunkContents response) {
//        chunks.put(chunkId, response);
        cache.put(chunkId, response);
    }
}
