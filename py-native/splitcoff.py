import sys
from pathlib import Path
import pefile
from pefile import PE
import json

def split_pe(path):

    p : PE = pefile.PE(str(path))

    outdir = Path(path).with_suffix('')
    outdir.mkdir(exist_ok=True)
    meta = {}
    data : bytes = None
    with open(path, 'rb') as f:
        data = f.read()
    # sections

    for sec in p.sections:
        name : str = sec.Name.rstrip(b'\x00').decode(errors='replace')

        raw_off : int = sec.PointerToRawData
        raw_sz  : int = sec.SizeOfRawData
        # clamp
        raw_end : int = min(raw_off + raw_sz, len(data))
        chunk   : bytes = data[raw_off:raw_end]

        fname = outdir / f"{name or 'section'}_{sec.VirtualAddress:08X}.bin"
        with open(fname, 'wb') as of:
            of.write(chunk)
            meta[str(fname.name)] = {
                'Name': name,
                'VirtualAddress': hex(sec.VirtualAddress),
                'VirtualSize': sec.Misc_VirtualSize,
                'PointerToRawData': raw_off,
                'SizeOfRawData': raw_sz,
                'Characteristics': hex(sec.Characteristics)
            }
    # overlay
    last_end : int = max((sec.PointerToRawData + sec.SizeOfRawData) for sec in p.sections) if p.sections else 0
    # ?
    if len(data) > last_end:
        overlay = data[last_end:]
        ofn = outdir / "overlay.bin"
        with open(ofn, 'wb') as of:
            of.write(overlay)
        meta['overlay.bin'] = {'Offset': last_end, 'Size': len(overlay)}

    # save metadata
    with open(outdir / 'sections.json', 'w') as mf:
        json.dump(meta, mf, indent = 2)

if __name__ == "__main__":
    if len(sys.argv) > 1:
        split_pe(sys.argv[1])
    else:
        print("Usage: splitcoff [file.exe]")
