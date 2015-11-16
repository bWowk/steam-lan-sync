/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unifei.edu.eco009.steamlansync.cache;

import br.unifei.edu.eco009.steamlansync.proxy.HttpResponseContent;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import net.sf.ehcache.Element;

/**
 *
 * @author bwowk
 */
public class ChunkElement extends Element{
    
    
    public ChunkElement(String chunkId, HttpResponseContent value) {
        super(chunkId, value);
    }
    
    public HttpResponseContent getResponseContent(){
        return (HttpResponseContent) getObjectValue();
    }
    
    
    
}
