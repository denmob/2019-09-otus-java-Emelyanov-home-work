package ru.otus.hw04;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AnnotationScanner extends ClassVisitor{

    AnnotationScanner(int api){
        super(api);
    }

    static class MethodAnnotationScanner extends MethodVisitor{
        MethodAnnotationScanner(){ super(Opcodes.ASM4); }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible){
          //  if desc.equals("ru/otus/hw04/LogMethodCall")
            System.out.println("M.visitAnnotation: desc="+desc + " visible= "+visible);
            return super.visitAnnotation(desc, visible);
        }
    }

//    @Override
//    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions){
//        System.out.println("\nvisitMethod: name="+name+" desc="+desc);
//        return new MethodAnnotationScanner();
//    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals("calculation")) {
            return new MethodAnnotationScanner();
           // return super.visitMethod(access, "calculationProxied", descriptor, signature, exceptions);
        } else {
            return new MethodAnnotationScanner();
        }
    }

}