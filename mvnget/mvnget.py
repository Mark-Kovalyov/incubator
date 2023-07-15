import os
import requests
import shutil
import sys
import argparse

from pathlib import Path
from sys import platform

repos = {
	 "mvn"        : "https://repo1.maven.org/maven2",
	 "ld"         : "http://logicaldoc.sourceforge.net/maven",
         "sona"       : "https://oss.sonatype.org/content/repositories/snapshots"
	 "sona-s01-s" : "https://s01.oss.sonatype.org/content/repositories/snapshots",
         "sona-s01-r" : "https://s01.oss.sonatype.org/content/repositories/releases",
	 "conf"       : "https://packages.confluent.io/maven"
}

package_types = {
	'jar' : '.jar',
	'pom' : '.pom',
	'src' : '-sources.jar',
	'doc' : '-javadoc.jar'
}

def user_home() -> str:
	if platform == "linux" or platform == "linux2":
		return str(Path.home())
	else:
		return str(Path.home()).replace('\\','/')

local_repo = user_home() + '/.m2/repository'

def get_dir(path : str) -> str:
	spl = path.split('/')
	return '/'.join(spl[0:-1])


def format_scala_file_only(artifact : str, scala_version : str, version : str) -> str:
	return artifact + '_' + scala_version + '-' + version

# Input:  mvnget -a "dev.zio:zio:2.0.15" -s 2.13 -p jar
#  vendor  : "dev.zio"
#  arti    : "zio"
#  version : "2.0.15"
#  scala   : "2.13"
# Output:
#  https://repo1.maven.org/maven2/dev/zio/zio_2.13/2.0.15/zio_2.13-2.0.15.jar
#                                 dev/zio/zio_2.13/2.0.15/zio_2.13-2.0.15

def format_scala(vendor : str, arti : str, scala_version : str, version : str) -> str:
	return vendor.replace('.','/') + '/' + \
		arti + '_' + scala_version + '/' + version + '/' + \
		format_scala_file_only(arti, scala_version, version)


# Input:
#  vendor  : "com.fasterxml.jackson.core"
#  arti    : "jackson-databind"
#  version : "2.15.2"
# Output:
#   https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2.jar
#                                  com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2
#   https://s01.oss.sonatype.org/content/repositories/snapshots/org/apktool/apktool-cli/2.6.1-SNAPSHOT/apktool-cli-2.6.1-20211108.112934-1-all.jar
#
def format_java(vendor : str, arti : str, version : str) -> str:
	return vendor.replace('.','/') + '/' + \
		arti + '/' + version + '/' + \
		arti + '-' + version

def explode_artifactcoordinates(artifactcoordinates : str) -> tuple:
	repository_items = artifactcoordinates.split(':')
	vendor = repository_items[0]
	artifact = repository_items[1]
	version = repository_items[2]
	return (vendor, artifact, version)

def download_scala(repository : str, artifactcoordinates : str, scala_version : str, destination : str, package_suffix : str) -> bool:
	(vendor, artifact, version) = explode_artifactcoordinates(artifactcoordinates)
	path = format_scala(vendor, artifact, scala_version, version)
	return download_raw_path(repository + '/' + path + package_suffix, destination)

def download_java(repository, artifactcoordinates, destination, package_suffix) -> bool:
	(vendor, artifact, version) = explode_artifactcoordinates(artifactcoordinates)
	path = format_java(vendor, artifact, version)
	return download_raw_path(repository + '/' + path + package_suffix, destination)


def universal_download(repository, artifactcoordinates, destination, package_suffix) -> bool:
	
	return True

def download_raw_path(src_http_path : str, dest : str) -> bool:
	print("src_http_path=", src_http_path)
	r = requests.get(src_http_path, stream=True)
	if (r.status_code == 200):
		Path(get_dir(dest)).mkdir(parents=True, exist_ok=True)
		bytes_content = r.content
		with open(dest,'wb') as f:
			f.write(bytes_content)
			print('Saved ', len(bytes_content), ' bytes into file ', dest)
		return True
	else:
		print('Something going wrong. Code = ', r.status_code, ' message = ', r.reason, '\n')
		return False
	return True


def download_raw(vendor : str, arti : str, version : str, package : str, repo_code : str, dest : str, src : str) -> bool:
	r = requests.get(src, stream=True)
	if (r.status_code == 200):
		Path(get_dir(dest)).mkdir(parents=True, exist_ok=True)
		bytes_content = r.content
		with open(dest,'wb') as f:
			f.write(bytes_content)
			print('Saved ', len(bytes_content), ' bytes into file ', dest)
		return True
	else:
		print('Something going wrong. Code = ', r.status_code, ' message = ', r.reason, '\n')
		return False
	return True




def main():
	parser = argparse.ArgumentParser(description='File Processor')
	parser.add_argument("-a", "--artifactcoordinates",  type=str, help="Example : junit:junit:4.0")
	parser.add_argument("-p", "--packagetype",   type=str, help="Package type : { jar|pom|src|doc }")
	parser.add_argument("-s", "--scalaversion",  type=str, default=None,
						help="Optional value for scala technology. Values are : { 2.11, 2.12, 2.13, 3 }")
	parser.add_argument("-r", "--repository",    type=str, nargs=1, default="mvn",
						help="Possible values are : { mvn, ld }")
	parser.add_argument("-d", "--destination",   type=str, nargs=1, default=None,
						help="Path to download. Defauls is the ~/.mvn/repository")
	#parser.add_argument("-i", "--ignorefolders", action="store_true", help="Ignore sub-folders structure in destination.")
	if len(sys.argv) == 1:
		parser.print_help()
		return
	else:
		args : dict = parser.parse_args()

		print("user_home = ", user_home())

		print("arti = ", args.artifactcoordinates, "\n")
		if args.scalaversion != None:
			print("scala = ", args.scalaversion, "\n")
		print("repo = ", args.repository, "\n")
		print("dst = ", args.destination, "\n")

		artifactcoordinates = args.artifactcoordinates
		repository          = repos[args.repository]
		scala_version       = args.scalaversion
		destination         = args.destination
		print("destination[1]=", destination)
		print("type(dest) = ", type(destination))
		#ignorefolders       = args.ignorefolders
		print("args.packagetype=", args.packagetype)
		package_suffix      = package_types[args.packagetype]
		print("package_suffix=", package_suffix)

		(vendor, artifact, version) = explode_artifactcoordinates(artifactcoordinates)

		if destination == None:
			destination = local_repo + "/" + format_scala(vendor, artifact, scala_version, version) + package_suffix
		elif destination[0] == ".":
			destination = format_scala_file_only(artifact, version, scala_version) + package_suffix
		else:
			destination = destination[0] + "/" + format_scala_file_only(artifact, version, scala_version) + package_suffix

		print("destination[2]=", destination)

		if scala_version != None:
			download_scala(repository, artifactcoordinates, scala_version, destination, package_suffix)
		else:
			download_java(repository, artifactcoordinates, destination, package_suffix)
		return

if __name__ == "__main__":
    main()
