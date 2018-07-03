# given a string S and a list of word D, find the
# word (from D) which is a substring (not a consecutive
# subsequence) of S and
# which is the longest among the ones that you can
# find in S

import time # for execution time

# data
def init():
    S = 'abppplee'
    D = ['able','ale','apple','bale','kangaroo','hallelujahuh']
    return S, D

def is_subseq(S,word):
    # return true if word is a subsequence of the string S

    N = len(S)
    L = len(word)
    print("Length is "+str(L))
    # loop through the string, one position at a time
    i = 0 # index on string S
    j = 0 # index on word's characters
    while i < N and L - j <= N - i:
        print("{:d}) {} {:d}) {}".format(j, word[j], i, S[i]))
        if word[j] == S[i]: # we found a character of word in S!
            j += 1 # increase j so that you will check following characters in word
        if j == L: # we found all the characters! (j=0,...,L-1 means that we are still searching for a char)
            return True
        i += 1 # increase to go to check the next character in S
    # we did not find it
    return False

def clean(S,D):
    # filter out words that are too long
    N = len(S)
    fD = list(filter(lambda str: len(str)<=N, D))
    print("Filtered: ", fD)

    # sort the words by lenght, so that you look for the longest before looking for
    # the shortest. If you find one, you know that that is immediately the longest
    # subsequence in S.
    sD = sorted(fD, key=len, reverse=True)
    print("Sorted: ", sD)

    return sD

def find(S, D): 
    # filter out long words and sort (longest words first) list sD
    sD = clean(S,D)
    # select a word
    for index, word in enumerate(sD):
        print("CHECK: " + word)
        if is_subseq(S,word) == True: # found!
            # since we pre-sorted D, we know that this is the longest one
            return word # return the longest word
    # we did not find it
    return None

def run():
    S, D = init()             
    res = find(S,D)
    print("Longest substring is: "+res) if res is not None else print("No word found in the string")

# execution
start = time.time()
run()
end = time.time()
print("Execution time: {:f}".format(end-start))



