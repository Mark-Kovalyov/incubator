DOTTY_VERSION=3.2.1
SCALA_2_VERSION=2.13.10

CURRENT_MAVEN_REPO=$(HOME)/.m2/repository
CURRENT_RELASE_FOLDER=bin/
TARGET_SCALA=target/scala-$(DOTTY_VERSION)
SCALA_HOME_LIB=/scala/$(DOTTY_VERSION)/lib

run:
	sbt test

release: build
	mkdir -p $(CURRENT_RELASE_FOLDER)logs
	mkdir -p $(CURRENT_RELASE_FOLDER)lib
	cp -f $(TARGET_SCALA)/ftp-sync_3-0.1.0-SNAPSHOT.jar $(CURRENT_RELASE_FOLDER)
	cp -f $(TARGET_SCALA)/*.jar $(CURRENT_RELASE_FOLDER)lib/
	rm -f $(TARGET_SCALA)/*.jar $(CURRENT_RELASE_FOLDER)lib/ftp-sync_3-0.1.0-SNAPSHOT.jar
	cp -f *.properties $(CURRENT_RELASE_FOLDER)
	cp -f $(TARGET_SCALA)/*.cmd $(CURRENT_RELASE_FOLDER)
	cp -f $(TARGET_SCALA)/*.sh $(CURRENT_RELASE_FOLDER)
	cp -f logback.xml $(CURRENT_RELASE_FOLDER)

build:
	sbt package
	cp -f src/main/resources/* $(TARGET_SCALA)
	cp -f $(CURRENT_MAVEN_REPO)/net/i2p/crypto/eddsa/0.3.0/eddsa-0.3.0.jar $(TARGET_SCALA)
	cp -f $(CURRENT_MAVEN_REPO)/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar $(TARGET_SCALA)
	cp -f $(SCALA_HOME_LIB)/scala3-library_3-$(DOTTY_VERSION).jar $(TARGET_SCALA)
	cp -f $(SCALA_HOME_LIB)/scala3-compiler_3-$(DOTTY_VERSION).jar $(TARGET_SCALA)
	cp -f $(SCALA_HOME_LIB)/scala3-interfaces-$(DOTTY_VERSION).jar $(TARGET_SCALA)
	cp -f $(SCALA_HOME_LIB)/scala3-staging_3-$(DOTTY_VERSION).jar $(TARGET_SCALA)
	cp -f $(SCALA_HOME_LIB)/scala3-tasty-inspector_3-$(DOTTY_VERSION).jar $(TARGET_SCALA)
	cp -f $(SCALA_HOME_LIB)/scala-library-$(SCALA_2_VERSION).jar $(TARGET_SCALA)
