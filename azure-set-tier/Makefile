build: clean
	mkdir -p bin/
	mvn clean package -DskipTests
	cp -f src/main/resources/*.sh bin/
	cp -f src/main/resources/*.cmd bin/
	cp -f src/main/resources/*.xml bin/
	cp -f target/azure-set-tier-*-SNAPSHOT-jar-with-dependencies.jar bin-fat/azure-set-tier.jar

build-jars: clean
	mvn clean package install dependency:copy-dependencies -DskipTests
	mkdir -p bin/
	cp -f src/main/resources/*.sh bin/
	cp -f src/main/resources/*.cmd bin/
	cp -f target/azure-set-tier.jar bin/azure-set-tier.jar
	cp -f target/dependency/azure-core-1.50.0.jar bin/
	cp -f target/dependency/azure-core-http-netty-1.15.2.jar                     bin/
	cp -f target/dependency/azure-json-1.1.0.jar                                 bin/
	cp -f target/dependency/azure-storage-blob-12.27.0.jar                       bin/
	cp -f target/dependency/azure-storage-common-12.26.0.jar                     bin/
	cp -f target/dependency/azure-xml-1.0.0.jar                                  bin/
	cp -f target/dependency/jackson-annotations-2.13.5.jar                       bin/
	cp -f target/dependency/jackson-core-2.13.5.jar                              bin/
	cp -f target/dependency/jackson-databind-2.13.5.jar                          bin/
	cp -f target/dependency/jackson-dataformat-xml-2.13.5.jar                    bin/
	cp -f target/dependency/jackson-datatype-jsr310-2.13.5.jar                   bin/
	cp -f target/dependency/logback-classic-1.5.18.jar                           bin/
	cp -f target/dependency/logback-core-1.5.18.jar                              bin/
	cp -f target/dependency/netty-buffer-4.1.110.Final.jar                       bin/
	cp -f target/dependency/netty-codec-4.1.110.Final.jar                        bin/
	cp -f target/dependency/netty-codec-http-4.1.110.Final.jar                   bin/
	cp -f target/dependency/netty-codec-http2-4.1.110.Final.jar                  bin/
	cp -f target/dependency/netty-common-4.1.110.Final.jar                       bin/
	cp -f target/dependency/netty-handler-4.1.110.Final.jar                      bin/
	cp -f target/dependency/netty-handler-proxy-4.1.110.Final.jar                bin/
	cp -f target/dependency/netty-resolver-4.1.110.Final.jar                     bin/
	cp -f target/dependency/netty-resolver-dns-4.1.109.Final.jar                 bin/
	cp -f target/dependency/netty-tcnative-classes-2.0.65.Final.jar              bin/
	cp -f target/dependency/netty-transport-4.1.110.Final.jar                    bin/
	cp -f target/dependency/netty-transport-classes-epoll-4.1.110.Final.jar      bin/
	cp -f target/dependency/netty-transport-native-unix-common-4.1.110.Final.jar bin/
	cp -f target/dependency/reactive-streams-1.0.4.jar                           bin/
	cp -f target/dependency/reactor-core-3.4.38.jar                              bin/
	cp -f target/dependency/reactor-netty-core-1.0.45.jar                        bin/
	cp -f target/dependency/reactor-netty-http-1.0.45.jar                        bin/
	cp -f target/dependency/slf4j-api-2.0.17.jar                                 bin/
	cp -f target/dependency/slf4j-ext-2.0.9.jar                                  bin/
	cp -f target/dependency/slf4j-simple-2.0.9.jar                               bin/
	cp -f target/dependency/stax2-api-4.2.1.jar                                  bin/
	cp -f target/dependency/woodstox-core-6.4.0.jar                              bin/

clean:
	rm -f bin/*

build-graal: build
	cd bin && native-image -jar azure-set-tier --no-fallback

run-trace-gc:
	java -verbose:class -jar bin/azure-set-tier.jar