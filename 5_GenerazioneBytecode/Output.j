.class public Output 
.super java/lang/Object

.method public <init>()V
 aload_0
 invokenonvirtual java/lang/Object/<init>()V
 return
.end method

.method public static print(I)V
 .limit stack 2
 getstatic java/lang/System/out Ljava/io/PrintStream;
 iload_0 
 invokestatic java/lang/Integer/toString(I)Ljava/lang/String;
 invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
 return
.end method

.method public static read()I
 .limit stack 3
 new java/util/Scanner
 dup
 getstatic java/lang/System/in Ljava/io/InputStream;
 invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
 invokevirtual java/util/Scanner/next()Ljava/lang/String;
 invokestatic java/lang/Integer.parseInt(Ljava/lang/String;)I
 ireturn
.end method

.method public static run()V
 .limit stack 1024
 .limit locals 256
 invokestatic Output/read()I
 istore 0
 invokestatic Output/read()I
 istore 1
 ldc 1
 ldc 1
 iadd 
 ldc 1
 iadd 
 ldc 1
 iadd 
 istore 2
 ldc 1
 ldc 2
 imul 
 ldc 3
 imul 
 ldc 4
 imul 
 istore 3
 iload 2
 invokestatic Output/print(I)V
 iload 3
 invokestatic Output/print(I)V
L1:
 iload 1
 ldc 0
 if_icmpne L2
 goto L3
L2:
 iload 1
 ldc 1
 isub 
 istore 1
 iload 1
 invokestatic Output/print(I)V
 goto L1
L3:
 iload 0
 ldc 0
 if_icmpne L4
 goto L5
L4:
L7:
 iload 0
 ldc 0
 if_icmpne L8
 goto L9
L8:
 iload 0
 ldc 1
 isub 
 istore 0
 iload 0
 invokestatic Output/print(I)V
 ldc 444
 ldc 444
 iadd 
 invokestatic Output/print(I)V
 ldc 888
 invokestatic Output/print(I)V
 goto L7
L9:
 goto L6
L5:
 ldc 1776
 ldc 2
 idiv 
 istore 4
 iload 0
 invokestatic Output/print(I)V
 iload 4
 invokestatic Output/print(I)V
 ldc 888
 invokestatic Output/print(I)V
L6:
 ldc 1
 ldc 1
 iadd 
 ldc 1
 iadd 
 ldc 1
 iadd 
 istore 2
 ldc 1
 ldc 2
 imul 
 ldc 3
 imul 
 ldc 4
 imul 
 istore 3
 iload 2
 invokestatic Output/print(I)V
 iload 3
 invokestatic Output/print(I)V
L0:
 return
.end method

.method public static main([Ljava/lang/String;)V
 invokestatic Output/run()V
 return
.end method

