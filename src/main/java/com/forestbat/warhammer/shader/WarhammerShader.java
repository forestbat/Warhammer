package com.forestbat.warhammer.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;

import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;

public class WarhammerShader {
	public void shader(){
		int shader1=OpenGlHelper.glCreateShader(GL_VERTEX_SHADER_ARB);
		OpenGlHelper.glCompileShader(shader1);
		OpenGlHelper.glAttachShader(Minecraft.getMinecraft().renderEngine.hashCode(),shader1);//???
	}
}
