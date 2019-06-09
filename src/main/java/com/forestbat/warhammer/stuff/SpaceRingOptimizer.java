package com.forestbat.warhammer.stuff;

import com.forestbat.warhammer.Warhammer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;

import java.util.List;

import static io.netty.handler.codec.ByteToMessageDecoder.MERGE_CUMULATOR;

public class SpaceRingOptimizer extends ChannelInitializer<Channel>{
    public ByteToMessageDecoder.Cumulator cumulator = MERGE_CUMULATOR;
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if(in instanceof PacketBuffer) {
            ChunkedWriteHandler chunkedWriteHandler = (ChunkedWriteHandler) ctx.handler();
            if(in.capacity()>16777214){
            	//ByteToMessageDecoder decoder=new LengthFieldBasedFrameDecoder(in.capacity(),0, 128);
				try {
					chunkedWriteHandler.channelWritabilityChanged(ctx);
				}catch (Exception e){
					Warhammer.LOGGER.error("No capacity!");
				}
			}
        }
    }

    public void initChannel(Channel channel){
        NBTTagList nbtTagList=new NBTTagList();
        ChannelPipeline channelPipeline=channel.pipeline();
        ChunkedWriteHandler nbtHandler= (ChunkedWriteHandler)channelPipeline.lastContext();
        channelPipeline.addFirst(nbtHandler);
    }
}
