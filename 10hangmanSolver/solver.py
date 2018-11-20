import sys, random
A=''
freq_map={}
with open('frequencies.txt') as f:
    for l in f:
        tokens = l.strip().split(" ")
        freq_map[tokens[0]] = [int(t) for t in tokens[1:]]
        A+=tokens[0]
def sample(P):
    H = 0
    r=random.randint(0,sum(P))
    for i in range(26):
        H += P[i]
        if r <= H:
            return A[i]
def G(l):
    return sample(freq_map[l])
new_word=True
tried=set()
while 1:
    state = sys.stdin.readline()
    if state.rstrip() == 'END': sys.exit()
    if '_' not in state.rstrip():
        new_word=True
        tried=set()
    else: new_word=False
    pre='e'
    for c in state.rstrip():
        if c == '_':
            guess=G(pre)
            while guess in tried:
                guess=G(pre)
            sys.stdout.write(guess+'\n')
            sys.stdout.flush()
            tried.add(guess)
            break
        pre=c