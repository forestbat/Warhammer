package com.forestbat.warhammer.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;

import javax.annotation.Nonnull;
import java.io.IOException;

public class WhNBTPacket implements Packet {
	public final NBTTagCompound nbtTagCompound;
	public WhNBTPacket(NBTTagCompound nbtTagCompound){
		this.nbtTagCompound=nbtTagCompound;
	}
	@Override
	public void readPacketData(@Nonnull PacketBuffer buf) throws IOException {
		ByteBuf byteBuf=buf.readBytes(nbtTagCompound.getSize());

	}

	@Override
	public void writePacketData(@Nonnull PacketBuffer buf) throws IOException {

	}

	@Override
	public void processPacket(@Nonnull INetHandler handler) {

	}
}
