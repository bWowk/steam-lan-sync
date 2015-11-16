/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.cache;

import br.unifei.edu.eco009.steamlansync.proxy.HttpResponseContent;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.store.DiskStoreBootstrapCacheLoader;

/**
 *
 * @author bwowk
 */
public class SteamCache {

    private static CacheManager cacheManager;
    private static DiskStoreBootstrapCacheLoader loader;

    public static void bootStrap() {
        Configuration cacheManagerConfig = new Configuration()
                .diskStore(new DiskStoreConfiguration()
                        .path("/home/bwowk/ehcache"));
        cacheManager = new CacheManager(cacheManagerConfig);
        loader = new DiskStoreBootstrapCacheLoader(true);
    }

    public static Cache getCache(String key) {
        if (cacheManager.getCache(key) == null) {
            CacheConfiguration cacheConfig = new CacheConfiguration()
                    .name(key)
                    .maxBytesLocalHeap(16, MemoryUnit.MEGABYTES)
                    .persistence(new PersistenceConfiguration()
                            .strategy(PersistenceConfiguration.Strategy.LOCALTEMPSWAP));
            Cache cache = new Cache(cacheConfig);
            cacheManager.addCache(cache);
//            loader.load(cache);

        }
        return cacheManager.getCache(key);
    }

    public static boolean cacheHasChunk(String appId, String chunkId) {
        return (getCache(appId).get(chunkId) != null);
    }

    public static FullHttpResponse getChunk(String AppId, String chunkId) {
        return ((ChunkElement) getCache(AppId).get(chunkId)).getResponseContent().getFullResponse();
    }

    public static void putChunk(String appId, String chunkId, FullHttpResponse response) {
        ChunkElement element = new ChunkElement(chunkId, new HttpResponseContent(response));
        getCache(appId).put(element);
    }
}
