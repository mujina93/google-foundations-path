import sys, random, subprocess

# Open the solver program (given as argv 1, such as in
# `python ./game.py [yoursolverprogram]`) in a subprocess.
# The game will send data (the game state: e.g. '_a_o_e') to
# the solver subprocess by submitting the semi-guessed word
# to the subprocess' stdin.
# The solver program will have to write to stdout its guesses
# every time it receives the game state from this process.
# This process (game) will read the solver's stdout and parse
# the guessed character from there. It will update the state of
# the game (either revelaing guessed letters or scoring errors,
# and eventually re-starting to a new word), and provide the
# state again to the solver's stdin.
# Once the solver receives 'END', the game ends, and the results
# are shown.
proc = subprocess.Popen(sys.argv[1:], stdin=subprocess.PIPE, stdout=subprocess.PIPE, encoding='UTF-8')

# function to write to proc's stdin and flush
def p(x):
    proc.stdin.write(x+'\n')
    proc.stdin.flush()

# import the words from wordlist.txt into a list and shuffle them
wordlist=[]
f=open('wordlist.txt', 'r')
for i in f:
    wordlist.append(i[:-1] if i[-1]=='\n' else i)
# wordlist=[i[:-1] for i in f]
random.shuffle(wordlist)

# game variables
score=0
totalerr=0

# for each word...
for s in wordlist:
    # play the game:
    print(s)

    # list of _ _ _ _ to guess
    s2=[]
    for i in s:
        s2.append('_')
    
    # wrong attempts
    err=0

    # write _ _ _ _ to the process
    p(''.join(s2))

    # until there are letters to guess and you didn't make 6+ errors..
    while err<6 and '_' in s2:
        # get a character from the stdout (the solver writes to stdout)
        c=proc.stdout.readline().strip()
        print(c)
        nomatch=True
        for i in range(0, len(s)):
            # if the letter is there, turn _ into the letter
            if s[i]==c:
                s2[i]=c
                nomatch=False
        # if the letter wasn't there, count 1 error
        if nomatch:
            err+=1
            totalerr+=1
        # push again the current (semi-guessed) word to proc's stdin
        p(''.join(s2))
    
    # if you guessed the whole word with 6- errors, you score 1
    if err<6:
        score+=1

# write END to proc's stdin
p('END')

# write in system's stderr the final result
sys.stderr.write('score is '+str(score)+', totalerr is '+str(totalerr)+'\n')