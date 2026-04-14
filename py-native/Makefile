py:
	python3 splitcoff.py pw-kecogen.exe

nuitka:
	nuitka --standalone --onefile --follow-imports --output-dir=dist splitcoff.py

nuitka-lto:
	nuitka --standalone --onefile --follow-imports --lto=. --output-dir=dist splitcoff.py

cython:
	python3 splitcoff.py

clean:
	rm -fr dist

deps:
	pip install nuitka
	pip install pefile
	apt install build-essential python3-dev
	conda install libpython-static
