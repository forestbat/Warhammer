package com.forestbat.warhammer.stuff;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

import static io.netty.handler.codec.ByteToMessageDecoder.MERGE_CUMULATOR;

public class SpaceRingOptimizer extends ChannelInitializer<Channel>{
    //todo Probably need to implement DataInput
    public ByteToMessageDecoder.Cumulator cumulator = MERGE_CUMULATOR;
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

    }

    public void initChannel(Channel channel){
        NBTTagList nbtTagList=new NBTTagList();
        ChannelPipeline channelPipeline=channel.pipeline();
        ChunkedWriteHandler nbtHandler=new ChunkedWriteHandler();
        channelPipeline.addFirst(nbtHandler);
        //nbtHandler.write();

    }
}
