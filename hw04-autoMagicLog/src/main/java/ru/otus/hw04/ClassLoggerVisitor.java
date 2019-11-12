package ru.otus.hw04;

import org.objectweb.asm.*;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class ClassLoggerVisitor extends ClassVisitor{

    ClassLoggerVisitor(int api, ClassVisitor classVisitor ){
        super(api, classVisitor);
    }

    static class MethodAnnotationScanner extends MethodVisitor{

        MethodAnnotationScanner(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        private boolean doLogging = false;

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible){
            if (desc.equals("Lru/otus/hw04/LogMethodParam;")) doLogging = true;
            return mv.visitAnnotation(desc, visible);
        }

        @Override
        public void visitCode() {
            mv.visitCode();
            if (doLogging) {
                Handle handle = new Handle(
                        H_INVOKESTATIC,
                        Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                        "makeConcatWithConstants",
                        MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                        false);
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitVarInsn(Opcodes.ALOAD, 1);
                mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(Ljava/lang/Integer;)Ljava/lang/String;", handle, "executed method: calculation, param:\u0001");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
              }
            }
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            return new MethodAnnotationScanner(Opcodes.ASM5, mv);
    }

}