JFLAGS = -g

JC = javac

JCLASSDIR=jbin

JVM = java

FILE=

.SUFFIXES: .java .class

.java.class:

	$(JC) $(JFLAGS) $*.java

CLASSES =eje.java

MAIN = eje

default: classes

classes:	$(CLASSES:.java=.class)

run: classes
	$(JVM) $(MAIN)
clean:
	$(RM) *.class
