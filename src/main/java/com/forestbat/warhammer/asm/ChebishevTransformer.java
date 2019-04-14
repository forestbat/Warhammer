package com.forestbat.warhammer.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import java.util.*;
import static java.lang.Math.PI;
import static org.objectweb.asm.Opcodes.ASM5;

public class ChebishevTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.util.math.MathHelper")) {
        FMLLog.log.warn("[Warhammer Transforming/]");
        ClassNode classNode = new ClassNode(ASM5);
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);
        List<MethodNode> methodNodeList = new ArrayList<>(classNode.methods);
        for (MethodNode methodNode : methodNodeList)
            if (methodNode.name.equals("func_76126_a(F)F")) {
                methodNode.instructions.clear();
                methodNode.instructions.add(new InsnNode(23));
                methodNode.instructions.add(new MethodInsnNode
                        (0x0001 + 0x0008, "Lcom/forestbat/warhammer/asm/ChebishevTransformer",
                                "sin", "(F)D", false));
                methodNode.instructions.add(new InsnNode(175));
            }
        ClassWriter classWriter = new ClassWriter(2);
        classNode.accept(classWriter);
        return classWriter.toByteArray();
    }
        return basicClass;
    }

    /**
     * This is ChebishevTransformer Algorithm
     */
    public static double sin(float value) {
        if (value >= 0 && value <= PI / 2)
            return (((((-0.000960664 * value + 0.0102697866) * value - 0.00198601997) * value - 0.1656067221)
                    * value - 0.0002715666) * value + 1.000026227) * value;
        if (value < 0)
            return -sin(-value);
        else
            return -sin((float) (value - PI));
    }

    public static double cos(float value) {
        return sin((float)PI/2-value);
    }
}
