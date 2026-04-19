py:
	python3 splitcoff.py ipv4toloc.exe

nuitka:
	sudo apt-get install python3-dev
	pip install nuitka
	pip install pefile
	apt install build-essential python3-dev
	conda install libpython-static
	nuitka --standalone --onefile --follow-imports --output-dir=dist splitcoff.py

nuitka-lto:
	nuitka --standalone --onefile --follow-imports --lto=. --output-dir=dist splitcoff.py

cython:
	pip install cython
	python3 setup.py build_ext --inplace

clean:
	rm -fr dist



