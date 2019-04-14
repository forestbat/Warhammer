package com.forestbat.warhammer.asm;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.FMLLog;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM5;

public class ParentTransformer implements IClassTransformer {
    private static String parentName;
    private static String[] baseInterfaces;

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(baseInterfaces!=null && parentName!=null) {
            ClassWriter classWriter = new ClassWriter(2);
            ClassVisitor classVisitor = new ClassVisitor(ASM5, classWriter) {
                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    super.visit(52, access, transformedName, null, null, null);
                }
            };
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classVisitor, 2);
            classWriter.visit(52, 0x0001, transformedName, null,
                    setParentName(transformedName, parentName), setInterfacesName(transformedName, baseInterfaces));
            classWriter.toByteArray();
            return basicClass;
        }
        else return basicClass;
    }
    public static String setParentName(String transformedName,String parentName){
        ParentTransformer.parentName=parentName;
        return parentName;
    }
    public static String[] setInterfacesName(String transformedName,String[] baseInterfaces) {
        ParentTransformer.baseInterfaces = baseInterfaces;
        if (baseInterfaces != null) {
            for (String baseInterface : baseInterfaces) {
                try {
                    ClassReader classReader = new ClassReader(baseInterface);
                    if (classReader.getItemCount() == 0)
                        return baseInterfaces;
                } catch (IOException e) {
                    FMLLog.bigWarning("No any interfaces!");
                }
            }
            return null;
        }
        else return null;
    }
}
