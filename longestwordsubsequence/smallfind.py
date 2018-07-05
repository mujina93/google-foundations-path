# author - Michele Piccolini (mujina93)

# given a string S and a list of word D, find the
# word (from D) which is a substring (not a consecutive
# subsequence) of S and
# which is the longest among the ones that you can
# find in S

# Solution 3
#

import time
from math import floor

# data
def init():
    S = 'abppplee'
    D = ['able','ale','apple','bale','kangaroo','hallelujahuh']
    return S, D

def clean(S,D):
    # O(len(D)*log(len(D))) = O(L*log(L))

    # filter out words that are too long
    N = len(S)
    fD = list(filter(lambda str: len(str)<=N, D)) # O(len(D))
    print("Filtered: ", fD)

    # sort the words by lenght, so that you look for the longest before looking for
    # the shortest. If you find one, you know that that is immediately the longest
    # subsequence in S.
    sD = sorted(fD, key=len, reverse=True) # O(len(D)*log(len(D)))
    print("Sorted: ", sD)

    return sD

# build a map of S, a dictionary representing the information
# in the most efficient way possible for our problem
def decompose(S):
    # O(N*A)
    # A = length of alphabet used to write S
    # N = length of S

    N = len(S)
    print("Creating a dense map out of "+S+" (alternative dense representation for the string):")
    map = {}
    logical_lengths = {}
    for i,letter in enumerate(S):
        if letter not in map:
            map[letter] = [-1]*N
            logical_lengths[letter] = 0
        for k in range(logical_lengths[letter],i):
            map[letter][k] = i
        logical_lengths[letter] = i
    # dump
    for letter, position_list in map.items():
        print(letter, position_list)
    return map

def smallfind(S,D):
    # O(L*log(L) + N*A + L) = O(L*log(L) + N*A) ~ O(N + L*log(L)) if A is << N

    # clean and sort words (longer sooner)
    D = clean(S,D)  # O(L*log(L))

    # map S into a better representation (for fast lookup)
    map = decompose(S) # O(len(S)*len(alphabet(S))) = O(N*A)

    # look if the word is a substring of S by queriend the map
    for word in D:  # O(len(D)*...) = O(L*...)
        print("CHECK: "+word)
        found_valid = False
        index_of_last_valid_letter = 0
        for j,letter in enumerate(word): # O(few times...)
            # O(1)
            print(letter)
            if letter in map:
                dense_occurrence_list = map[letter] # positions of 'letter' in S. One or more may be 'valid'.
                # look for the first valid letter (the letter must be in S, and it must be
                # it must come after the last valid letter that was found in S)
                index_of_next_valid_letter = dense_occurrence_list[index_of_last_valid_letter]
                if index_of_next_valid_letter != -1:
                    index_of_last_valid_letter = index_of_next_valid_letter
                if index_of_last_valid_letter == -1: # you did not find a next valid letter
                    print("no valid letter was found. Stop searching for this word.")
                    break # exit the for letter in word loop, i.e. stop considering this word, it's not there
                else: # you found a next valid letter
                    found_valid = True # you found a valid one
                    print("valid position found: ",index_of_last_valid_letter)
            else:
                print("no such letter in the string")
                break
        if found_valid: # if the last letter of the word was found in S (and it was valid, i.e. it came after the other letters)
            return word # immediately return (since D was ordered, this is the longest one)
    # if nothing was found
    return None

def run():
    S, D = init()             
    res = smallfind(S,D)
    print("Longest substring is: "+res) if res is not None else print("No word found in the string")

# execution
start = time.time()
run()
end = time.time()
print("Execution time: {:f}".format(end-start))