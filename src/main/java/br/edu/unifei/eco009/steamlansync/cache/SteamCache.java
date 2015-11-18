/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.cache;

import io.netty.handler.codec.http.FullHttpResponse;
import java.util.Hashtable;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.GroupCacheAccess;

/**
 *
 * @author bwowk
 */
public class SteamCache {

    private static Hashtable<String, HttpChunkContents> chunks;
    private static GroupCacheAccess<String, HttpChunkContents> cache;

    public static void bootstrap() {
        chunks = new Hashtable<String, HttpChunkContents>();
        cache = JCS.getGroupCacheInstance("chunks");
    }

    public static HttpChunkContents getChunk(String chunkId, String appId) {
        return cache.getFromGroup(chunkId,appId);
    }

    public static void putChunk(String chunkId, String appId,  HttpChunkContents response) {
        cache.putInGroup(chunkId,appId, response);
    }
}
