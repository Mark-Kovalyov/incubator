TAG=words

build:
	mvn clean compile exec:java -Dexec.mainClass="mayton.DemoRadix" -Dexec.args=$(TAG)
	iconv -f utf-8 -t windows-1251 $(TAG)-prefix-tree.txt > $(TAG)-prefix-tree-1251.txt
	xxd -i $(TAG)-prefix-tree-1251.txt > $(TAG)-prefix-tree-1251.h


bloom:
	#https://github.com/DCSO/bloom
	bloom --gzip -p 0.001 -n 1200 words-bloom.dat
	cat words.txt | bloom insert words-bloom.dat
