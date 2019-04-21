package com.forestbat.warhammer.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.ARBGpuShaderFp64;

import static org.lwjgl.opengl.ARBVertexShader.GL_VERTEX_SHADER_ARB;

public class WarhammerShader {
	public void shader(){
		int shader1=OpenGlHelper.glCreateShader(GL_VERTEX_SHADER_ARB);
	}
}
