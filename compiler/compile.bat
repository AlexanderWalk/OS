cd "C:\FH Kempten\Master of Disaster\Betreibssystem Eigenbau\compiler"
compile "..\src" "..\javaCompatibility" -o boot -u rte -g -t ia32 -T nsop -s 1M
cd "C:\Program Files\qemu"
qemu-system-x86_64.exe -m 32 -boot a -serial stdio -fda "C:\FH Kempten\Master of Disaster\Betreibssystem Eigenbau\compiler\BOOT_FLP.IMG"
