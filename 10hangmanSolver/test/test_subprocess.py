import subprocess as sp

sp.run(['ls','-l'])

#sp.run("exit 1", shell=True, check=True)

ret = sp.run(['ls','-l','/dev/null'])

print("returned code: ",ret.returncode)
