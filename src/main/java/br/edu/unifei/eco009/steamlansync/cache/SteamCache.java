/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unifei.eco009.steamlansync.cache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;

/**
 *
 * @author bwowk
 */
public class SteamCache {

    private static HashMap<String, CacheAccess<String, HttpChunkContents>> cacheRegions;

    public static void bootstrap() {
        cacheRegions = new HashMap<>();
        loadCaches();

    }

    private static void loadCaches() {
        Properties prop = new Properties();
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("cache.ccf"));
        } catch (IOException ex) {
            Logger.getLogger(SteamCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<String> caches = new ArrayList<String>();
        File[] files = new File(prop.getProperty("jcs.auxiliary.blockDiskCache.attributes.DiskPath")).listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().contains(".dat")) {
                    caches.add(file.getName().replace(".dat", ""));
                }
            }
            for (String cache : caches) {
                getCache(cache);
            }
        }
    }

    private static CacheAccess<String, HttpChunkContents> getCache(String appId) {
        synchronized (SteamCache.class) {
            if (!cacheRegions.containsKey(appId)) {
                CacheAccess<String, HttpChunkContents> newRegion = JCS.defineRegion(appId);
                cacheRegions.put(appId, newRegion);
                return newRegion;
            }
            return cacheRegions.get(appId);

        }
    }

//    public static boolean cacheHasChunk(String chunkId, String appId) {
////        return (chunks.containsKey(chunkId));
//        return !(cache.getFromGroup(chunkId,appId) == null);
//    }
    public static HttpChunkContents getChunk(String chunkId, String appId) {
//        return chunks.get(chunkId);
        return getCache(appId).get(chunkId);
    }

    public static void putChunk(String chunkId, String appId, HttpChunkContents response) {
//        chunks.put(chunkId, response);
        getCache(appId).put(chunkId, response);
    }
}
