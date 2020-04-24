package ru.otus.hw04.asm;

import org.objectweb.asm.*;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.stream.IntStream;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;

public class ClassLoggerVisitor extends ClassVisitor {

  ClassLoggerVisitor(int api, ClassVisitor classVisitor) {
    super(api, classVisitor);
  }

  static class MethodAnnotationScanner extends MethodVisitor {

    MethodAnnotationScanner(int api, MethodVisitor methodVisitor, String methodName, String methodDesc) {
      super(api, methodVisitor);
      this.methodName = methodName;
      this.methodDesc = methodDesc;
    }

    private boolean doLogging = false;
    private String methodName;
    private String methodDesc;

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      if (desc.equals("Lru/otus/hw04/logAnnotation/LogMethodParam;")) {
        doLogging = true;
      }
      return mv.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
      mv.visitCode();
      if (doLogging) {
        Type[] argTypes = Type.getArgumentTypes(methodDesc);
        if (argTypes.length > 0) {
          methodDesc = getDescByArgTypes(argTypes);

          Handle handle = new Handle(
              H_INVOKESTATIC,
              Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
              "makeConcatWithConstants",
              MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
              false);

          mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

          for (int i = 0; i < argTypes.length; i++)
            mv.visitVarInsn(argTypes[i].getOpcode(Opcodes.ILOAD), i + 1);

          mv.visitInvokeDynamicInsn("makeConcatWithConstants", methodDesc, handle, "executed method " + methodName + ", param: \u0001;" + " \u0001;".repeat(argTypes.length - 1));
          mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
      }
    }
  }

  @Override
  public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
    MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
    return new MethodAnnotationScanner(Opcodes.ASM5, mv, name, descriptor);
  }

  private static String getDescByArgTypes(Type[] argTypes) {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    IntStream.range(0, argTypes.length).forEach(i -> sb.append(argTypes[i].getDescriptor()));
    sb.append(")Ljava/lang/String;");
    return sb.toString();
  }


}
