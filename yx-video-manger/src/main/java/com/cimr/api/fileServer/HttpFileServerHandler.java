package com.cimr.api.fileServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
 
import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.util.regex.Pattern;
 
import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpHeaderUtil.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaderUtil.setContentLength;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
 
/**
 * Created by root on 8/14/17.
 */
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
 
    private final String url;
    String WEBROOT = HttpFileServer.WEBROOT;
    public HttpFileServerHandler(String url) {
        this.url = url;
    }
 
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        if (!fullHttpRequest.decoderResult().isSuccess()){
            sendError(channelHandlerContext, HttpResponseStatus.BAD_REQUEST);
            return;
        }
 
        if (fullHttpRequest.method()!= HttpMethod.GET){
            sendError(channelHandlerContext,HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
 
        String uri=fullHttpRequest.uri();
        if (uri==null||uri.trim().equalsIgnoreCase("")){
            uri="/";
        }
        if (uri.trim().equalsIgnoreCase("/")){
            uri= WEBROOT;
        }
        if(!uri.startsWith(WEBROOT)){
            uri= WEBROOT +uri;
        }
        final String path=sanitizeUri(uri);
        if (path==null){
            sendError(channelHandlerContext,HttpResponseStatus.FORBIDDEN);
            return;
        }
 
        File file=new File(path);
        if (file.isHidden()||!file.exists()){
            sendError(channelHandlerContext,HttpResponseStatus.NOT_FOUND);
            return;
        }
 
        if (file.isDirectory()){
            if (uri.endsWith("/")){
                senfListing(channelHandlerContext,file);
 
            }else {
                sendRedirect(channelHandlerContext,uri+"/");
            }
 
            return;
        }
 
        if (!file.isFile()){
            sendError(channelHandlerContext,HttpResponseStatus.FORBIDDEN);
            return;
        }
 
        RandomAccessFile randomAccessFile=null;
        try{
            randomAccessFile=new RandomAccessFile(file,"r");
        }catch (FileNotFoundException e){
            e.printStackTrace();
            sendError(channelHandlerContext,HttpResponseStatus.NOT_FOUND);
            return;
        }
 
        Long fileLength=randomAccessFile.length();
        HttpResponse httpResponse=new DefaultHttpResponse(HTTP_1_1,OK);
        setContentLength(httpResponse,fileLength);
        setContentTypeHeader(httpResponse,file);
 
        if (isKeepAlive(fullHttpRequest)){
            httpResponse.headers().set(CONNECTION,KEEP_ALIVE);
        }
 
        channelHandlerContext.writeAndFlush(httpResponse);
        ChannelFuture sendFileFuture = channelHandlerContext.write(
                new ChunkedFile(randomAccessFile,0,fileLength,8192),channelHandlerContext.newProgressivePromise());
 
        sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                if (total<0){
                    System.err.println("progress:"+progress);
                }else {
                    System.err.println("progress:"+progress+"/"+total);
                }
            }
 
            public void operationComplete(ChannelProgressiveFuture future) {
                System.err.println("complete");
            }
        });
 
 
        ChannelFuture lastChannelFuture=channelHandlerContext.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!isKeepAlive(fullHttpRequest)){
            lastChannelFuture.addListener(ChannelFutureListener.CLOSE );
 
        }
 
    }
 
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()){
            sendError(ctx,INTERNAL_SERVER_ERROR);
        }
    }
 
 
 
    private static final Pattern INSECURE_URI=Pattern.compile(".*[<>&\"].*");
 
    public String sanitizeUri(String uri){
        try{
            uri= URLDecoder.decode(uri,"UTF-8");
        }catch (Exception e){
            try{
                uri= URLDecoder.decode(uri,"ISO-8859-1");
            }catch (Exception ew){
                ew.printStackTrace();
            }
        }
 
        if (!uri.startsWith(url)){
            return null;
 
        }
 
        if (!uri.startsWith("/")){
            return null;
 
        }
 
        uri=uri.replace('/',File.separatorChar);
        if (uri.contains(File.separator+'.')||uri.startsWith(".")||uri.endsWith(".")||INSECURE_URI.matcher(uri).matches()){
            return null;
        }
 
        return uri;//System.getProperty("user.dir")+uri
 
    }
 
    private static final Pattern ALLOWED_FILE_NAME=Pattern.compile("[a-zA-Z0-9\\.]*");
    private void senfListing(ChannelHandlerContext channelHandlerContext, File dir) {
        FullHttpResponse response=new DefaultFullHttpResponse(HTTP_1_1,OK);
        response.headers().set(CONTENT_TYPE,"text/html;charset=UTF-8");
        StringBuilder builder =new StringBuilder();
        String dirPath=dir.getPath();
        builder.append("<!DOCTYPE html> \r\n");
        builder.append("<html><head><title>");
        builder.append(dirPath);
        builder.append("目录:");
        builder.append("</title></head><body>\r\n");
        builder.append("<h3>");
        builder.append(dirPath).append("目录:");
        builder.append("</h3>\r\n");
        builder.append("<ul>");
        builder.append("<li>链接：<a href=\"../\">..</a></li>\r\n");
        for (File f:dir.listFiles()){
            if (f.isHidden()||!f.canRead()){
                continue;
            }
            String fname=f.getName();
            if (!ALLOWED_FILE_NAME.matcher(fname).matches()){
                continue;
            }
            builder.append("<li>链接：<a href=\" ");
            builder.append(fname);
            builder.append("\" >");
            builder.append(fname);
            builder.append("</a></li>\r\n");
        }
        builder.append("</ul></body></html>\r\n");
 
        ByteBuf byteBuf= Unpooled.copiedBuffer(builder, CharsetUtil.UTF_8);
        response.content().writeBytes(byteBuf);
        byteBuf.release();
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
 
    }
 
 
    private void sendRedirect(ChannelHandlerContext channelHandlerContext, String newUri) {
        FullHttpResponse response=new DefaultFullHttpResponse(HTTP_1_1,FOUND);
        response.headers().set(LOCATION,newUri);
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
 
    private void sendError(ChannelHandlerContext channelHandlerContext, HttpResponseStatus status) {
        FullHttpResponse response=new DefaultFullHttpResponse(
                HTTP_1_1,status,Unpooled.copiedBuffer("Failure: "+status.toString()+"\r\n",
                CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE,"text/plain; charset=UTF-8");
        channelHandlerContext.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
 
    }
 
 
    private void setContentTypeHeader(HttpResponse httpResponse, File file) {
 
        MimetypesFileTypeMap mimetypesFileTypeMap=new MimetypesFileTypeMap();
        httpResponse.headers().set(CONTENT_TYPE,mimetypesFileTypeMap.getContentType(file.getPath()));
    }
 
 
 
}
